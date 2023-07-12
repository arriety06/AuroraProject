package AururaFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class TileDataBuilder {

    public static void main(String[] args) {
        String inputFile = "D:\\Aurura\\JsonSQL\\tile_real.txt";
        String outputFile = "tile_set_info_build";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFile));

            int tileID = Integer.parseInt(reader.readLine().split(": ")[1]);

            dos.writeByte(tileID);

            for (int i = 0; i < tileID; i++) {
                reader.readLine(); // Bỏ qua dòng "Tile ID: i"
                int numTileOfMap = Integer.parseInt(reader.readLine().split(": ")[1]);

                dos.writeByte(numTileOfMap);

                for (int j = 0; j < numTileOfMap; j++) {
                    reader.readLine(); // Bỏ qua dòng "Tile Type: ..."
                    int numIndex = Integer.parseInt(reader.readLine().split(": ")[1]);

                    dos.writeInt(Integer.parseInt(reader.readLine().split(": ")[1]));
                    dos.writeByte(numIndex);

                    for (int k = 0; k < numIndex; k++) {
                        dos.writeByte(Integer.parseInt(reader.readLine().split(": ")[1]));
                    }
                }
            }

            reader.close();
            dos.close();

            System.out.println("File " + outputFile + " has been successfully built.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
