package org.cytobank.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class App {

  private static final Logger log = Logger.getLogger(App.class.getName());

  public static void main(String[] args) {
//    String action = args[0];
//    String bucket = args[1];
//    String filePrefix = args[2];
//
//    ReadTest readTest = new ReadTest();
//    if(action.equalsIgnoreCase("w")) {
////      WriteTest uploadTest = new WriteTest();
////      uploadTest.writeToEFS(filePrefix);
////      uploadTest.writeToS3(bucket, filePrefix, filePrefix);
//    } else if(action.equalsIgnoreCase("r")) {
//
//      readTest.readFromEFSMultiple(filePrefix);
//      readTest.readFromS3Multiple(bucket, filePrefix);
//    } else if(action.equalsIgnoreCase("r1")) {
//      readTest.readFromEFSSingleThread(filePrefix);
//      readTest.readFromS3SingleThread(bucket, filePrefix);
//    } else {
//      return;
//    }


    log.info("Done!!");

    log.info("Hello world");
  }

}
