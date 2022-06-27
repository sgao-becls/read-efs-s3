package org.cytobank.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cytobank.test.dto.Input;
import org.cytobank.test.service.NioService;

import java.nio.file.Path;

public class App {

  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  public static void main(String[] args) {
    String x = "{\n" +
        "    \"namePrefix\": \"efs-nio-test\",\n" +
        "    \"numFiles\": 2,\n" +
        "    \"numThreads\": 2,\n" +
        "    \"directory\": \"/mnt/channelstripe\",\n" +
        "    \"bufferSize\": 5120\n" +
        "}";
    Input input = GSON.fromJson(x, Input.class);
    System.out.println(GSON.toJson(input));
    System.out.println(input.getNumFiles());
    Path[] stripeFilePaths = new Path[input.getNumFiles()];
    for (int i = 0; i < input.getNumFiles(); i++) {
      stripeFilePaths[i] = Path.of(input.getDirectory(), String.format("%s.%d", input.getNamePrefix(), i));
      System.out.printf("%d file path: %s \n", i, stripeFilePaths[i]);
    }
    NioService nioService = new NioService(input.getNumThreads(), input.getBufferSize());
    nioService.multipleReadFileSequentially(stripeFilePaths);
  }

}
