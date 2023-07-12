package com.girlkun.models.npc.specialnpc;

import com.girlkun.models.item.Item;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.services.PetService;
import com.girlkun.models.player.Player;
import com.girlkun.utils.Util;
import com.girlkun.network.io.Message;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.girlkun.utils.Logger;

public class EggLinhThu {

    private static final long DEFAULT_TIME_DONE = 1209600000;

    private Player player;
    public long lastTimeCreate;
    public long timeDone;

    private final short id = 50; //id npc

    public EggLinhThu(Player player, long lastTimeCreate, long timeDone) {
        this.player = player;
        this.lastTimeCreate = lastTimeCreate;
        this.timeDone = timeDone;

    }

    public static void createEggLinhThu(Player player) {
        player.egglinhthu = new EggLinhThu(player, System.currentTimeMillis(), DEFAULT_TIME_DONE);
    }

    public void sendEggLinhThu() {
        Message msg;
        try {
            msg = new Message(-122);
            msg.writer().writeShort(this.id);
            msg.writer().writeByte(1);
            msg.writer().writeShort(15074);
            msg.writer().writeByte(0);
            msg.writer().writeInt(this.getSecondDone());
            this.player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(EggLinhThu.class, e);
        }
    }

    public int getSecondDone() {
        int seconds = (int) ((lastTimeCreate + timeDone - System.currentTimeMillis()) / 1000);
        return seconds > 0 ? seconds : 0;
    }

    public void openEgg() {
        if (InventoryServiceNew.gI().getCountEmptyBag(this.player) > 0) {
            try {
                destroyEgg();
                Thread.sleep(4000);
                int[] list_linh_thu = new int[]{2021, 1998, 2020, 1995, 2019, 1993};
                Item linhThu = ItemService.gI().createNewItem((short) list_linh_thu[Util.nextInt(list_linh_thu.length)]);
                laychiso(player, linhThu, 0);
                InventoryServiceNew.gI().addItemBag(player, linhThu);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendThongBao(player, "Chúc mừng bạn nhận được Linh thú " + linhThu.template.name);
                ChangeMapService.gI().changeMapInYard(this.player, this.player.gender * 7, -1, Util.nextInt(300, 500));
                player.egglinhthu = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Service.gI().sendThongBao(player, "Hành trang không đủ chỗ trống");
        }
    }

    public void destroyEgg() {
        try {
            Message msg = new Message(-117);
            msg.writer().writeByte(101);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.player.egglinhthu = null;
    }

    public void subTimeDone(int d, int h, int m, int s) {
        this.timeDone -= ((d * 24 * 60 * 60 * 1000) + (h * 60 * 60 * 1000) + (m * 60 * 1000) + (s * 1000));
        this.sendEggLinhThu();
    }

    public void dispose() {
        this.player = null;
    }

    public void laychiso(Player player, Item linhthu, int lvnow) {
        switch (linhthu.template.id) {
            case 2021:
                linhthu.itemOptions.add(new Item.ItemOption(50, 10 + 3 * lvnow));//%sd
                linhthu.itemOptions.add(new Item.ItemOption(0, 1000 + 300 * lvnow));//sd
                break;
            case 1998:
                linhthu.itemOptions.add(new Item.ItemOption(94, 10 + 3 * lvnow));//%giap
                linhthu.itemOptions.add(new Item.ItemOption(47, 1000 + 300 * lvnow));//sd
                break;
            case 2020:
                linhthu.itemOptions.add(new Item.ItemOption(14, 5 + 1 * lvnow));//%cm
                linhthu.itemOptions.add(new Item.ItemOption(5, 10 + 3 * lvnow));//%sdcm
                break;
            case 1995:
                linhthu.itemOptions.add(new Item.ItemOption(103, 10 + 3 * lvnow));//%ki
                linhthu.itemOptions.add(new Item.ItemOption(23, 10 + 3 * lvnow));//KI
                break;
            case 2019:
                linhthu.itemOptions.add(new Item.ItemOption(77, 10 + 3 * lvnow));//%HP
                linhthu.itemOptions.add(new Item.ItemOption(22, 10 + 3 * lvnow));//HP
                break;
            case 1993:
                linhthu.itemOptions.add(new Item.ItemOption(108, 5 + 1 * lvnow));//%ne
                linhthu.itemOptions.add(new Item.ItemOption(97, 10 + 3 * lvnow));//pst
                break;
        }
        InventoryServiceNew.gI().sendItemBags(player);
    }
}
