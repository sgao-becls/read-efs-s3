package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.cytobank.io.DoubleFile;
import org.cytobank.io.LargeFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

@Log
public class FileHandler implements RequestHandler<String, String> {

  private static final boolean isLogEnabled;

  static {
    String isLogEnabledStr = System.getenv().get("logEnabled");
    if (StringUtils.isBlank(isLogEnabledStr)) {
      isLogEnabled = false;
    } else {
      isLogEnabled = Boolean.parseBoolean(isLogEnabledStr);
    }
  }

  private void checkAndPrint(String message) {
    if (isLogEnabled) {
      print(message);
    }
  }

  private void print(String message) {
    log.info(message);
  }

  @Override
  public String handleRequest(String path, Context context) {
    Instant start = Instant.now();
    try (FileChannel fileChannel = new FileInputStream(Paths.get(path).toFile()).getChannel()) {
      DoubleFile doubleFile = new DoubleFile(fileChannel, LargeFile.READ_ONLY);
      long size = fileChannel.size();
      long eventsNumber = size / DoubleFile.BYTES_PER_DOUBLE;
      checkAndPrint(String.format("events number: %d", eventsNumber));
      for (int i = 0; i < eventsNumber; i++) {
        checkAndPrint(String.format("events[%d] = %f", i, doubleFile.get(i)));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    print(String.format("Userd: %dms", Duration.between(start, Instant.now()).toMillis()));
    return null;
  }
}
