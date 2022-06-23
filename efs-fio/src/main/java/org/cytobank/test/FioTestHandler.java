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
        , "--numjobs", input.getNumjobs()
        , "--bs", input.getBs()
        , "--filesize", input.getFilesize()
        , "--directory", input.getDirectory()
        , "--name", input.getName()
    );
    final List<String> command = processBuilder.command();
    input.getOtherArguments().forEach((key, value) -> {
      command.add(key);
      if (Objects.nonNull(value) && !value.isBlank()) {
        command.add(value);
      }
    });

    StringBuilder output = new StringBuilder();
    for (int i = 0; i < input.getTimes(); i++) {
      log.info(String.format("run %d times", i + 1));
      try {
        Process process;
        if (Objects.isNull(input.getCommandline()) || input.getCommandline().isEmpty()) {
          StringBuilder commandline = new StringBuilder();
          if (Objects.nonNull(command)) {
            command.forEach(c -> {
              if (c.startsWith("--")) {
                commandline.append(c);
                commandline.append("=");
              } else {
                commandline.append(c);
                commandline.append(" ");
              }
            });
          }

          if (input.isThread()) {
            commandline.append("--thread");
          }
          log.info(commandline.toString());
          process = processBuilder.start();
        } else {
          log.info(input.getCommandline());
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
    }
    log.info(output.toString());
    return output.toString();
  }
}