package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cytobank.test.dto.FioInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author sgao
 */
public class FioTestHandler implements RequestHandler<FioInput, String> {

  private static final Logger log = Logger.getLogger(FioTestHandler.class.getName());
  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  @Override
  public String handleRequest(FioInput input, Context context) {
    log.info(GSON.toJson(input));

    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command("fio"
        , "--ioengine", input.getIoengine()
        , "--iodepth", input.getIodepth()
        , "--readwrite", input.getReadwrite()
        , "--rwmixread", input.getRwmixread()
        , "--numjobs", input.getNumjobs()
        , "--runtime", input.getRuntime()
        , "--bs", input.getBs()
        , "--nrfiles", input.getNrfiles()
        , "--loops", input.getLoops()
        , "--size", input.getSize()
        , "--filesize", input.getFilesize()
        , "--directory", input.getDirectory()
    );
    final List<String> command = processBuilder.command();
    input.getJobAndFile().forEach((key, value) -> {
      command.add("--name");
      command.add(key);
      command.add("--filename");
      command.add(value);
    });
    if (input.isThread()) {
      command.add("--thread");
    }

    StringBuilder output = new StringBuilder();
    try {
      Process process;
      if (Objects.isNull(input.getCommandline()) || input.getCommandline().isEmpty()) {
        StringBuilder commandline = new StringBuilder();
        command.forEach(c -> {
          if (c.startsWith("--")) {
            commandline.append(c);
            commandline.append("=");
          } else {
            commandline.append(c);
            commandline.append(" ");
          }
        });

        if (input.isThread()) {
          commandline.append("--thread");
        }
        System.out.println(commandline);
        process = processBuilder.start();
      } else {
        System.out.println(input.getCommandline());
        process = Runtime.getRuntime().exec(input.getCommandline());
      }
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