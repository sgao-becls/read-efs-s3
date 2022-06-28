package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cytobank.test.dto.Input;
import org.cytobank.test.service.NioService;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * @author sgao
 */
public class NioTestHandler implements RequestHandler<Input, String> {

  private static final Logger log = Logger.getLogger(NioTestHandler.class.getName());
  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  @Override
  public String handleRequest(Input input, Context context) {
    System.out.println(GSON.toJson(input));
    Path[] stripeFilePaths = new Path[input.getNumFiles()];
    for (int i = 0; i < input.getNumFiles(); i++) {
      stripeFilePaths[i] = Path.of(input.getDirectory(), String.format("%s.%d", input.getNamePrefix(), i));
//      System.out.println(String.format("%d file path: %s", i, stripeFilePaths[i]));
    }
    try (NioService nioService = new NioService(input.getNumThreads(), input.getBufferSize())) {
      nioService.multipleReadFileSequentially(stripeFilePaths);
    }
    return "success";
  }
}