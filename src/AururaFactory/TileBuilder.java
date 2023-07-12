package AururaFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TileBuilder
 * 
 */
public class TileBuilder {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\Aurura\\Sao\\tile_real.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Aurura\\Sao\\tile_set_info"));
            String line;
            byte tileID = -1;
            int currentTileID = -1;
            int[][][] tileIndex = null;
            int[][] tileType = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Tile ID:")) {
                    currentTileID = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                    if (currentTileID > tileID) {
                        tileID = (byte) currentTileID;
                        tileIndex = new int[tileID + 1][][];
                        tileType = new int[tileID + 1][];
                    }
                } else if (line.startsWith("Tile Type:")) {
                    int tileTypeValue = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                    tileType[currentTileID] = new int[] { tileTypeValue };
                } else if (line.startsWith("Tile Index:")) {
                    int tileIndexValue = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                    tileIndex[currentTileID] = new int[][] { { tileIndexValue } };
                }
            }
            writer.write(tileID + "\n");
            for (int i = 0; i <= tileID; i++) {
                int numTileOfMap = tileType[i].length;
                writer.write(numTileOfMap + "\n");
                for (int j = 0; j < numTileOfMap; j++) {
                    writer.write(tileType[i][j] + "\n");
                    int numIndex = tileIndex[i][j].length;
                    writer.write(numIndex + "\n");
                    for (int k = 0; k < numIndex; k++) {
                        writer.write(tileIndex[i][j][k] + "\n");
                    }
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}