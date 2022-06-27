package org.cytobank.test;

import org.cytobank.test.service.NioService;

import java.nio.file.Path;

public class App {

  public static void main(String[] args) {
    String stripefile = App.class.getClassLoader().getResource("stripefile").getPath();
    Path[] stripefilePaths = new Path[1];
    stripefilePaths[0] = Path.of(stripefile);
    NioService nioService = new NioService(5, 1024 * 5);
    nioService.multipleReadFileSequentially(stripefilePaths);

    System.exit(0);
  }

}
