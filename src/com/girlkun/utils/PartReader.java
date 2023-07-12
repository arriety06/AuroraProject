package com.girlkun.utils;

import java.io.*;
import java.util.*;
import org.json.simple.*;

public class PartReader {
    
//    public static final String FILE_PATH = "NR_part";
    public static final String FILE_PATH = "C:\\Users\\HT\\Desktop\\Dragonboy\\NR_part";
    public static final String OUTPUT_FILE_PATH = "C:\\Users\\HT\\Desktop\\Dragonboy\\output.sql";
    
    

    
    public static void main(String[] args) {
        List<Part> parts = readParts(FILE_PATH);
        String sql = createSql(parts);
        writeSqlToFile(sql, OUTPUT_FILE_PATH);
        System.out.println("Done!");
    }
    
    private static List<Part> readParts(String filePath) {
        List<Part> parts = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            int num = dis.readShort();
            for (int i = 0; i < num; i++) {
                int type = dis.readByte();
                int n = 0;
                if (type == 0) {
                    n = 3;
                } else if (type == 1) {
                    n = 17;
                } else if (type == 2) {
                    n = 14;
                } else if (type == 3) {
                    n = 2;
                }
                JSONArray pis = new JSONArray();
                for (int k = 0; k < n; k++) {
                    JSONArray pi = new JSONArray();
                    pi.add(Short.valueOf(dis.readShort()));
                    pi.add(Byte.valueOf(dis.readByte()));
                    pi.add(Byte.valueOf(dis.readByte()));
                    pis.add(pi);
                }
                parts.add(new Part(i, type, pis));
                System.out.println("ID Part " + i + " Type " + type + ", '" + pis.toJSONString() + "');");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parts;
    }
    
    private static String createSql(List<Part> parts) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("USE part;\n\n");
        sqlBuilder.append("DROP TABLE IF EXISTS part;\n");
        sqlBuilder.append("CREATE TABLE part (\n");
        sqlBuilder.append("  id INT NOT NULL,\n");
        sqlBuilder.append("  type INT NOT NULL,\n");
        sqlBuilder.append("  DATA JSON NOT NULL,\n");
        sqlBuilder.append("  PRIMARY KEY (id)\n");
        sqlBuilder.append(");\n\n");
        for (Part part : parts) {
            sqlBuilder.append("INSERT INTO part (id, type, DATA) VALUES (");
            sqlBuilder.append(part.getId()).append(", ");
            sqlBuilder.append(part.getType()).append(", ");
            sqlBuilder.append("'").append(part.getPi().toJSONString()).append("'");
            sqlBuilder.append(");\n");
        }
        return sqlBuilder.toString();
    }
    
    private static void writeSqlToFile(String sql, String filePath) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"))) {
            writer.write(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static class Part {
        private int id;
        private int type;
        private JSONArray pi;
        
        public Part(int id, int type, JSONArray pi) {
            this.id = id;
            this.type = type;
            this.pi = pi;
        }
        
        public int getId() {
            return id;
        }
        
        public int getType() {
            return type;
        }
        
        public JSONArray getPi() {
            return pi;
        }
    }
}
