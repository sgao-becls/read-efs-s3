package org.cytobank.test;

import org.cytobank.aws.s3client.util.S3Transfer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class ReadTest {

  private static final Logger log = Logger.getLogger(ReadTest.class.getName());

  // File amount that we are using in the test
  private static final int FILE_AMOUNT = 16;

  // config of Test Host
  private static final String USER_HOME = "/home/ec2-user";
  private static final String EFS_TARGET_PATH = "efs_download_test";
  private static final String S3_TARGET_PATH = "s3_download_test";

  // config of EFS
  private static final String EFS_MOUNT_PATH = USER_HOME + "/efs";

  // config of S3
  private static final String S3_SECOND_PATH = "fcs_6M_to_1G";

  private ExecutorService executorService;

  public ReadTest() {
    try {
      Files.createDirectories(Paths.get(USER_HOME, EFS_TARGET_PATH));
      Files.createDirectories(Paths.get(USER_HOME, S3_TARGET_PATH));

      executorService =
          new ThreadPoolExecutor(
              16,
              16,
              0L,
              TimeUnit.MILLISECONDS,
              new LinkedBlockingQueue<>());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * read from efs
   *
   * @param filePrefix
   */
  public void readFromEFSSingleThread(String filePrefix) {
    Instant totalStart = Instant.now();
    for (int i = 0; i < FILE_AMOUNT; i++) {
      readFromEFS(filePrefix + "_" + i);
    }
    System.out.println("read file from efs uses " + Duration.between(totalStart, Instant.now()).toMillis() + " ms\n");
  }

  public void readFromEFSMultiple(String filePrefix) {
    CompletableFuture.allOf(
            IntStream.range(0, FILE_AMOUNT + 1)
                .boxed()
                .map(
                    i ->
                        CompletableFuture.runAsync(
                            () ->
                                readFromEFS(filePrefix + "_" + i), executorService))
                .toArray(CompletableFuture[]::new))
        .join();
  }

  private void readFromEFS(String filePrefix) {
    Path sourcePathFromEFS = Paths.get(EFS_MOUNT_PATH, filePrefix + ".fcs");
    String fileName = sourcePathFromEFS.getFileName().toString();
    Instant start = Instant.now();
    try (FileInputStream fileInputStream = new FileInputStream(sourcePathFromEFS.toFile());
         FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(USER_HOME, EFS_TARGET_PATH, fileName).toFile())) {
      byte[] buffer = new byte[1024];
      int len;
      while ((len = fileInputStream.read(buffer)) > 0) {
        fileOutputStream.write(buffer, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      log.info("read file from efs uses " + Duration.between(start, Instant.now()).toMillis() + " ms\n");
    }
  }


  /**
   * read from S3 transfer
   * it is the multiThread download
   *
   * @param bucket
   * @param keyPrefix
   */
  public void readFromS3SingleThread(String bucket, String keyPrefix) {
    Instant initS3TransferStart = Instant.now();
    S3Transfer s3Transfer = S3Transfer.getInstance();
    log.info("init s3 transfer uses " + Duration.between(initS3TransferStart, Instant.now()).toMillis() + " ms\n");
    Instant s3mStartTime = Instant.now();
    for (int i = 0; i < 16; i++) {
      readFromS3(bucket, keyPrefix + "_" + i, s3Transfer);
    }
    log.info("read file from s3 multiple download, uses " + Duration.between(s3mStartTime, Instant.now()).toMillis() + " ms\n");
  }

  public void readFromS3Multiple(String bucket, String keyPrefix) {
    S3Transfer s3Transfer = S3Transfer.getInstance();
    CompletableFuture.allOf(
            IntStream.range(0, FILE_AMOUNT + 1)
                .boxed()
                .map(
                    i ->
                        CompletableFuture.runAsync(
                            () ->
                                readFromS3(bucket, keyPrefix + "_" + i, s3Transfer), executorService))
                .toArray(CompletableFuture[]::new))
        .join();
  }

  public void readFromS3(String bucket, String keyPrefix, S3Transfer s3Transfer) {
    try {
      String fileName = keyPrefix + ".fcs";
      s3Transfer.downloadFileMultiThread(bucket, S3_SECOND_PATH + "/" + fileName + ".fcs", Paths.get(USER_HOME, S3_TARGET_PATH, fileName).toString());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      s3Transfer.shutDown();
    }
  }

}
