package org.cytobank.test;

import org.cytobank.aws.s3client.util.S3Transfer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class UploadTest {

  private static final String USER_HOME = "/home/ec2-user";
  private static final String TARGET_PATH = "efs";

  /**
   * read from efs
   * @param filePath
   */
  public void uploadToEFS(String filePath) {
    Instant efsStartTime = Instant.now();
    String fileName = Paths.get(filePath).getFileName().toString();
    try (FileInputStream fileInputStream = new FileInputStream(Paths.get(filePath).toFile());
         FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(USER_HOME, TARGET_PATH, fileName).toFile())) {
      byte[] buffer = new byte[1024];
      int len;
      while ((len = fileInputStream.read(buffer)) > 0) {
        fileOutputStream.write(buffer, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.out.println("write file from efs uses " + Duration.between(efsStartTime, Instant.now()).toMillis() + " ms\n");
    }
  }

  /**
   * read from S3 transfer
   * it is the multiThread download
   * @param bucket
   * @param key
   */
  public void uploadToS3(String bucket, String key, String filePath) {
    Instant initS3TransferStart = Instant.now();
    S3Transfer s3Transfer = S3Transfer.getInstance();
    System.out.println("init s3 transfer uses " + Duration.between(initS3TransferStart, Instant.now()).toMillis() + " ms\n");

    Instant s3mStartTime = Instant.now();
    try {
      s3Transfer.uploadFile(bucket, key, Paths.get(filePath).toString());
    } finally {
      s3Transfer.shutDown();
      System.out.println("write file to s3, uses " + Duration.between(s3mStartTime, Instant.now()).toMillis() + " ms\n");
    }
  }

}
