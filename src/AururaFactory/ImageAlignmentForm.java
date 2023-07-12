/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AururaFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

public class ImageAlignmentForm extends JFrame {

    private JLabel[] imageLabels;
    private int[] imageX, imageY;
    private String[] imageIds;
    private int[] imageDx, imageDy;
//    private JLabel imageLabel;

    public ImageAlignmentForm(int numImages) {
        setTitle("Image Alignment Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        imageLabels = new JLabel[numImages];
        imageX = new int[numImages];
        imageY = new int[numImages];
        imageIds = new String[numImages];
        imageDx = new int[numImages];
        imageDy = new int[numImages];

        JPanel panel = new JPanel();
        panel.setLayout(null);

        for (int i = 0; i < numImages; i++) {
            imageLabels[i] = new JLabel();
            imageLabels[i].setIcon(new ImageIcon());
            imageLabels[i].setBounds(0, 0, 100, 100);
            panel.add(imageLabels[i]);
        }

        getContentPane().add(panel);

        setVisible(true);
    }

    public void loadImagesFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            int numImages = Math.min(files.length, imageLabels.length);

            for (int i = 0; i < numImages; i++) {
                File file = files[i];
                if (file.isFile() && file.getName().endsWith(".png")) {
                    ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                    imageLabels[i].setIcon(imageIcon);
                    imageLabels[i].setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
                    imageIds[i] = file.getName().replaceAll(".png", "");
                    imageDx[i] = 0;
                    imageDy[i] = 0;

                    imageLabels[i].addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                int index = getIndexFromLabel((JLabel) e.getSource());
                                int dx = (e.getX() - imageX[index]) / 5;
                                int dy = (e.getY() - imageY[index]) / 5;
                            } else if (SwingUtilities.isLeftMouseButton(e)) {
                                int index = getIndexFromLabel((JLabel) e.getSource());
                                String infoText = "Tên: " + imageIds[index] + " | dx: " + imageDx[index] + " | dy: " + imageDy[index];
                                JOptionPane.showMessageDialog(null, infoText, "Thông tin ảnh", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    });

                    imageLabels[i].addMouseMotionListener(new MouseMotionAdapter() {
                        public void mouseDragged(MouseEvent e) {
                            int index = getIndexFromLabel((JLabel) e.getSource());
                            if (index >= 0) {
                                int dx = (e.getX() - imageX[index]) / 4;
                                int dy = (e.getY() - imageY[index]) / 4;
                                imageLabels[index].setLocation(imageLabels[index].getX() + dx, imageLabels[index].getY() + dy);
                            }
                        }
                    });

                }
            }
        }
    }

    private int getIndexFromLabel(JLabel label) {
        for (int i = 0; i < imageLabels.length; i++) {
            if (imageLabels[i] == label) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageAlignmentForm form = new ImageAlignmentForm(32);
            form.loadImagesFromFolder("C:\\Users\\HT\\Desktop\\goku heroes\\goku heroes\\x4");
        });
    }
}
