package org.cytobank.test.dto;

import lombok.Data;

@Data
public class Input {

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

}
