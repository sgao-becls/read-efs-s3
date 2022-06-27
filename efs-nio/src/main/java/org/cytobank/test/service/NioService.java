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
    Instant start = Instant.now();
    try {
      CompletableFuture.allOf(IntStream.range(0, path.length).boxed()
              .map(
                  index ->
                      CompletableFuture.runAsync(
                          () -> readFileSequentially(path[index], index),
                          executorService)).toArray(CompletableFuture[]::new))
          .join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(String.format("Totally uses %dms", Duration.between(start, Instant.now()).toMillis()));
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
    System.out.println(String.format("%d thread - file size: %dbytes, uses: %dms\n", index, fileSize, Duration.between(start, Instant.now()).toMillis()));
  }

  @Override
  public void close() {
    if(Objects.nonNull(executorService)) {
      executorService.shutdownNow();
    }
  }

}
