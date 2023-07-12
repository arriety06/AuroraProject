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

public class GiveItemForm extends JFrame {

    private static GiveItemForm i;
    private JTextField nameField;
    private JTextField idField;
    private JTextField quantityField;
    private JButton giveItemButton;

    public static GiveItemForm gI() {
        if (i == null) {
            i = new GiveItemForm();
        }
        return i;
    }

    public GiveItemForm() {
        initializeUI();
        setTitle("Give Item Form");
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

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(quantityLabel);
        panel.add(quantityField);

        giveItemButton = new JButton("Tặng vật phẩm");
        giveItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText();
//                System.err.println("name " + playerName);
                int itemId = Integer.parseInt(idField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                Player pl = Client.gI().getPlayer(playerName);
//                Player p = (Player) PLAYERID_OBJECT.get(pl.id);
                if (pl != null) {
                    Item item = ItemService.gI().createNewItem((short) itemId);
                    item.quantity = quantity;
                    InventoryServiceNew.gI().addItemBag(pl, item);
                    InventoryServiceNew.gI().sendItemBags(pl);
                    Service.getInstance().sendThongBao(pl, "Nhận " + item.template.name + "\nSố lượng: " + item.quantity);
                    JOptionPane.showMessageDialog(null, "Đã tặng vật phẩm thành công cho người chơi " + playerName);
                } else {
                    JOptionPane.showMessageDialog(null, "Người chơi không online");
                }
            }
        });

        panel.add(new JLabel()); // Cột trống
        panel.add(giveItemButton);

        add(panel);
        pack();
    }

    public void run() {
        new GiveItemForm().setVisible(true);
    }
}
