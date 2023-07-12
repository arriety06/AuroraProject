package AururaFactory;

import com.girlkun.services.Service;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* FormByArriety
 */
public class ThongBaoPlayerByForm extends JFrame {

    private static ThongBaoPlayerByForm i;
    private JTextField textField;
    private JButton confirmButton;

    public static ThongBaoPlayerByForm gI() {
        if (i == null) {
            i = new ThongBaoPlayerByForm();
        }
        return i;
    }

    public ThongBaoPlayerByForm() {
        initializeUI();
        setTitle("Thông Báo Toàn Server");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        textField = new JTextField(20);
        panel.add(textField);

        confirmButton = new JButton("Đồng ý");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                Service.getInstance().sendThongBaoAllPlayer(message);
//                System.err.println("Bạn Đã Thông Báo " + textField.getText());
//                JOptionPane.showMessageDialog(null, "Thông báo đã được gửi tới toàn bộ server.");
            }
        });
        panel.add(confirmButton);

        add(panel);
        pack();
    }

    public void run() {
        new ThongBaoPlayerByForm().setVisible(true);
    }
}
