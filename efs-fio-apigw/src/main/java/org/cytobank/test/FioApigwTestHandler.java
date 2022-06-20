package org.cytobank.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cytobank.test.dto.FioInput;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

/**
 * @author sgao
 */
public class FioApigwTestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private static final Logger log = Logger.getLogger(FioApigwTestHandler.class.getName());
  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
    String body = apiGatewayProxyRequestEvent.getBody();
    log.info(body);
    FioInput fioInput = GSON.fromJson(body, FioInput.class);
    FioTestHandler fioTestHandler = new FioTestHandler();
    Instant startTime = Instant.now();
    String responseBody = fioTestHandler.handleRequest(fioInput, context);
    log.info("uses " + Duration.between(startTime, Instant.now()).toMillis() + "ms");

    APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
    responseEvent.setBody(responseBody);
    responseEvent.setStatusCode(200);
    return responseEvent;
  }
}