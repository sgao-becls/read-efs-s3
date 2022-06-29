package org.cytobank.test;

import lombok.Data;

@Data
public class NioInput {

  int numRequests;

  /**
   *  The prefix of file.
   *  Default file name format is ${namePrefix}.indexOf${numFiles}
   */
  String namePrefix;

  /**
   * Number of files
   */
  int numFiles;

  /**
   * Number of threads
   */
  int numThreads;

  /**
   * The directory that puts the test files.
   */
  String directory;

  /**
   * buffer size of bytebuffer
   */
  int bufferSize = 1024 * 5;

  /**
   * individual file size
   */
  int fileSize;
}
