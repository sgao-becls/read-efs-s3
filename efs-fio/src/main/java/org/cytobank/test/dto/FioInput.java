package org.cytobank.test.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FioInput {

  // command line
  String commandline;
  // how many times do the commandline run?
  int times = 0;

  // target directory for testing
  String directory;

  // set file size for each job
  String filesize = "32M";

  // The number of thread
  String numjobs = "1";

  // other arguments of fio
  Map<String, String> otherArguments = new HashMap<>();

  // io engine
  // - Linux native asynchronous I/O
  String ioengine = "libaio";
  //
  String iodepth = "8";

  // read or write
  // read
  // write
  // randread
  // randwrite
  // rw    -- read & write
  String readwrite = "read";

  // block size, default is 4K
  String bs = "1M";

  // thread or fork
  boolean thread = true;

  // job name
  String name = "m_thread_m_file_";

  /*******************/

  // when read & write, the IO rate of reading
  String rwmixread = "75";

  // test file size, ${nrfiles} files share 4G size
  String size = "4G";

  // job loop times
  String loops = "1";

  // running time in second of current test, if not set the whole ${size} file will be processed
  String runtime = "300";

  // Lock memory, can only use 10G RAM; we have not used it so far.
  String lockmem = "10G";

  // number of test files
  String nrfiles = "1";

  // target file name for testing
  String filename;
}
