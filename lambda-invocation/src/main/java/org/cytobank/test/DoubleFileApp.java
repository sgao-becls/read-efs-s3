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
import lombok.extern.java.Log;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Log
public class DoubleFileApp {

  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  private static AWSLambda awsLambda;

  private static boolean LOG_ENABLED;

  private static void checkAndPrint(String message) {
    if (LOG_ENABLED) {
      print(message);
    }
  }

  private static void print(String message) {
    log.info(message);
  }

  @Data
  private static class Input {
    String path;
  }

  public static void main(String[] args) {

    final String functionName = args[0];
    final int lambdas = Integer.parseInt(args[1]);
    Input input = new Input();
    input.setPath(args[2]);
    final String logEnable = args[3];
    if (Objects.isNull(logEnable) || logEnable.isBlank()) {
      LOG_ENABLED = false;
    } else {
      LOG_ENABLED = Boolean.parseBoolean(logEnable);
    }

    InvokeRequest invokeRequest = new InvokeRequest()
        .withFunctionName(functionName)
        .withPayload(GSON.toJson(input));
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
      e.printStackTrace();
    }
    print("Totally uses " + Duration.between(start, Instant.now()).toMillis() + "ms");
    System.exit(0);
  }

  private static void invokeLambda(InvokeRequest invokeRequest) {
    Instant start = Instant.now();
    InvokeResult invokeResult = awsLambda.invoke(invokeRequest);


    if(200 == invokeResult.getStatusCode()) {
      checkAndPrint("uses " + Duration.between(start, Instant.now()).toMillis() + "ms");
    } else {
      System.out.println("ERROR");
    }
  }


}
