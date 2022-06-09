package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cytobank.test.dto.FioInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
//    processBuilder.command("ls", "-l", "-a");
    StringBuilder jobAndFile = new StringBuilder();
    input.getJobAndFile().entrySet().forEach(entry->{
      jobAndFile.append("--name=");
      jobAndFile.append(entry.getKey());
      jobAndFile.append(" ");
      jobAndFile.append("--filename=");
      jobAndFile.append(entry.getValue());
    });

    processBuilder.command("fio"
        , jobAndFile.toString()
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
        , "--group_reporting");
    StringBuilder output = new StringBuilder();
    try {
      Process process = processBuilder.start();
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