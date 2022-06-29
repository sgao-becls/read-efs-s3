package org.cytobank.test;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class NioConfig {

  String functionName;

  String active;

  Map<String, NioInput> inputMap = new HashMap<>();

  boolean log;
  
  int threadsPoolSize;

  public NioInput getActiveNioInput() {
    return inputMap.get(active);
  }

}
