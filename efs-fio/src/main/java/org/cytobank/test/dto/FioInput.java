package org.cytobank.test.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FioInput {

  // job name & filename
  // fio --name=job1 --filename=/mnt/job1_file --name=job2 --filename=/mnt/job2_file
  Map<String, String> jobAndFile = new HashMap<>();

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
  String readwrite = "randrw";
  // when read & write, the IO rate of reading
  String rwmixread = "75";

  // test file size, ${nrfiles} files share 4G size
  String size = "4G";

  // set file size for each job
  String filesize = "32M";

  // job loop times
  String loops = "1";
  // The number of thread
  String numjobs = "1";
  // running time in second of current test, if not set the whole ${size} file will be processed
  String runtime = "300";
  // block size, default is 4K
  String bs = "4k";

  // Lock memory, can only use 10G RAM; we have not used it so far.
  String lockmem = "10G";

  boolean thread = true;

  // number of test files
  String nrfiles = "1";

  // target directory for testing
  String directory;

  // job name
  String name = "test_job";

  // target file name for testing
  String filename;

  // command line
  String commandline;
}
