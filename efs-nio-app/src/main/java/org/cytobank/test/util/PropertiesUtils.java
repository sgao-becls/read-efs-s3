package org.cytobank.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/** @author sgao */
public class PropertiesUtils {

  private static final ObjectMapper mapper =
      new ObjectMapper(new YAMLFactory()).findAndRegisterModules();

  /**
   * load properties from a yaml file
   *
   * @param clazz
   * @param yml
   * @param <T>
   * @return
   */
  public static <T> T loadProperties(Class<T> clazz, String yml) {
    try {
      InputStream inputStream = new FileInputStream("./" + yml);
//      InputStream inputStream = clazz.getResourceAsStream("/" + yml);
      return mapper.readValue(inputStream, clazz);
    } catch (IOException e) {
      e.printStackTrace();
     
      return null;
    }
  }
}
