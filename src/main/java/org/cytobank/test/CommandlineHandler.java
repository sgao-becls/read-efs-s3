package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * @author sgao
 */
public class CommandlineHandler implements RequestHandler<String, String> {

  private static final Logger log = Logger.getLogger(CommandlineHandler.class.getName());

  @Override
  public String handleRequest(String commandline, Context context) {
    log.info(commandline);
    StringBuilder output = new StringBuilder();
    try {
      Process process = Runtime.getRuntime().exec(commandline);
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }

      int exitVal = process.waitFor();
      if (exitVal == 0) {
        log.info("Success!!");
      } else {
        log.info("exit with code " + exitVal);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(output);
    return output.toString();
  }
}