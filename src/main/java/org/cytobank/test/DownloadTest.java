package org.cytobank.test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.cytobank.aws.s3client.util.S3ClientHolder;
import org.cytobank.aws.s3client.util.S3Transfer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class DownloadTest {

  private static final String USER_HOME = "/home/ec2-user";
  private static final String TARGET_PATH = "read_test";
  private static final String DOWNLOAD_EFS_FILE_NAME = "efs_file";
  private static final String DOWNLOAD_S3_CLIENT_FILE_NAME = "s3_file";
  private static final String DOWNLOAD_S3_TRANSFER_FILE_NAME = "s3_M_file";

  /**
   * read from efs
   * @param filePath
   */
  public void downloadFromEFS(String filePath) {
    Instant efsStartTime = Instant.now();
    try (FileInputStream fileInputStream = new FileInputStream(Paths.get(filePath).toFile());
         FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(USER_HOME, TARGET_PATH, DOWNLOAD_EFS_FILE_NAME).toFile())) {
      byte[] buffer = new byte[1024];
      int len;
      while ((len = fileInputStream.read(buffer)) > 0) {
        fileOutputStream.write(buffer, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.out.println("read file from efs uses " + Duration.between(efsStartTime, Instant.now()).toMillis() + " ms\n");
    }
  }

  /**
   * read from S3
   * @param bucket
   * @param key
   */
  public void downloadFromS3Client(String bucket, String key) {
    Instant initS3ClientStart = Instant.now();
    AmazonS3 s3Client = S3ClientHolder.getInstance().getS3Client();
    System.out.println("init s3 client uses " + Duration.between(initS3ClientStart, Instant.now()).toMillis() + " ms\n");

    Instant s3StartTime = Instant.now();
    try (S3Object s3Object = s3Client.getObject(bucket, key);
         FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(USER_HOME, TARGET_PATH, DOWNLOAD_S3_CLIENT_FILE_NAME).toFile())) {
      byte[] buffer = new byte[1024];
      S3ObjectInputStream input = s3Object.getObjectContent();
      int len;
      while ((len = input.read(buffer)) > 0) {
        fileOutputStream.write(buffer, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.out.println("read file from s3 uses " + Duration.between(s3StartTime, Instant.now()).toMillis() + " ms\n");
    }
  }

  /**
   * read from S3 transfer
   * it is the multiThread download
   * @param bucket
   * @param key
   */
  public void downloadFromS3Transfer(String bucket, String key) {
    Instant initS3TransferStart = Instant.now();
    S3Transfer s3Transfer = S3Transfer.getInstance();
    System.out.println("init s3 transfer uses " + Duration.between(initS3TransferStart, Instant.now()).toMillis() + " ms\n");

    Instant s3mStartTime = Instant.now();
    try {
      s3Transfer.downloadFileMultiThread(bucket, key, Paths.get(USER_HOME, TARGET_PATH, DOWNLOAD_S3_TRANSFER_FILE_NAME).toString());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.out.println("read file from s3 multiple download, uses " + Duration.between(s3mStartTime, Instant.now()).toMillis() + " ms\n");
    }
  }
}
