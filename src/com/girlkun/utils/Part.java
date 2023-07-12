package com.girlkun.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.json.simple.JSONArray;

public class Part {
  public static byte[] loadFile(String url) {
    try {
      FileInputStream openFileInput = new FileInputStream(url);
      byte[] data = new byte[openFileInput.available()];
      openFileInput.read(data);
      openFileInput.close();
      return data;
    } catch (IOException iOException) {
      return null;
    } 
  }
  
  public static void main(String[] args) throws IOException {
    byte[] data = loadFile ("C:\\Users\\HT\\Desktop\\Dragonboy\\NR_part");
//    File outputFile = new File("output.sql");
    DataInputStream d = new DataInputStream(new ByteArrayInputStream(data));
    int num = d.readShort();
    for (int i = 0; i < num; i++) {
      int type = d.readByte();
      int n = 0;
      if (type == 0)
        n = 3; 
      if (type == 1)
        n = 17; 
      if (type == 2)
        n = 14; 
      if (type == 3)
        n = 2; 
      JSONArray pis = new JSONArray();
      for (int k = 0; k < n; k++) {
        JSONArray pi = new JSONArray();
        pi.add(Short.valueOf(d.readShort()));
        pi.add(Byte.valueOf(d.readByte()));
        pi.add(Byte.valueOf(d.readByte()));
        pis.add(pi);
      } 
      System.out.println("INSERT INTO part(`id`,`type`,`pi`) VALUES (" + i + ", " + type + ", '" + pis.toJSONString() + "');\n");
    } 
  }
}
