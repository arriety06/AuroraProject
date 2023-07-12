package AururaFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingForm extends JFrame {

    private JPanel settingsPanel;

    public SettingForm() {
        initializeUI();
        setTitle("Cài đặt");
//        setDefaultCloseOperation(JFrame.EXIT_ON_fCLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(settingsPanel);
        add(scrollPane);
        addSetting("Halloween", Setting.EVENT_HALLOWEEN);
        addSetting("Trung Thu", Setting.EVENT_TRUNG_THU);
        addSetting("Kênh Thế Giới", Setting.LOG_CHAT_GLOBAL);
        pack();
    }

    private void addSetting(String name, boolean enabled) {
        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.X_AXIS));

        JLabel nameLabel = new JLabel(name);
        settingPanel.add(nameLabel);

        JButton disableButton = new JButton("Tắt");
        JButton enableButton = new JButton("Bật");
        disableButton.setVisible(enabled);
        enableButton.setVisible(!enabled);

        disableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                disableButton.setVisible(false);
                enableButton.setVisible(true);
                if (name.equals("Halloween")) {
                    Setting.EVENT_HALLOWEEN = false;
                    JOptionPane.showMessageDialog(null, "Đã tắt sự kiện " + name);
                } else if (name.equals("Trung Thu")) {
                    Setting.EVENT_TRUNG_THU = false;
                    JOptionPane.showMessageDialog(null, "Đã tắt sự kiện " + name);
                } else if (name.equals("Kênh Thế Giới")) {
                    Setting.LOG_CHAT_GLOBAL = false;
                    JOptionPane.showMessageDialog(null, "Đã Tắt Chặn Chat " + name);
                }
            }
        });

        enableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                disableButton.setVisible(true);
                enableButton.setVisible(false);
                // Thực hiện hành động bật sự kiện ở đây
                if (name.equals("Halloween")) {
                    Setting.EVENT_HALLOWEEN = true;
                    JOptionPane.showMessageDialog(null, "Đã bật sự kiện " + name);
                } else if (name.equals("Trung Thu")) {
                    Setting.EVENT_TRUNG_THU = true;
                    JOptionPane.showMessageDialog(null, "Đã bật sự kiện " + name);
                } else if (name.equals("Kênh Thế Giới")) {
                    Setting.LOG_CHAT_GLOBAL = true;
                    JOptionPane.showMessageDialog(null, "Đã bật Chặn Chat" + name);
                }
            }
        });

        settingPanel.add(disableButton);
        settingPanel.add(enableButton);

        settingsPanel.add(settingPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SettingForm().setVisible(true);
            }
        });
    }
}
