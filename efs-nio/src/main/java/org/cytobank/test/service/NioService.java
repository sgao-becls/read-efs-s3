package org.cytobank.test.service;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Recording log costs time.
 */

public class NioService implements Closeable {

  private static final Logger log = Logger.getLogger(NioService.class.getName());
  private final int bufferSize;

  private final ExecutorService executorService;

  public NioService(int nThreads, int bufferSize) {
    executorService = Executors.newFixedThreadPool(nThreads);
    this.bufferSize = bufferSize;
  }

  public void multipleReadFileSequentially(Path[] path) {
    int len = path.length;
    Instant start = Instant.now();
    try {
      CompletableFuture.allOf(IntStream.range(0, len).boxed()
              .map(
                  index ->
                      CompletableFuture.runAsync(
                          () -> readFileSequentially(path[index], index),
                          executorService)).toArray(CompletableFuture[]::new))
          .join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    long duration = Duration.between(start, Instant.now()).toMillis();
    System.out.println(String.format("Aggregated duration %dms", duration));
  }

  public void readFileSequentially(Path path, int index) {
    int fileSize = 0;
    Instant start = Instant.now();
    try (FileChannel channel = FileChannel.open(path,
        StandardOpenOption.READ)) {
      ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
//      DoubleBuffer doubleBuffer = buffer.asDoubleBuffer();
      int bytesRead;
      while ((bytesRead = channel.read(buffer)) != -1) {
        fileSize += bytesRead;
//        if (bytesRead > 0) {
//          while(doubleBuffer.hasRemaining()){
//            System.out.printf("  %f\n", doubleBuffer.get());
//          }
//        }
        buffer.rewind();
//        doubleBuffer.rewind();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    long durationTemp = Duration.between(start, Instant.now()).toMillis();
    long duration = durationTemp == 0l?1l:durationTemp;
    System.out.println(String.format("%d thread - file size: %dbytes, duration: %dms, throughput: %dKB/s\n", index, fileSize, duration, fileSize / 1024 / duration * 1000));
  }

  @Override
  public void close() {
    if(Objects.nonNull(executorService)) {
      executorService.shutdownNow();
    }
  }

}
