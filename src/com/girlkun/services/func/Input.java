package com.girlkun.services.func;

import AururaFactory.AlexDConfig;
import com.Arriety.games.LuckyNumberGameManager;
import com.Arriety.manager.GiftEventManager;
import com.girlkun.models.map.bdkb.BanDoKhoBauService;
import com.girlkun.database.GirlkunDB;
import com.girlkun.consts.ConstNpc;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.Zone;
import com.girlkun.models.npc.Npc;
import com.girlkun.models.npc.NpcManager;
import com.girlkun.models.player.Inventory;
import com.girlkun.models.player.Player;
import com.girlkun.network.io.Message;
import com.girlkun.network.session.ISession;
import com.girlkun.result.GirlkunResultSet;
import com.girlkun.server.Client;
import com.girlkun.services.Service;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
//import com.girlkun.services.NapThe;
import com.girlkun.services.NpcService;
import com.girlkun.utils.Util;
import java.time.Instant;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

public class Input {

    private static final Map<Integer, Object> PLAYER_ID_OBJECT = new HashMap<Integer, Object>();

    public static final int CHANGE_PASSWORD = 500;
    public static final int GIFT_CODE = 501;
    public static final int FIND_PLAYER = 502;
    public static final int CHANGE_NAME = 503;
    public static final int CHOOSE_LEVEL_BDKB = 504;
    public static final int NAP_THE = 505;
    public static final int CHANGE_NAME_BY_ITEM = 506;
    public static final int GIVE_IT = 507;
    public static final int LUCKY_NUMBER = 508;
    public static final int SEND_ITEM_OP = 513;

    public static final byte NUMERIC = 0;
    public static final byte ANY = 1;
    public static final byte PASSWORD = 2;

    private static Input intance;

    private Input() {

    }

    public static Input gI() {
        if (intance == null) {
            intance = new Input();
        }
        return intance;
    }

