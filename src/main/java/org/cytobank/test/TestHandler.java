package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * @author sgao
 */
public class TestHandler implements RequestHandler<String, String> {

  @Override
  public String handleRequest(String filePrefix, Context context) {
    ReadTest readTest = new ReadTest();
    readTest.readFromEFSMultiple(filePrefix);
    readTest.readFromS3Multiple("fcs-file-test-data", filePrefix);
    return null;
  }
}