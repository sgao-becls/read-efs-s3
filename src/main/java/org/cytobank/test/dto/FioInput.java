package org.cytobank.test.dto;

import lombok.Data;

@Data
public class FioInput {
  // Pointing to a file in testing device
  String fileName;

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

  // job name
  String name = "lambda-efs-test";
  // job loop times
  String loops = "1";
  // The number of thread
  String numjobs = "20";
  // running time of current test, if not set the whole ${size} file will be processed
  String runtime = "600";
  // block size, default is 4K
  String bs = "4k";

  // Lock memory, can only use 10G RAM; we have not used it so far.
  String lockmem = "10G";

  // number of test files
  String nrfiles = "1";

}
