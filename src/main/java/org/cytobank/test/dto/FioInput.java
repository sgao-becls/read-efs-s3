package org.cytobank.test.dto;

import lombok.Data;

@Data
public class FioInput {
  String fileName;
  String ioengine = "libaio";
  String iodepth = "64";
  String readwrite = "randrw";
  String rwmixread = "75";
  String size = "4G";
}
