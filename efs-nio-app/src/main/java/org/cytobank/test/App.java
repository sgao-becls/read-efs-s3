package org.cytobank.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cytobank.test.service.NioService;
import org.cytobank.test.util.PropertiesUtils;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class App {

  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  public static void main(String[] args) {
    final NioConfig nioConfig = PropertiesUtils.loadProperties(NioConfig.class, "config.yml");
    final NioInput nioInput = nioConfig.getActiveNioInput();
    System.out.println(GSON.toJson(nioConfig));
    final Path[] stripeFilePaths = new Path[nioInput.getNumFiles()];
    for (int i = 0; i < nioInput.getNumFiles(); i++) {
      stripeFilePaths[i] = Path.of(nioInput.getDirectory(), String.format("%s.%d", nioInput.getNamePrefix(), i));
    }

    ExecutorService executorService = Executors.newFixedThreadPool(nioConfig.getThreadsPoolSize());
    Instant start = Instant.now();
    try {
      CompletableFuture.allOf(IntStream.range(0, nioInput.getNumRequests()).boxed()
              .map(
                  index ->
                      CompletableFuture.runAsync(
                          () -> readFiles(nioInput, stripeFilePaths),
                          executorService)).toArray(CompletableFuture[]::new))
          .join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    long duration = Duration.between(start, Instant.now()).toMillis();
    System.out.println(String.format("Aggregated duration %dms", duration));
    executorService.shutdownNow();
  }

  private static void readFiles(NioInput input, Path[] paths) {
    try (NioService nioService = new NioService(input.getNumThreads(), input.getBufferSize())) {
      nioService.multipleReadFileSequentially(paths);
    }
  }

}
