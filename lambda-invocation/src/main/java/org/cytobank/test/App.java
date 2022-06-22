package org.cytobank.test;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class App {

  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  private static AWSLambda awsLambda;

  public static void main(String[] args) {
    final int lambdas = 1;
    String functionName = "song-efs-apigw";
    FioInput fioInput = new FioInput();
    fioInput.setName("test_job");
    fioInput.setDirectory("/mnt/channelstripe");
    fioInput.setBs("4K");
    fioInput.setFilesize("157K");
    fioInput.setNumjobs("600");
    fioInput.setTimes(1);
    InvokeRequest invokeRequest = new InvokeRequest()
        .withFunctionName(functionName)
        .withPayload(GSON.toJson(fioInput));
    awsLambda = AWSLambdaClientBuilder.standard()
        .withCredentials(new ProfileCredentialsProvider())
        .withRegion(Regions.US_WEST_2).build();

    ExecutorService executorService = Executors.newFixedThreadPool(100);
    Instant start = Instant.now();
    try {
      CompletableFuture.allOf(IntStream.range(0, lambdas).boxed()
              .map(
                  channelId ->
                      CompletableFuture.runAsync(
                          () -> {
                            invokeLambda(invokeRequest);
                          },
                          executorService)).toArray(CompletableFuture[]::new))
          .join();
    } catch (ServiceException e) {
      System.out.println(e);
    }
    System.out.println("Totally uses " + Duration.between(start, Instant.now()).toMillis() + "ms");
    System.out.println("done!!");
    System.exit(0);
  }

  private static void invokeLambda(InvokeRequest invokeRequest) {
    Instant start = Instant.now();
    InvokeResult invokeResult = awsLambda.invoke(invokeRequest);

    String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

    //write out the return value
    InvokeResultObject invokeResultObject = GSON.fromJson(ans, InvokeResultObject.class);
    if(200 == invokeResultObject.getStatusCode()) {
      System.out.println(invokeResultObject.getBody());
      System.out.println("uses " + Duration.between(start, Instant.now()).toMillis() + "ms");
    } else {
      System.out.println("ERROR");
    }
  }

  @Data
  private static class InvokeResultObject {
    int statusCode;
    String body;
  }
}
