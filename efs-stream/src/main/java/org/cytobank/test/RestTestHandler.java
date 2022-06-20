package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sgao
 */
public class RestTestHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

  private static final Logger log = Logger.getLogger(RestTestHandler.class.getName());

  @Override
  public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
    Map<String, String> queryStringParameters = event.getQueryStringParameters();
    String action = queryStringParameters.get("action");
    boolean isEfsEnabled = Boolean.parseBoolean(queryStringParameters.get("efsEnabled"));
    boolean isS3Enabled = Boolean.parseBoolean(queryStringParameters.get("s3Enabled"));
    String filePrefix = queryStringParameters.get("filePrefix");
    if ("r".equalsIgnoreCase(action)) {
      ReadTest readTest = new ReadTest();
      if (isEfsEnabled) {
        log.info("Reading from efs");
        readTest.readFromEFSMultiple(filePrefix);
      }
      if (isS3Enabled) {
        log.info("Reading from s3");
        readTest.readFromS3Multiple("fcs-file-test-data", filePrefix);
      }
    } else {
      log.log(Level.SEVERE, "action 'w' or 'r' is required");
    }
    APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
    response.setBody(event.getRawQueryString());
    response.setStatusCode(200);
    return response;
  }
}