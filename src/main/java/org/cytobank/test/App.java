package org.cytobank.test;


public class App {

  public static void main(String[] args) {
    String action = args[0];
    String bucket = args[1];
    String key = args[2];
    String targetFile = args[3];

    if(action.equalsIgnoreCase("u")) {
      UploadTest uploadTest = new UploadTest();
      uploadTest.uploadToEFS(targetFile);
      uploadTest.uploadToS3(bucket, key, targetFile);
    } else if(action.equalsIgnoreCase("d")) {
      DownloadTest downloadTest = new DownloadTest();
      downloadTest.downloadFromEFS(targetFile);
      downloadTest.downloadFromS3Transfer(bucket, key);
    } else {
      return;
    }
    System.out.println("Done!!");
  }

}
