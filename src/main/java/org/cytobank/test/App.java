package org.cytobank.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cytobank.test.dto.FioInput;
import org.cytobank.test.util.PropertiesUtils;

import java.util.logging.Logger;

public class App {

  private static final Logger log = Logger.getLogger(App.class.getName());
  private static final String CONFIG_FILE = "fio_input.yml";
  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  public static void main(String[] args) {
    log.info("fio start!");
    FioInput fioInput = PropertiesUtils.loadProperties(FioInput.class, CONFIG_FILE);
    log.info(GSON.toJson(fioInput));
    FioTestHandler fioTestHandler = new FioTestHandler();
    fioTestHandler.handleRequest(fioInput, null);
    log.info("fio done!");
  }

}
