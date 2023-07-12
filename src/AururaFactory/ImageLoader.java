/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AururaFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

    public static void main(String[] args) {
        int id = 5; // ID của ảnh cần hiển thị
        int zoom = 1; // Tỷ lệ phóng to (zoom level)

        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("data/girlkun/effdata/x" + zoom + "/" + id));
            dis.readShort(); // Bỏ qua giá trị không cần thiết
            int dataSize = dis.readInt();
            byte[] data = new byte[dataSize];
            dis.readFully(data);

            BufferedImage oriImage = ImageIO.read(new FileInputStream("data/girlkun/effdata/x" + zoom + "/" + id + ".png"));

            showImageFrames(data, zoom, oriImage);

            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showImageFrames(byte[] data, int zoom, BufferedImage oriImage) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bis);

            int nImageInfo = dis.readByte();
            BufferedImage[] imageInfo = new BufferedImage[nImageInfo];
            for (int i = 0; i < nImageInfo; i++) {
                int id = dis.readByte();
                int x = dis.readByte() * zoom;
                int y = dis.readByte() * zoom;
                int w = dis.readByte() * zoom;
                int h = dis.readByte() * zoom;
                imageInfo[i] = oriImage.getSubimage(x, y, w, h);
            }

            int nFrame = dis.readShort();
            for (int i = 0; i < nFrame; i++) {
                BufferedImage frame = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = frame.createGraphics();
                int nF = dis.readByte();
                for (int j = 0; j < nF; j++) {
                    int dx = dis.readShort() * zoom;
                    int dy = dis.readShort() * zoom;
                    int idImage = dis.readByte();
                    g.drawImage(imageInfo[idImage], 500 + dx, 500 + dy, null);
                }
                g.dispose();
                showImage(frame);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showImage(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame();
        JLabel label = new JLabel(icon);
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
