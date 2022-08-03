package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.cytobank.io.DoubleFile;
import org.cytobank.io.LargeFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

@Log
public class FileHandler implements RequestHandler<String, String> {

  private static final boolean LOG_ENABLED;
  private static final int READ_TIMES;

  static {
    String logEnabledStr = System.getenv().get("logEnabled");
    if (StringUtils.isBlank(logEnabledStr)) {
      LOG_ENABLED = false;
    } else {
      LOG_ENABLED = Boolean.parseBoolean(logEnabledStr);
    }

    String readTimesStr = System.getenv().get("readTimes");
    if (StringUtils.isBlank(logEnabledStr)) {
      READ_TIMES = 1;
    } else {
      READ_TIMES = Integer.parseInt(readTimesStr);
    }
  }

  private void checkAndPrint(String message) {
    if (LOG_ENABLED) {
      print(message);
    }
  }

  private void print(String message) {
    log.info(message);
  }

  @Override
  public String handleRequest(String path, Context context) {
    for (int i = 0; i < READ_TIMES; i++) {
      readFile(path);
    }
    return null;
  }

  private void readFile(String path) {
    Instant start = Instant.now();
    try (FileChannel fileChannel = new FileInputStream(Paths.get(path).toFile()).getChannel()) {
      DoubleFile doubleFile = new DoubleFile(fileChannel, LargeFile.READ_ONLY);
      long size = fileChannel.size();
      long eventsNumber = size / DoubleFile.BYTES_PER_DOUBLE;
      checkAndPrint(String.format("events number: %d", eventsNumber));
      for (int i = 0; i < eventsNumber; i++) {
        checkAndPrint(String.format("events[%d] = %f", i, doubleFile.get(i)));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    print(String.format("Used: %dms", Duration.between(start, Instant.now()).toMillis()));
  }
}
