package org.cytobank.test;


public class App {
  public static void main(String[] args) {
    FileHandler fileHandler = new FileHandler();
    String path = "/Users/sgao/Downloads/30c516f159fcb80c3bea8192e6bce498c2afd77f0d8d321ea741cc0e61b31ce88aa20fe809fc9be72697916e2703a082c18f10d6c4c8ae580fd9faf76143f427_0";
    Input input = new Input();
    input.setPath(path);
    fileHandler.handleRequest(input, null);
  }
}
