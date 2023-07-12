package AururaFactory;

import com.girlkun.database.GirlkunDB;
import static com.girlkun.models.npc.NpcFactory.PLAYERID_OBJECT;
import com.girlkun.models.player.Player;
import com.girlkun.server.Client;
import com.girlkun.services.Service;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BanPlayerForm extends JFrame {

    private JTextField playerNameTextField;
    private JButton banButton;
    private JButton unbanButton; // Nút mở ban player

    public BanPlayerForm() {
        setTitle("Ban Player");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        playerNameTextField = new JTextField(20);
        add(playerNameTextField);

        banButton = new JButton("Ban");
        banButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameTextField.getText();
                Player pl = Client.gI().getPlayer(playerName);
                if (!playerName.isEmpty()) {
                    banPlayer(playerName);
//                    Service.gI().LinkService(pl, 1139, "|2|TÀI KHOẢN BẠN ĐÃ BỊ KHÓA DÀI HẠN HOẶC NGẮN HẠN!\n|7|VUI LÒNG LIÊN HỆ ADMIN ĐỂ BIẾT THÊM THÔNG TIN", "https://xvideos98.xxx/?k=phim+sexx", "ZALO");
                }
            }
        });
        add(banButton);

        unbanButton = new JButton("Unban");
        unbanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameTextField.getText();
                if (!playerName.isEmpty()) {
                    unbanPlayer(playerName);
                }
            }
        });
        add(unbanButton);

        pack();
        setVisible(true);
    }

    private void banPlayer(String playerName) {
        try {
            GirlkunDB.executeUpdate("UPDATE account SET ban = 1 WHERE username = ?", playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unbanPlayer(String playerName) {
        try {
            GirlkunDB.executeUpdate("UPDATE account SET ban = 0 WHERE username = ?", playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BanPlayerForm();
            }
        });
    }
}
