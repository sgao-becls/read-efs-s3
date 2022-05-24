package org.cytobank.test;


import java.util.logging.Logger;

public class App {

  private static final Logger log = Logger.getLogger(App.class.getName());

  public static void main(String[] args) {
    String action = args[0];
    String bucket = args[1];
    String filePrefix = args[2];

    if(action.equalsIgnoreCase("w")) {
//      WriteTest uploadTest = new WriteTest();
//      uploadTest.writeToEFS(filePrefix);
//      uploadTest.writeToS3(bucket, filePrefix, filePrefix);
    } else if(action.equalsIgnoreCase("r")) {
      ReadTest readTest = new ReadTest();
      readTest.readFromEFSMultiple(filePrefix);
      readTest.readFromS3Multiple(bucket, filePrefix);
    } else {
      return;
    }
    log.info("Done!!");

    log.info("Hello world");
  }

}
