package org.cytobank.test;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FioInput {

  // target directory for testing
  String directory;

  // set file size for each job
  String filesize = "32M";

  // The number of thread
  String numjobs = "1";

  // other arguments of fio
  Map<String, String> otherArguments = new HashMap<>();

  // block size, default is 4K
  String bs = "1M";

  // job name
  String name = "m_thread_m_file_";

  int times = 1;
}