    public void doInput(Player player, Message msg) {
        try {
            String[] text = new String[msg.reader().readByte()];
            for (int i = 0; i < text.length; i++) {
                text[i] = msg.reader().readUTF();
            }
            switch (player.iDMark.getTypeInput()) {
                case SEND_ITEM_OP:
                    if (player.isAdmin()) {
                        int idItemBuff = Integer.parseInt(text[1]);
                        int idOptionBuff = Integer.parseInt(text[2]);
                        int slOptionBuff = Integer.parseInt(text[3]);
                        int slItemBuff = Integer.parseInt(text[4]);
                        Player pBuffItem = Client.gI().getPlayer(text[0]);
                        if (pBuffItem != null) {
                            String txtBuff = "Buff to player: " + pBuffItem.name + "\b";
                            if (idItemBuff == -1) {
                                pBuffItem.inventory.gold = Math.min(pBuffItem.inventory.gold + (long) slItemBuff, Inventory.LIMIT_GOLD);
                                txtBuff += slItemBuff + " vàng\b";
                                Service.getInstance().sendMoney(player);
                            } else if (idItemBuff == -2) {
                                pBuffItem.inventory.gem = Math.min(pBuffItem.inventory.gem + slItemBuff, 2000000000);
                                txtBuff += slItemBuff + " ngọc\b";
                                Service.getInstance().sendMoney(player);
                            } else if (idItemBuff == -3) {
                                pBuffItem.inventory.ruby = Math.min(pBuffItem.inventory.ruby + slItemBuff, 2000000000);
                                txtBuff += slItemBuff + " ngọc khóa\b";
                                Service.getInstance().sendMoney(player);
                            } else {
                                //Item itemBuffTemplate = ItemBuff.getItem(idItemBuff);
                                Item itemBuffTemplate = ItemService.gI().createNewItem((short) idItemBuff);
                                itemBuffTemplate.itemOptions.add(new Item.ItemOption(idOptionBuff, slOptionBuff));
                                itemBuffTemplate.quantity = slItemBuff;
                                txtBuff += "x" + slItemBuff + " " + itemBuffTemplate.template.name + "\b";
                                InventoryServiceNew.gI().addItemBag(pBuffItem, itemBuffTemplate);
                                InventoryServiceNew.gI().sendItemBags(pBuffItem);
                            }
                            NpcService.gI().createTutorial(player, 24, txtBuff);
                            if (player.id != pBuffItem.id) {
                                NpcService.gI().createTutorial(player, 24, txtBuff);
                            }
                        } else {
                            Service.getInstance().sendThongBao(player, "Player không online");
                        }
                        break;
                    }
                    break;
                case GIVE_IT:
                    String name = text[0];
                    int id = Integer.valueOf(text[1]);
                    int q = Integer.valueOf(text[2]);
                    if (Client.gI().getPlayer(name) != null) {
                        Item item = ItemService.gI().createNewItem(((short) id));
                        item.quantity = q;
                        InventoryServiceNew.gI().addItemBag(Client.gI().getPlayer(name), item);
                        InventoryServiceNew.gI().sendItemBags(Client.gI().getPlayer(name));
                        Service.getInstance().sendThongBao(Client.gI().getPlayer(name), "Nhận " + item.template.name + " từ " + player.name);
                    } else {
                        Service.getInstance().sendThongBao(player, "Không online");
                    }
                    break;
//                case NAP_THE:
//                    NapThe.gI().napThe(player, text[0], text[1]);
//                    break;
                case CHANGE_PASSWORD:
                    Service.getInstance().changePassword(player, text[0], text[1], text[2]);
                    break;
                case GIFT_CODE: {
                    String textLevel = text[0];
                    Input.gI().addItemGiftCodeToPlayer(player, textLevel);
                }
                break;
                case LUCKY_NUMBER:
                    if (text.length == 0) {
                        Service.getInstance().sendThongBao(player, "Vui lòng nhập con số may mắn");
                        break;
                    }

                    int value = Integer.parseInt(text[0]);
                    if (value >= AlexDConfig.LUCKY_NUMBER_MIN && value <= AlexDConfig.LUCKY_NUMBER_MAX) {
                        LuckyNumberGameManager.getInstance().handlePlayerInput((int) player.id, value);
                    } else {
                        Service.getInstance().sendThongBao(player, "Con số may mắn không hợp lệ");
                    }
                    break;
                case FIND_PLAYER:
                    Player pl = Client.gI().getPlayer(text[0]);
                    if (pl != null) {
                        NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_FIND_PLAYER, -1, "Ngài muốn..?",
                                new String[]{"Đi tới\n" + pl.name, "Gọi " + pl.name + "\ntới đây", "Đổi tên", "Ban", "Kick"},
                                pl);
                    } else {
                        Service.getInstance().sendThongBao(player, "Người chơi không tồn tại hoặc đang offline");
                    }
                    break;
                case CHANGE_NAME: {
                    Player plChanged = (Player) PLAYER_ID_OBJECT.get((int) player.id);
                    if (plChanged != null) {
                        if (GirlkunDB.executeQuery("select * from player where name = ?", text[0]).next()) {
                            Service.getInstance().sendThongBao(player, "Tên nhân vật đã tồn tại");
                        } else {
                            plChanged.name = text[0];
                            GirlkunDB.executeUpdate("update player set name = ? where id = ?", plChanged.name, plChanged.id);
                            Service.getInstance().player(plChanged);
                            Service.getInstance().Send_Caitrang(plChanged);
                            Service.getInstance().sendFlagBag(plChanged);
                            Zone zone = plChanged.zone;
                            ChangeMapService.gI().changeMap(plChanged, zone, plChanged.location.x, plChanged.location.y);
                            Service.getInstance().sendThongBao(plChanged, "Chúc mừng bạn đã có cái tên mới đẹp đẽ hơn tên ban đầu");
                            Service.getInstance().sendThongBao(player, "Đổi tên người chơi thành công");
                        }
                    }
                }
                break;
                case CHANGE_NAME_BY_ITEM: {
                    if (player != null) {
                        if (GirlkunDB.executeQuery("select * from player where name = ?", text[0]).next()) {
                            Service.getInstance().sendThongBao(player, "Tên nhân vật đã tồn tại");
                            createFormChangeNameByItem(player);
                        } else {
                            Item theDoiTen = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 2006);
                            if (theDoiTen == null) {
                                Service.getInstance().sendThongBao(player, "Không tìm thấy thẻ đổi tên");
                            } else {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, theDoiTen, 1);
                                player.name = text[0];
                                GirlkunDB.executeUpdate("update player set name = ? where id = ?", player.name, player.id);
                                Service.getInstance().player(player);
                                Service.getInstance().Send_Caitrang(player);
                                Service.getInstance().sendFlagBag(player);
                                Zone zone = player.zone;
                                ChangeMapService.gI().changeMap(player, zone, player.location.x, player.location.y);
                                Service.getInstance().sendThongBao(player, "Chúc mừng bạn đã có cái tên mới đẹp đẽ hơn tên ban đầu");
                            }
                        }
                    }
                }
                break;
                case CHOOSE_LEVEL_BDKB:
                    int level = Integer.parseInt(text[0]);
                    if (level >= 1 && level <= 110) {
                        Npc npc = NpcManager.getByIdAndMap(ConstNpc.QUY_LAO_KAME, player.zone.map.mapId);
                        if (npc != null) {
                            npc.createOtherMenu(player, ConstNpc.MENU_ACCEPT_GO_TO_BDKB,
                                    "Con có chắc chắn muốn tới bản đồ kho báu cấp độ " + level + "?",
                                    new String[]{"Đồng ý", "Từ chối"}, level);
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    }

//                    BanDoKhoBauService.gI().openBanDoKhoBau(player, (byte) );
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createForm(Player pl, int typeInput, String title, SubInput... subInputs) {
        pl.iDMark.setTypeInput(typeInput);
        Message msg;
        try {
            msg = new Message(-125);
            msg.writer().writeUTF(title);
            msg.writer().writeByte(subInputs.length);
            for (SubInput si : subInputs) {
                msg.writer().writeUTF(si.name);
                msg.writer().writeByte(si.typeInput);
            }
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createForm(ISession session, int typeInput, String title, SubInput... subInputs) {
        Message msg;
        try {
            msg = new Message(-125);
            msg.writer().writeUTF(title);
            msg.writer().writeByte(subInputs.length);
            for (SubInput si : subInputs) {
                msg.writer().writeUTF(si.name);
                msg.writer().writeByte(si.typeInput);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFormChangePassword(Player pl) {
        createForm(pl, CHANGE_PASSWORD, "Đổi mật khẩu", new SubInput("Mật khẩu cũ", PASSWORD),
                new SubInput("Mật khẩu mới", PASSWORD),
                new SubInput("Nhập lại mật khẩu mới", PASSWORD));
    }

    public void createFormGiveItem(Player pl) {
        createForm(pl, GIVE_IT, "Tặng vật phẩm", new SubInput("Tên", ANY), new SubInput("Id Item", ANY), new SubInput("Số lượng", ANY));
    }

    public void createFormGiftCode(Player pl) {
        createForm(pl, GIFT_CODE, "Gift code Ngọc Rồng Aurora", new SubInput("Gift-code", ANY));
    }

    public void createLuckyNumber(Player pl) {
        createForm(pl, LUCKY_NUMBER,
                String.format("Nhập con số may mắn từ %d đến %d", AlexDConfig.LUCKY_NUMBER_MIN, AlexDConfig.LUCKY_NUMBER_MAX),
                new SubInput("Số may mắn", ANY));
    }

    public void createFormSenditem1(Player pl) {
        createForm(pl, SEND_ITEM_OP, "SEND Vật Phẩm Option",
                new SubInput("Tên người chơi", ANY),
                new SubInput("ID Trang Bị", NUMERIC),
                new SubInput("ID Option", NUMERIC),
                new SubInput("Param", NUMERIC),
                new SubInput("Số lượng", NUMERIC));
    }

    public void createFormFindPlayer(Player pl) {
        createForm(pl, FIND_PLAYER, "Tìm kiếm người chơi", new SubInput("Tên người chơi", ANY));
    }

    public void createFormNapThe(Player pl, byte loaiThe) {
        pl.iDMark.setLoaiThe(loaiThe);
        createForm(pl, NAP_THE, "Nạp thẻ", new SubInput("Mã thẻ", ANY), new SubInput("Seri", ANY));
    }

    public void createFormChangeName(Player pl, Player plChanged) {
        PLAYER_ID_OBJECT.put((int) pl.id, plChanged);
        createForm(pl, CHANGE_NAME, "Đổi tên " + plChanged.name, new SubInput("Tên mới", ANY));
    }

    public void createFormChangeNameByItem(Player pl) {
        createForm(pl, CHANGE_NAME_BY_ITEM, "Đổi tên " + pl.name, new SubInput("Tên mới", ANY));
    }

    public void createFormChooseLevelBDKB(Player pl) {
        createForm(pl, CHOOSE_LEVEL_BDKB, "Chọn cấp độ", new SubInput("Cấp độ (1-110)", NUMERIC));
    }

    public static class SubInput {

        private String name;
        private byte typeInput;

        public SubInput(String name, byte typeInput) {
            this.name = name;
            this.typeInput = typeInput;
        }
    }

    public void addItemGiftCodeToPlayer(Player p, final String giftcode) {
        GirlkunResultSet red = null;
        try {
            red = GirlkunDB.executeQuery("SELECT * FROM `gift_code` WHERE `code` LIKE '" + Util.strSQL(giftcode) + "' LIMIT 1;");
            if (red != null && red.first()) {
                String text = "Mã quà tặng" + ": " + giftcode + "\b- " + "Phần quà của bạn là:" + "\b";
                int id = red.getInt("id");
                final byte type = red.getByte("type");
                int limit = red.getInt("limit");
                int isPlayer = red.getInt("isPlayer");
                final JSONArray listUser = (JSONArray) JSONValue.parseWithException(red.getString("listUser"));
                int isTime = red.getInt("isTime");
                if (limit <= 0) {
                    Service.gI().sendThongBaoFromAdmin(p, "Đã đạt giới hạn lượt nhập <3");
                    return;
                }
                if (type == 1) {
                    for (int i = 0; i < listUser.size(); ++i) {
                        final int playerId = Integer.parseInt(listUser.get(i).toString());
                        if (playerId == p.id) {
                            Service.gI().sendThongBaoFromAdmin(p, "Mỗi tài khoản chỉ được phép sử dụng mã quà tặng này 1 lần duy nhất.");
                            return;
                        }
                    }
                }
                if (isPlayer == 1) {
                    final JSONArray jar = (JSONArray) JSONValue.parse(red.getString("player"));
                    boolean checkUser = false;
                    int j;
                    for (j = 0; j < jar.size(); j++) {
                        if (jar.get(j).toString().equals(p.name)) {
                            checkUser = true;
                            break;
                        }
                    }
                    if (!checkUser) {
                        Service.gI().sendThongBaoFromAdmin(p, "Bạn không thể sử dụng Gift code này.");
                        return;
                    }
                }
                if (isTime == 1) {
                    if (Date.from(Instant.now()).compareTo(Util.getDate(red.getString("time"))) > 0) {
                        Service.gI().sendThongBaoFromAdmin(p, "Mã Gift code này đã hết hạn sử dụng.");
                        return;
                    }
                }
                JSONValue jv = new JSONValue();
                JSONArray listItem = null;
                listItem = (JSONArray) jv.parse(red.getString("item_gift"));
                if (p != null && InventoryServiceNew.gI().getCountEmptyBag(p) < listItem.size()) {
                    Service.gI().sendThongBaoFromAdmin(p, "Hành trang cần phải có ít nhất " + listItem.size() + " ô trống để nhận vật phẩm");
                    return;
                }
                for (int i = 0; i < listItem.size(); ++i) {
                    JSONArray dataItem = (JSONArray) jv.parse(listItem.get(i).toString());
                    short idItem = Short.parseShort(String.valueOf(dataItem.get(0)));
                    int quantity = Integer.parseInt(String.valueOf(dataItem.get(1)));
                    //gửi item vào túi
                    if (idItem == -1) {
                        p.inventory.gold = Math.min(p.inventory.gold + (long) quantity, Inventory.LIMIT_GOLD);
                        text += quantity + " vàng\b";
                        Service.getInstance().sendMoney(p);
                    } else if (idItem == -2) {
                        p.inventory.gem = (int) Math.min(p.inventory.gem + quantity, 2000000000);
                        text += quantity + " ngọc\b";
                        Service.getInstance().sendMoney(p);
                    } else if (idItem == -3) {
                        p.inventory.ruby = (int) Math.min(p.inventory.ruby + quantity, 2000000000);
                        text += quantity + " ngọc khóa\b";
                        Service.getInstance().sendMoney(p);
                    } else {
                        Item itemGiftTemplate = ItemService.gI().createNewItem((short) idItem);
                        itemGiftTemplate.quantity = quantity;
                        JSONArray options = (JSONArray) jv.parse(String.valueOf(dataItem.get(2)).replaceAll("\"", ""));
                        for (int j = 0; j < options.size(); j++) {
                            JSONArray opt = (JSONArray) jv.parse(String.valueOf(options.get(j)));
                            itemGiftTemplate.itemOptions.add(new Item.ItemOption(Integer.parseInt(String.valueOf(opt.get(0))),
                                    Integer.parseInt(String.valueOf(opt.get(1)))));
                        }
                        text += "x" + quantity + " " + itemGiftTemplate.template.name + "\b";
                        InventoryServiceNew.gI().addItemBag(p, itemGiftTemplate);
                        InventoryServiceNew.gI().sendItemBags(p);
                        Service.getInstance().sendMoney(p);
                        if (i < listItem.size() - 1) {
                            text += "";
                        }
                    }
                }
                if (limit != -1) {
                    --limit;
                }
                listItem.clear();
                listUser.add(p.id);
                GirlkunDB.executeUpdate("UPDATE `gift_code` SET `limit` = " + limit + ", `listUser` = '" + listUser.toJSONString() + "' WHERE `code` LIKE '" + Util.strSQL(giftcode) + "';");
                NpcService.gI().createTutorial(p, 15269, text);
            } else {
                Service.gI().sendThongBaoFromAdmin(p, "Mã quà tặng không tồn tại hoặc đã được sử dụng");
                if (red != null) {
                    red.dispose();
                    red = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Service.gI().sendThongBaoFromAdmin(p, "Có lỗi sảy ra hãy báo ngay cho QTV để khắc phục.");
            e.printStackTrace();
        } finally {
            if (red != null) {
                red.dispose();
                red = null;
            }
        }
    }

}
