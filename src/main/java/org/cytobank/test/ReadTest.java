package org.cytobank.test;

import org.cytobank.aws.s3client.util.S3Transfer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class ReadTest extends MultipleThreadTest {

  private static final Logger log = Logger.getLogger(ReadTest.class.getName());

  // File amount that we are using in the test
  private static final int FILE_AMOUNT = 10;

  // config of EFS
  private static final String EFS_MOUNT_PATH = "/mnt/channelstripe";

  // config of S3
  private static final String S3_SECOND_PATH = "fcs_6M_to_1G";

  public ReadTest() {
     super();
  }

  /**
   * read from efs
   *
   * @param filePrefix
   */
  public void readFromEFSSingleThread(String filePrefix) {
    Instant totalStart = Instant.now();
    for (int i = 0; i < FILE_AMOUNT; i++) {
      readFromEFS(filePrefix + "_" + i, i);
    }
    System.out.println("read file from efs uses " + Duration.between(totalStart, Instant.now()).toMillis() + " ms\n");
  }

  public void readFromEFSMultiple(String filePrefix) {
    Instant efsStartTime = Instant.now();
    CompletableFuture.allOf(
            IntStream.range(1, FILE_AMOUNT + 1)
                .boxed()
                .map(
                    i ->
                        CompletableFuture.runAsync(
                            () ->
                                readFromEFS(filePrefix + "_" + i, i), getExecutorService()))
                .toArray(CompletableFuture[]::new))
        .join();
    log.info("read file from efs, totally uses " + Duration.between(efsStartTime, Instant.now()).toMillis() + " ms\n");
  }

  private void readFromEFS(String filePrefix, int index) {
    Path sourcePathFromEFS = Paths.get(EFS_MOUNT_PATH, filePrefix + ".fcs");
    String fileName = sourcePathFromEFS.getFileName().toString();
    Instant start = Instant.now();
    try (FileInputStream fileInputStream = new FileInputStream(sourcePathFromEFS.toFile())) {
      byte[] buffer = new byte[1024];
      int len;
      int counter = 0;
      while ((len = fileInputStream.read(buffer)) > 0) {
//        fileOutputStream.write(buffer, 0, len);
        counter += len;
      }
      log.info(index + " - file size is " + counter);
    } catch (IOException e) {
      log.info(e.getMessage());
    } finally {
      log.info(index + " - read file from efs uses " + Duration.between(start, Instant.now()).toMillis() + " ms\n");
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
    Instant initS3TransferStart = Instant.now();
    S3Transfer s3Transfer = S3Transfer.getInstance();
    log.info("init s3 transfer uses " + Duration.between(initS3TransferStart, Instant.now()).toMillis() + " ms\n");
    Instant s3mStartTime = Instant.now();
    CompletableFuture.allOf(
            IntStream.range(1, FILE_AMOUNT + 1)
                .boxed()
                .map(
                    i ->
                        CompletableFuture.runAsync(
                            () ->
                                readFromS3(bucket, keyPrefix + "_" + i, s3Transfer), getExecutorService()))
                .toArray(CompletableFuture[]::new))
        .join();
    log.info("read file from s3 multiple download, uses " + Duration.between(s3mStartTime, Instant.now()).toMillis() + " ms\n");
  }

  public void readFromS3(String bucket, String keyPrefix, S3Transfer s3Transfer) {
    String fileName = keyPrefix + ".fcs";
    try (InputStream inputStream = s3Transfer.getS3ObjectMultiThreadInputStream(bucket, S3_SECOND_PATH + "/" + fileName)){
      byte[] buffer = new byte[1024];
      int len;
      int counter = 0;
      while ((len = inputStream.read(buffer)) > 0) {
        counter += len;
      }
      log.info("file size is " + counter);
    } catch (IOException e) {
      log.info(e.getMessage());
    } finally {
      s3Transfer.shutDown();
    }
  }

}
