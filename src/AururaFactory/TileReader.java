/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AururaFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TileReader
 * 
 */
public class TileReader {
    public static void main(String[] args) {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("data/girlkun/map/tile_set_info"));
            PrintWriter writer = new PrintWriter("D:\\Aurura\\JsonSQL\\tile_real_beo.txt", "UTF-8");
            byte tileID = dis.readByte();
            int[][][] tileIndex = new int[tileID][][];
            int[][] tileType = new int[tileID][];
            for (int i = 0; i < tileID; i++) {
                byte numTileOfMap = dis.readByte();
                tileType[i] = new int[numTileOfMap];
                tileIndex[i] = new int[numTileOfMap][];
                for (int j = 0; j < numTileOfMap; j++) {
                    tileType[i][j] = dis.readInt();
                    byte numIndex = dis.readByte();
                    tileIndex[i][j] = new int[numIndex];
                    for (int k = 0; k < numIndex; k++) {
                        tileIndex[i][j][k] = dis.readByte();
                    }
                }
            }
            // Ghi dữ liệu vào file text
           for (int i = 0; i < tileIndex.length; i++) {
    writer.println("Tile ID: " + i);
    for (int j = 0; j < tileIndex[i].length; j++) {
        writer.println("Tile Type: " + tileType[i][j]);
        for (int k = 0; k < tileIndex[i][j].length; k++) {
            writer.println("Tile Index: " + tileIndex[i][j][k]);
        }
    }
}
writer.close();
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
