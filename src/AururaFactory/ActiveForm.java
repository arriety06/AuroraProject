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

public class ActiveForm extends JFrame {

    private JTextField playerNameTextField;
    private JButton banButton;
    private JButton unbanButton; // Nút Mở Kích Hoạt

    public ActiveForm() {
        setTitle("Ban Player");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        playerNameTextField = new JTextField(20);
        add(playerNameTextField);

        banButton = new JButton("Kích Hoạt Tài Khoản");
        banButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameTextField.getText();
                Player pl = Client.gI().getPlayer(playerName);
                if (!playerName.isEmpty()) {
                    Active(playerName);
                    Service.gI().LinkService(pl, 1139, "|2|TÀi KHOẢN BẠN ĐÃ ĐƯỢC KÍCH HOẠT", "http://nrostyle.online//", "web");
                }
            }
        });
        add(banButton);

        unbanButton = new JButton("Hủy Kích Hoạt");
        unbanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameTextField.getText();
                if (!playerName.isEmpty()) {
                    unActive(playerName);
                }
            }
        });
        add(unbanButton);

        pack();
        setVisible(true);
    }

    private void Active(String playerName) {
        try {
            GirlkunDB.executeUpdate("UPDATE account SET active = 1 WHERE username = ?", playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unActive(String playerName) {
        try {
            GirlkunDB.executeUpdate("UPDATE account SET active = 0 WHERE username = ?", playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ActiveForm();
            }
        });
    }
}
