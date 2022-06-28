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
import org.cytobank.test.util.PropertiesUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class NioApp {

  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  private static AWSLambda awsLambda;

  private static NioConfig nioConfig;

  public static void main(String[] args) {
    nioConfig = PropertiesUtils.loadProperties(NioConfig.class, "config.yml");
    NioInput nioInput = nioConfig.getActiveNioInput();
    String functionName = nioConfig.getFunctionName();
    System.out.println(GSON.toJson(nioInput));
    InvokeRequest invokeRequest = new InvokeRequest()
        .withFunctionName(functionName)
        .withPayload(GSON.toJson(nioInput));
    awsLambda = AWSLambdaClientBuilder.standard()
        .withCredentials(new ProfileCredentialsProvider())
        .withRegion(Regions.US_WEST_2).build();

    ExecutorService executorService = Executors.newFixedThreadPool(100);
    Instant start = Instant.now();
    try {
      CompletableFuture.allOf(IntStream.range(0, nioInput.getNumLambdas()).boxed()
              .map(
                  channelId ->
                      CompletableFuture.runAsync(
                          () -> {
                            invokeLambda(invokeRequest);
                          },
                          executorService)).toArray(CompletableFuture[]::new))
          .join();
    } catch (ServiceException e) {
      e.printStackTrace();
    }
    long duration = Duration.between(start, Instant.now()).toMillis();
    System.out.println(String.format("total duration: %dms", duration));
    executorService.shutdownNow();
  }

  private static void invokeLambda(InvokeRequest invokeRequest) {
    Instant start = Instant.now();
    InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
    long duration = Duration.between(start, Instant.now()).toMillis();
    if (200 == invokeResult.getStatusCode()) {
      if(nioConfig.log) {
        System.out.println(String.format("duration: %dms, throughput: %dKB/s", duration, nioConfig.getFileSize()/duration));
      }
    } else {
      System.out.println("ERROR");
    }
  }

}
