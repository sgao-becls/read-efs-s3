package org.cytobank.test;

import org.cytobank.test.dto.FioInput;
import org.cytobank.test.util.PropertiesUtils;

import java.util.logging.Logger;

public class App {

  private static final Logger log = Logger.getLogger(App.class.getName());
  private static final String CONFIG_FILE = "fio_input.yml";

  public static void main(String[] args) {
    log.info("fio start!");
    FioInput fioInput = PropertiesUtils.loadProperties(FioInput.class, CONFIG_FILE);
    FioTestHandler fioTestHandler = new FioTestHandler();
    fioTestHandler.handleRequest(fioInput, null);
    log.info("fio done!");
  }

}
