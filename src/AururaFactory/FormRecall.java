package AururaFactory;

import com.girlkun.models.item.Item;
import com.girlkun.models.player.Player;
import com.girlkun.server.Client;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormRecall extends JFrame {

    private static FormRecall i;
    private JTextField nameField;
    private JTextField idField;
    private JTextField quantityField;
    private JButton giveItemButton;
    private JTextField recoveryQuantityField;
    private JButton recoveryItemButton;

    public static FormRecall gI() {
        if (i == null) {
            i = new FormRecall();
        }
        return i;
    }

    public FormRecall() {
        initializeUI();
        setTitle("Recall Item Form");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Tên người chơi:");
        nameField = new JTextField();
        JLabel idLabel = new JLabel("ID vật phẩm:");
        idField = new JTextField();
        JLabel quantityLabel = new JLabel("Số lượng:");
        quantityField = new JTextField();
        JLabel recoveryLabel = new JLabel("Thu hồi vật phẩm:");
        recoveryQuantityField = new JTextField();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(quantityLabel);
        panel.add(quantityField);

        giveItemButton = new JButton("Thu Hồi Vật Phẩm");
        giveItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText();
                String itemIdText = idField.getText();
                String quantityText = recoveryQuantityField.getText();
//
//                if (!itemIdText.isEmpty() && !quantityText.isEmpty()) {
//                    try {
                        int itemId = Integer.parseInt(itemIdText);
                        int quantity = Integer.parseInt(quantityText);

                        Player pl = Client.gI().getPlayer(playerName);
                        if (pl != null) {
                            Item item = ItemService.gI().createNewItem((short) itemId);
                            item.quantity = quantity;
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, quantity);
                            InventoryServiceNew.gI().sendItemBags(pl);
                            Service.getInstance().sendThongBao(pl, "Thu hồi " + item.template.name + "\nSố lượng: " + item.quantity);
                            JOptionPane.showMessageDialog(null, "Đã thu hồi vật phẩm thành công của người chơi " + playerName);
                        } else {
                            JOptionPane.showMessageDialog(null, "Người chơi không online");
                        }
//                    } catch (NumberFormatException ex) {
//                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số cho ID vật phẩm và số lượng");
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "Vui lòng nhập giá trị cho ID vật phẩm và số lượng");
//                }
            }
        });

        /*
        * Cột Trống
         */
        panel.add(
                new JLabel());
        panel.add(giveItemButton);

        add(panel);

        pack();
    }

    public void run() {
        new FormRecall().setVisible(true);
    }
}
