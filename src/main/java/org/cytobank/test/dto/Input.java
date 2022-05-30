package org.cytobank.test.dto;

import lombok.Data;

@Data
public class Input {

  String filePrefix;

  String action;

  boolean efsEnabled;

  boolean s3Enabled;

}
