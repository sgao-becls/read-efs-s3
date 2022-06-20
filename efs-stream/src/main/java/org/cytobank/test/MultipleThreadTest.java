package org.cytobank.test;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class MultipleThreadTest {

  @Getter
  private final ExecutorService executorService;

  public MultipleThreadTest() {
    executorService =
        new ThreadPoolExecutor(
            16,
            16,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());
  }

}
