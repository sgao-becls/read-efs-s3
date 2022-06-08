package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.cytobank.test.dto.FioInput;
import org.cytobank.test.dto.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sgao
 */
public class FioTestHandler implements RequestHandler<FioInput, String> {

  private static final Logger log = Logger.getLogger(FioTestHandler.class.getName());

  @Override
  public String handleRequest(FioInput input, Context context) {
    ProcessBuilder processBuilder = new ProcessBuilder();
//    processBuilder.command("ls", "-l", "-a");
    processBuilder.command("fio"
        , "--name", input.getName()
        , "--filename", input.getFileName()
        , "--ioengine", input.getIoengine()
        , "--iodepth", input.getIodepth()
        , "--readwrite", input.getReadwrite()
        , "--rwmixread", input.getRwmixread()
        , "--numjobs", input.getNumjobs()
        , "--runtime", input.getRuntime()
        , "--bs", input.getBs()
        , "--nrfiles", input.getNrfiles()
        , "--loops", input.getLoops()
        , "--size", input.getSize());
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