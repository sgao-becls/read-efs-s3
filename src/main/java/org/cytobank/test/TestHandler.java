package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.cytobank.test.dto.Input;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sgao
 */
public class TestHandler implements RequestHandler<Input, String> {

  private static final Logger log = Logger.getLogger(TestHandler.class.getName());

  @Override
  public String handleRequest(Input input, Context context) {
    if ("w".equalsIgnoreCase(input.getAction())) {
     return null;
    } else if ("r".equalsIgnoreCase(input.getAction())) {
      ReadTest readTest = new ReadTest();
      if (input.isEfsEnabled()) {
        readTest.readFromEFSMultiple(input.getFilePrefix());
      }
      if (input.isS3Enabled()) {
        readTest.readFromS3Multiple("fcs-file-test-data", input.getFilePrefix());
      }
    } else {
      log.log(Level.SEVERE, "action 'w' or 'r' is required");
    }
    return null;
  }
}