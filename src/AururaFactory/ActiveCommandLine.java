package AururaFactory;

import AururaFactory.BanPlayerForm;
import AururaFactory.FormRecall;
import AururaFactory.GiveItemForm;
import AururaFactory.ThongBaoPlayerByForm;
import com.girlkun.network.server.GirlkunSessionManager;
import com.girlkun.server.Client;
import com.girlkun.server.Maintenance;
import com.girlkun.server.Manager;
import com.girlkun.services.ClanService;
import com.girlkun.utils.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActiveCommandLine extends JFrame {

    private static ActiveCommandLine instance;
    private JButton maintenanceButton;
    private JButton clientButton;
    private JButton playerButton;
    private JButton onlineButton;
    private JButton savePlayerButton;
    private JButton giveItemButton;
    private JButton thongBaoPlayerButton;
    private JButton banButton;
    private JButton thuHoiButton;
    private JButton Setting;
    private JButton active;

    public static ActiveCommandLine gI() {
        if (instance == null) {
            instance = new ActiveCommandLine();
        }
        return instance;
    }

    private ActiveCommandLine() {
        initializeUI();
        setTitle("Mew Mew");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void showPlayersOnline() {
        String message = "Số Người Online: " + Client.gI().getPlayers().size();
        JOptionPane.showMessageDialog(null, message);
    }

    private void initializeUI() {
        ImageIcon icon = new ImageIcon("icon.png");
        setIconImage(icon.getImage());
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(5, 2, 10, 10));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBackground(Color.BLUE); // Đặt màu nền của JPanel thành màu xanh lam
        panel.setBackground(new Color(0, 128, 128));

        maintenanceButton = new JButton("Bảo trì");
        maintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Maintenance.gI().start(60 * 2);
                System.out.println("Đã bắt đầu bảo trì");
                JOptionPane.showMessageDialog(null, "Đang Thực Thi Bảo Trì");
            }
        });
        panel.add(maintenanceButton);

        clientButton = new JButton("Session");
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Thông số" + GirlkunSessionManager.gI().getSessions().size());
            }
        });
        panel.add(clientButton);

        playerButton = new JButton("Số Người Online");
        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlayersOnline();
            }
        });
        panel.add(playerButton);

        onlineButton = new JButton("Save Dữ Liệu Clan");
        onlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClanService.gI().close();
                Logger.error("Save " + Manager.CLANS.size() + " bang");
                JOptionPane.showMessageDialog(null, "Đã Save" + Manager.CLANS.size() + " Clan");
            }
        });
        panel.add(onlineButton);

        savePlayerButton = new JButton("Save Dữ Liệu Player");
        savePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Đã Save" + Client.gI().getPlayers().size() + " Player");
                Client.gI().close();
            }
        });
        panel.add(savePlayerButton);

        giveItemButton = new JButton("Tặng vật phẩm");
        giveItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GiveItemForm giveItemForm = new GiveItemForm();
                giveItemForm.setVisible(true);
            }
        });
        panel.add(giveItemButton);

        thongBaoPlayerButton = new JButton("Thông Báo");
        thongBaoPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThongBaoPlayerByForm thongBao = new ThongBaoPlayerByForm();
                thongBao.setVisible(true);
            }
        });
        panel.add(thongBaoPlayerButton);

        banButton = new JButton("Ban & Unban Player");
        banButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BanPlayerForm ban = new BanPlayerForm();
                ban.setVisible(true);
            }
        });
        panel.add(banButton);
        
        active = new JButton("Kích Hoạt & Hủy Kích Hoạt Player");
        active.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActiveForm active = new ActiveForm();
                active.setVisible(true);
            }
        });
        panel.add(active);

        thuHoiButton = new JButton("Thu Hồi Item");
        thuHoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormRecall thuhoi = new FormRecall();
                thuhoi.setVisible(true);
            }
        });
        panel.add(thuHoiButton);
        
        Setting = new JButton("Setting");
        Setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingForm setting = new SettingForm();
                setting.setVisible(true);
            }
        });
        panel.add(Setting);

        add(panel);
        pack();
    }

    public void run() {
        new ActiveCommandLine().setVisible(true);
    }
}
