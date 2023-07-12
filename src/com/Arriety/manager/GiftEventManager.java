package com.Arriety.manager;

import com.Arriety.models.GiftEvent;
import com.Arriety.models.GiftEventTransaction;
import com.Arriety.models.GiftItem;
import com.Arriety.repositorys.GiftEventRepository;
import com.Arriety.repositorys.GiftEventTransactionRepository;
import com.Arriety.repositorys.IGiftEventRepository;
import com.Arriety.repositorys.IGiftEventTransactionRepository;
import com.girlkun.models.item.Item;
import com.girlkun.models.player.Player;
import com.girlkun.server.Client;
import com.girlkun.server.Manager;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


public class GiftEventManager {

    private static GiftEventManager i;

    private IGiftEventRepository giftEventRepository = new GiftEventRepository();
    private IGiftEventTransactionRepository giftEventTransactionRepository = new GiftEventTransactionRepository();
    private Gson gson = new Gson();

    public static GiftEventManager gI(){
        if(i == null){
            i = new GiftEventManager();
        }
        return i;
    }

    public void handleInputGiftEventCode(String code, Player player) {
        System.out.println("Handle enter giftcode: " + code);

        GiftEvent giftEvent = giftEventRepository.findGiftEventByCode(code);
        if(giftEvent == null) {
            Service.getInstance().sendThongBao(player, "Code không hợp lệ, vui lòng nhập code hợp lệ");
            return;
        }

        if(!giftEvent.isEnabled()) {
            Service.getInstance().sendThongBao(player, "Code đã bị khóa, vui lòng liên hệ admin để biết thêm chi tiết");
        }

//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        if(giftEvent.getExpiredDate() <= timestamp.getTime()) {
//            Service.getInstance().sendThongBao(player, "Code đã hết hạn, vui lòng nhật code hợp lệ");
//            return;
//        }
//
        GiftEventTransaction transaction = giftEventTransactionRepository.findGiftEventTransaction((int)player.id, giftEvent.getId());
        if(transaction != null) {
            Service.getInstance().sendThongBao(player, "Bạn đã sử dụng code này, vui lòng nhập mã code mới");
            return;
        }

        boolean resultCreateTransaction = giftEventTransactionRepository.createNewTransaction((int)player.id, giftEvent.getId(), giftEvent.getGiftCode());
        if(!resultCreateTransaction) {
            Service.getInstance().sendThongBao(player, "Có lỗi xảy ra khi nhập code, vui lòng thử lại sau");
            return;
        }

        boolean updateResult = giftEventRepository.updateRemainCode(giftEvent);
        System.out.println(giftEvent.getGiftBody());
        GiftItem [] giftItems = gson.fromJson(giftEvent.getGiftBody(), GiftItem[].class);

        handleItemsForPlayer(Arrays.asList(giftItems), (int)player.id);
    }

    private void addItemIdToItemBagForPlayer(Player plInGame, int quantity, int itemId, String name) {
        String message = "";
        if(plInGame.inventory != null && plInGame.inventory.itemsBag != null) {
            List<Item> items = plInGame.inventory.itemsBag;
            Item goldItem = null;
            for(int i = 0; i < items.size(); i++) {
                Item bagItem = items.get(i);
                if(bagItem != null && bagItem.template != null && bagItem.template.id == itemId) {
                    goldItem = bagItem;
                    System.out.println(String.format("Find bagItem %d", itemId));
                }
            }
            if(goldItem != null) {
                goldItem.quantity += quantity;
                message = String.format("Bạn vừa được cộng %d %s. Chúc bạn chơi game vui vẻ...", quantity, name);
                InventoryServiceNew.gI().sendItemBags(plInGame);
                Service.getInstance().sendThongBao(plInGame, message);
            } else {
                Item newItem = ItemService.gI().createNewItem((short)itemId, quantity);
                InventoryServiceNew.gI().addItemBag(plInGame, newItem);
                InventoryServiceNew.gI().sendItemBags(plInGame);
                message = String.format("Bạn vừa được cộng %d %s. Chúc bạn chơi game vui vẻ...", quantity, name);
                Service.getInstance().sendThongBao(plInGame, message);
            }
        }
    }
    private void handleItemsForPlayer(List<GiftItem> items, int playerId) {
        if(items.size() == 0) {
            return;
        }

        items.forEach(new Consumer<GiftItem>() {
            @Override
            public void accept(GiftItem giftItem) {
                Player plInGame = Client.gI().getPlayer(playerId);
                if(plInGame == null) {
                    return;
                }

                System.out.println(String.format("Process item: [%d] [%s] [%d]", giftItem.getId(), giftItem.getName(), giftItem.getQuantity()));
                String message = "";
                int quantity = giftItem.getQuantity();

                switch (giftItem.getId()) {
                    case 76:
                        plInGame.inventory.gold += quantity;
                        Service.getInstance().sendMoney(plInGame);
                        message = String.format("Bạn vừa được cộng %d vàng. Chúc bạn chơi game vui vẻ..", giftItem.getQuantity());
                        Service.getInstance().sendThongBao(plInGame, message);
                        break;
                    case 861:
                        plInGame.inventory.gem += quantity;
                        Service.getInstance().sendMoney(plInGame);
                        message = String.format("Bạn vừa được cộng %d ngọc. Chúc bạn chơi game vui vẻ..", giftItem.getQuantity());
                        Service.getInstance().sendThongBao(plInGame, message);
                        break;
                    case 457:
                    case 224:
                        addItemIdToItemBagForPlayer(plInGame, quantity, giftItem.getId(), giftItem.getName());
                        break;
                    default:
                        break;
                }
            }
        });
    }
}


