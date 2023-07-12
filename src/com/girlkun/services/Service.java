package com.girlkun.services;

import AururaFactory.Setting;
import com.girlkun.models.DragonBallNamec.NgocRongNamecService;
import com.girlkun.database.GirlkunDB;
import com.girlkun.consts.ConstNpc;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.utils.FileIO;
import com.girlkun.data.DataGame;
import com.girlkun.jdbc.daos.GodGK;
import com.girlkun.models.boss.BossManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.sun.management.OperatingSystemMXBean;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.npc.specialnpc.MabuEgg;
import com.girlkun.models.player.Pet;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.map.Zone;
import com.girlkun.models.map.sieuhang.SieuHangManager;
import com.girlkun.models.matches.TOP;
import com.girlkun.models.npc.specialnpc.EggLinhThu;
import com.girlkun.models.player.Player;
import com.girlkun.models.shop.ItemShop;
import com.girlkun.models.shop.Shop;
import com.girlkun.models.skill.Skill;
import com.girlkun.network.io.Message;
import com.girlkun.network.server.GirlkunSessionManager;
import com.girlkun.network.session.ISession;
import com.girlkun.server.Client;
import com.girlkun.server.Manager;
import com.girlkun.server.ServerManager;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.services.func.ChonAiDay;
import com.girlkun.services.func.Input;
import com.girlkun.utils.Logger;
import com.girlkun.utils.TimeUtil;
import com.girlkun.utils.Util;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

public class Service {

    private static Service instance;

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public static Service gI() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public void SendRemovePlayer(Player player) {
        try {
            Message message = new Message(-6);
            message.writer().writeInt((int) player.id);
            sendMessAllPlayerInMap(player, message);
            message.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeEff(Player pl, int... id) {
        try {
            Message msg = new Message(-128);
            if (id.length > 0) {
                msg.writer().writeByte(1);
            } else {
                msg.writer().writeByte(2);
            }
            msg.writer().writeInt((int) pl.id);
            if (id.length > 0) {
                msg.writer().writeShort(id[0]);
            }
            sendMessAllPlayerInMap(pl.zone, msg);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void sendEffectHideNPC(Player pl, byte npcID, byte status) {
        Message msg;
        try {
            msg = new Message(-73);
            msg.writer().writeByte(npcID);
            msg.writer().writeByte(status); // 0 = hide
            Service.gI().sendMessAllPlayerInMap(pl, msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendEffectTemplate(ISession session, int id) {
        Message msg;
        try {
            byte[] eff_data = FileIO.readFile("data/girlkun/effdata/x" + session.getZoomLevel() + "/" + id);
            msg = new Message(-66);
            msg.writer().write(eff_data);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEffectChar(Player pl, int id, int layer, int loop, int loopcount, int stand) {
        if (!pl.idEffChar.contains(id)) {
            pl.idEffChar.add(id);
        }
        try {
            Message msg = new Message(-128);
            msg.writer().writeByte(0);
            msg.writer().writeInt((int) pl.id);
            msg.writer().writeShort(id);
            msg.writer().writeByte(layer);
            msg.writer().writeByte(loop);
            msg.writer().writeShort(loopcount);
            msg.writer().writeByte(stand);
            sendMessAllPlayerInMap(pl.zone, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEff(Zone z, int id, int x, int y, int loop, int layer, int loopCount) {
        try {
            Message msg = new Message(113);
            msg.writer().writeByte(loop);
            msg.writer().writeByte(layer);
            msg.writer().writeByte(id);
            msg.writer().writeShort(x);
            msg.writer().writeShort(y);
            msg.writer().writeShort(loopCount);
            this.sendMessAllPlayerInMap(z, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showListTop(Player player, List<TOP> tops) {
        Message msg;
        try {
            List<Player> players = GodGK.getListPlayerById(tops);

            msg = new Message(-96);
            msg.writer().writeByte(0);
            msg.writer().writeUTF("Top");
            msg.writer().writeByte(tops.size());

            for (int i = 0; i < tops.size(); i++) {
                TOP top = tops.get(i);
                Player pl = players.get(i);
                String online = "";
                if (Client.gI().getPlayer(pl.name) != null) {
                    online = "online";
                } else {
                    online = "offline";
                }
                msg.writer().writeInt(i + 1);
                msg.writer().writeInt((int) pl.id);
                msg.writer().writeShort(pl.getHead());
                if (player.getSession().getVersion() > 214) {
                    msg.writer().writeShort(-1);
                }
                msg.writer().writeShort(pl.getBody());
                msg.writer().writeShort(pl.getLeg());
                msg.writer().writeUTF(pl.name);
                msg.writer().writeUTF(top.getInfo1());
                msg.writer().writeUTF(online);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showListTop(Player player, List<TOP> tops, byte isPVP) {
        Message msg;
        try {
            List<Player> players = GodGK.getListPlayerById(tops);
            msg = new Message(-96);
            msg.writer().writeByte(0);
            msg.writer().writeUTF("Top");
            msg.writer().writeByte(tops.size());

            for (Player pl : players) {
                if (pl != null) {
                    String text1 = "";
                    String text2 = "";
                    text2 += " HP: " + pl.nPoint.hpMax + "\n"
                            + " KI: " + pl.nPoint.mpMax + "\n"
                            + "SD: " + pl.nPoint.dame + "\n"
                            + " Giáp: " + pl.nPoint.def;
                    if (SieuHangManager.gI().getrubyranksh((int) pl.rankSieuHang) != 0) {
                        text1 = "+" + SieuHangManager.gI().getrubyranksh((int) pl.rankSieuHang) + " Hồng ngọc/ngày";
                    }
                    msg.writer().writeInt((int) pl.rankSieuHang);
                    msg.writer().writeInt((int) pl.id);
                    msg.writer().writeShort(pl.getHead());
                    if (player.getSession().getVersion() > 214) {
                        msg.writer().writeShort(-1);
                    }
                    msg.writer().writeShort(pl.getBody());
                    msg.writer().writeShort(pl.getLeg());
                    msg.writer().writeUTF(pl.name);
                    msg.writer().writeUTF(text1);
                    msg.writer().writeUTF(text2);
                }
            }

            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPopUpMultiLine(Player pl, int tempID, int avt, String text) {
        Message msg = null;
        try {
            msg = new Message(-218);
            msg.writer().writeShort(tempID);
            msg.writer().writeUTF(text);
            msg.writer().writeShort(avt);
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (msg != null) {
                msg.cleanup();
                msg = null;
            }
        }
    }

    public void sendThongBaoBenDuoi(String text) {
        Message msg = null;
        try {
            msg = new Message(93);
            msg.writer().writeUTF(text);
            sendMessAllPlayer(msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (msg != null) {
                msg.cleanup();
                msg = null;
            }
        }
    }

    public void SendMsgUpdateHoaDa(Player player, byte typead, byte typeTar, byte type) {
        try {
            Message message = new Message(-124);
            message.writer().writeByte(typead);
            message.writer().writeByte(typeTar);
            message.writer().writeByte(type);
            message.writer().writeInt((int) player.id);
            sendMessAllPlayerInMap(player, message);
            message.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void sendPopUpMultiLine(Player pl, int tempID, int avt, String text) {
//        Message msg = null;
//        try {
//            msg = new Message(-218);
//            msg.writer().writeShort(tempID);
//            msg.writer().writeUTF(text);
//            msg.writer().writeShort(avt);
//            pl.sendMessage(msg);
//            msg.cleanup();
//        } catch (Exception e) {
////            e.printStackTrace();
//        } finally {
//            if (msg != null) {
//                msg.cleanup();
//                msg = null;
//            }
//        }
//    }
    public void sendPetFollow(Player player, short smallId) {
        Message msg;
        try {
            if (player != null) {
                msg = new Message(31);
                msg.writer().writeInt((int) player.id);
                if (smallId == 0) {
                    msg.writer().writeByte(0);
                } else {
                    msg.writer().writeByte(1);
                    msg.writer().writeShort(smallId);
                    msg.writer().writeByte(1);
                    int[] fr = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
                    msg.writer().writeByte(fr.length);
                    for (int i = 0; i < fr.length; i++) {
                        msg.writer().writeByte(fr[i]);
                    }
                    msg.writer().writeShort(smallId == 15067 ? 65 : 75);
                    msg.writer().writeShort(smallId == 15067 ? 65 : 75);
                }
                sendMessAllPlayerInMap(player, msg);
                msg.cleanup();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPetFollowToMe(Player me, Player pl) {
        Item linhThu = pl.inventory.itemsBody.get(10);
        if (!linhThu.isNotNullItem()) {
            return;
        }
        short smallId = (short) (linhThu.template.iconID - 1);
        Message msg;
        try {
            msg = new Message(31);
            msg.writer().writeInt((int) pl.id);
            msg.writer().writeByte(1);
            msg.writer().writeShort(smallId);
            msg.writer().writeByte(1);
            int[] fr = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
            msg.writer().writeByte(fr.length);
            for (int i = 0; i < fr.length; i++) {
                msg.writer().writeByte(fr[i]);
            }
            msg.writer().writeShort(smallId == 15067 ? 65 : 75);
            msg.writer().writeShort(smallId == 15067 ? 65 : 75);
            me.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessAllPlayer(Message msg) {
        PlayerService.gI().sendMessageAllPlayer(msg);
    }

    public void sendMessAllPlayerIgnoreMe(Player player, Message msg) {
        PlayerService.gI().sendMessageIgnore(player, msg);
    }

    public void sendMessAllPlayerInMap(Zone zone, Message msg) {
        if (zone == null) {
            msg.dispose();
            return;
        }
        List<Player> players = zone.getPlayers();
        if (players.isEmpty()) {
            msg.dispose();
            return;
        }
        List<Player> copy = new ArrayList<>(players);
        for (Player pl : copy) {
            if (pl != null) {
                pl.sendMessage(msg);
            }
        }
        msg.cleanup();
    }

    public void sendMessAllPlayerInMap(Player player, Message msg) {
        if (player == null || player.zone == null) {
            msg.dispose();
            return;
        }
        if (MapService.gI().isMapOffline(player.zone.map.mapId)) {
            if (player.isPet && player.isNewPet) {
                ((Pet) player).master.sendMessage(msg);
            } else {
                player.sendMessage(msg);
            }
        } else {
            List<Player> players = player.zone.getPlayers();
            if (players.isEmpty()) {
                msg.dispose();
                return;
            }
            for (int i = 0; i < players.size(); i++) {
                Player pl = players.get(i);
                if (pl != null) {
                    pl.sendMessage(msg);
                }
            }
        }
        msg.cleanup();
    }

//    public void regisAccount(ISession session, Message _msg) {
////        sendThongBaoOK(session, "Tính đăng đăng kí tài khoản trong game đang trong quá trình phát triển. Vui lòng truy cập website http://ngocrongdragon.com để đăng kí tài khoản. Xin cám ơn");
////        return;
//
//        try {
//            _msg.readUTF();
//            _msg.readUTF();
//            _msg.readUTF();
//            _msg.readUTF();
//            _msg.readUTF();
//            _msg.readUTF();
//            _msg.readUTF();
//            String user = _msg.readUTF();
//            String pass = _msg.readUTF();
//            if (!(user.length() >= 4 && user.length() <= 18)) {
//                sendThongBaoOK(session, "Tài khoản phải có độ dài 4-18 ký tự");
//                return;
//            }
//            if (!(pass.length() >= 6 && pass.length() <= 18)) {
//                sendThongBaoOK(session, "Mật khẩu phải có độ dài 6-18 ký tự");
//                return;
//            }
//            GirlkunResultSet rs = GirlkunDB.executeQuery("select * from account where username = ?", user);
//            if (rs.first()) {
//                sendThongBaoOK(session, "Tài khoản đã tồn tại");
//            } else {
//                pass = Util.md5(pass);
//                GirlkunDB.executeUpdate("insert into account (username, password) values()", user, pass);
//                sendThongBaoOK(session, "Đăng ký tài khoản thành công!");
//            }
//            rs.dispose();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void sendMessAnotherNotMeInMap(Player player, Message msg) {
        if (player == null || player.zone == null) {
            msg.dispose();
            return;
        }

        List<Player> players = new ArrayList<>(player.zone.getPlayers()); // Tạo bản sao của danh sách players

        if (players.isEmpty()) {
            msg.dispose();
            return;
        }

        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player pl = iterator.next();
            if (pl != null && !pl.equals(player)) {
                pl.sendMessage(msg);
            }
        }
        msg.cleanup();
    }

    public void Send_Info_NV(Player pl) {
        Message msg;
        try {
            msg = Service.getInstance().messageSubCommand((byte) 14);//Cập nhật máu
            msg.writer().writeInt((int) pl.id);
            msg.writer().writeInt(pl.nPoint.hp);
            msg.writer().writeByte(0);//Hiệu ứng Ăn Đậu
            msg.writer().writeInt(pl.nPoint.hpMax);
            sendMessAnotherNotMeInMap(pl, msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendInfoPlayerEatPea(Player pl) {
        Message msg;
        try {
            msg = Service.getInstance().messageSubCommand((byte) 14);
            msg.writer().writeInt((int) pl.id);
            msg.writer().writeInt(pl.nPoint.hp);
            msg.writer().writeByte(1);
            msg.writer().writeInt(pl.nPoint.hpMax);
            sendMessAnotherNotMeInMap(pl, msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginDe(ISession session, short second) {
        Message msg;
        try {
            msg = new Message(122);
            msg.writer().writeShort(second);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetPoint(Player player, int x, int y) {
        Message msg;
        try {
            player.location.x = x;
            player.location.y = y;
            msg = new Message(46);
            msg.writer().writeShort(x);
            msg.writer().writeShort(y);
            player.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearMap(Player player) {
        Message msg;
        try {
            msg = new Message(-22);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ChangeMonsterBody(Player pl, int type, int idMap, int body) {
        Message message;
        try {
            message = new Message(-112);
            message.writeByte(type);
            message.writeByte(idMap);
            if (type == 1) {
                message.writeShort(body);
            }
            sendMessAllPlayerInMap(pl, message);
            message.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void switchToRegisterScr(ISession session) {
//        try {
//            Message message;
//            try {
//                message = new Message(42);
//                message.writeByte(0);
//                session.sendMessage(message);
//                message.cleanup();
//            } catch (Exception e) {
//            }
//        } catch (Exception e) {
//        }
//    }
    public void chat(Player player, String text) {
        //check data by Management
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long startTime = threadMXBean.getCurrentThreadCpuTime();
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalPhysicalMemorySize = operatingSystemMXBean.getTotalPhysicalMemorySize();
        long freePhysicalMemorySize = operatingSystemMXBean.getFreePhysicalMemorySize();
        long usedPhysicalMemorySize = totalPhysicalMemorySize - freePhysicalMemorySize;
        long endTime = threadMXBean.getCurrentThreadCpuTime();
        long cpuTime = endTime - startTime;
        double cpuUsage = (double) cpuTime / (System.nanoTime() - startTime) * 100;
        if (player.getSession() != null && player.isAdmin()) {
            if (text.equals("r")) {
                new Thread(() -> {
                    while (true) {
                        Manager.loadPart();
                        DataGame.updateData(player.getSession());
                        try {
                            Thread.sleep(5);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return;
            }
            if (text.equals("beodz")) {
                Manager.gI().reloadShop();
                return;
            }
            if (text.equals("hskill")) {
                Service.getInstance().releaseCooldownSkill(player);
                return;
            }
            if (text.equals("skillxd")) {
                SkillService.gI().learSkillSpecial(player, Skill.LIEN_HOAN_CHUONG);
                return;
            }
            if (text.equals("skilltd")) {
                SkillService.gI().learSkillSpecial(player, Skill.SUPER_KAME);
                return;
            }
            if (text.equals("skillnm")) {
                SkillService.gI().learSkillSpecial(player, Skill.MA_PHONG_BA);
                return;
            }

            if (text.equals("logskill")) {
                Service.getInstance().sendThongBao(player, player.playerSkill.skillSelect.coolDown + "");
                return;
            }
            if (text.equals("client")) {
                Client.gI().show(player);
            } else if (text.equals("m")) {
                sendThongBaoFromAdmin(player, "Map " + player.zone.map.mapName + " (" + player.zone.map.mapId + ")");
                return;
            } else if (text.equals("boss")) {
                String str = "";
                for (Player b : player.zone.getBosses()) {
                    str += b.name + "\n";
                }
                sendThongBao(player, str);
            } else if (text.equals("vt")) {
                sendThongBaoFromAdmin(player, player.location.x + " - " + player.location.y + "\n"
                        + player.zone.map.yPhysicInTop(player.location.x, player.location.y));
            } else if (text.equals("b")) {
                Message msg;
                try {
                    msg = new Message(52);
                    msg.writer().writeByte(0);
                    msg.writer().writeInt((int) player.id);
                    sendMessAllPlayerInMap(player, msg);
                    msg.cleanup();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (text.equals("c")) {
                Message msg;
                try {
                    msg = new Message(52);
                    msg.writer().writeByte(2);
                    msg.writer().writeInt((int) player.id);
                    msg.writer().writeInt((int) player.zone.getHumanoids().get(1).id);
                    sendMessAllPlayerInMap(player, msg);
                    msg.cleanup();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (text.equals("nrnm")) {
                Service.getInstance().activeNamecShenron(player);
                return;
            }
            if (text.equals("t1")) {
                sendThongBao(player, "test " + player.inventory.pointNangDong + "\n");
                return;
            }
            if (text.equals("ts")) {
                sendThongBao(player, "Time start server: " + ServerManager.timeStart + "\n");
                return;
            }
            if (text.equals("a")) {
                BossManager.gI().showListBoss(player);
                return;
            }
            if (text.equals("admin")) {
                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_ADMIN, -1,
                        "Quản trị admin Aurora \n"
                        + "|1|Online: " + Client.gI().getPlayers().size() * 2 + "\n"
                        + "|2|Sessions: " + GirlkunSessionManager.gI().getSessions().size() * 2 + "\n"
                        + "|4|Thread: " + Thread.activeCount() * 2 + "\n"
                        + "|5|Tỷ lệ sử dụng CPU: " + cpuUsage + "%" + "\n"
                        + "|7|Tổng dung lượng Ram: " + Util.ThongSo(totalPhysicalMemorySize) + "\n"
                        + "|8|Đã sửa dụng Ram: " + Util.ThongSo(usedPhysicalMemorySize) + "\n"
                        + "|3|Ram trống: " + Util.ThongSo(freePhysicalMemorySize) + "\n",
                        "Ngọc rồng", "Đệ tử", "Bảo trì", "Tìm kiếm\nngười chơi", "Boss", "Create\nBoss", "Đóng");
                return;
            } else if (text.startsWith("upp")) {
                try {
                    long power = Long.parseLong(text.replaceAll("upp", ""));
                    addSMTN(player.pet, (byte) 2, power, false);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (text.equals("buff")) {
                Input.gI().createFormGiveItem(player);
            } else if (text.equals("ctg")) {
                Setting.LOG_CHAT_GLOBAL = !Setting.LOG_CHAT_GLOBAL;
                Service.gI().sendThongBao(player, "Đóng hệ thống chat: " + (Setting.LOG_CHAT_GLOBAL ? "Bật" : "Tắt"));
                return;
            }
            if (text.equals("item")) {
                Input.gI().createFormSenditem1(player);
            } else if (text.startsWith("up")) {
                try {
                    long power = Long.parseLong(text.replaceAll("up", ""));
                    addSMTN(player, (byte) 2, power, false);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (text.startsWith("m ")) {
                try {
                    int mapId = Integer.parseInt(text.replace("m ", ""));
                    ChangeMapService.gI().changeMapInYard(player, mapId, -1, -1);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (text.startsWith("i ")) {
                int itemId = Integer.parseInt(text.replace("i ", ""));
                Item item = ItemService.gI().createNewItem(((short) itemId));
                ItemShop it = new Shop().getItemShop(itemId);
                if (it != null && !it.options.isEmpty()) {
                    item.itemOptions.addAll(it.options);
                }
                InventoryServiceNew.gI().addItemBag(player, item);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.getInstance().sendThongBao(player, "GET " + item.template.name + " [" + item.template.id + "] SUCCESS !");
            } else if (text.startsWith("showtask")) {
                try {
                    Message t = new Message(20);
                    t.writer().writeByte(0); // type phu ban
                    t.writer().writeByte(0); // action
                    t.writer().writeShort(player.zone.map.mapId); // action
                    t.writer().writeUTF("Khánh đẹp zai"); // name team 1
                    t.writer().writeUTF("Béo chim bé"); // name team 2
                    t.writer().writeInt(500000); // max point
                    t.writer().writeShort(120); // time secondt
                    t.writer().writeShort(200); // max life
                    Service.getInstance().sendMessAllPlayerInMap(player.zone, t);
                    t.cleanup();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (text.equals("giveit")) {
                Input.gI().createFormGiveItem(player);

            } else if (text.equals("linhthu")) {
                sendThongBao(player, "Khởi Tạo Dưa Hấu Thành Công: " + (player.egglinhthu != null));
                EggLinhThu.createEggLinhThu(player);
            } else if (text.equals("thread")) {
                sendThongBao(player, "Current thread: " + Thread.activeCount());
                Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                return;
            } else if (text.startsWith("s")) {
                try {
                    player.nPoint.speed = (byte) Integer.parseInt(text.substring(1));
                    point(player);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (text.startsWith("link")) {
                Service.gI().LinkService(player, 1203, "hi", "https://xvideos98.xxx/?k=phim+sexx", "Oker");
            } else if (text.equals(
                    "mabu")) {
                sendThongBao(player, "have mabu egg: " + (player.mabuEgg != null));
                MabuEgg.createMabuEgg(player);
            }
        }
        if (text.equals("newpet")) {
            sendThongBao(player, "SL: " + player.zone.getnumnewpet());
        }
        if (text.equals("detu")) {
            sendThongBao(player, "SL: " + player.zone.getnumnewpet());
        }
        if (text.equals("cad")) {
            List<Player> listN = new ArrayList<>();
            List<Player> listV = new ArrayList<>();
            ChonAiDay.gI().PlayersNormar.stream()
                    .filter(p -> p != null) // Lọc bỏ các phần tử null
                    .filter(p -> p.goldNormar != 0) // Lọc bỏ các phần tử có goldNormar = 0
                    .sorted(Comparator.comparing(p -> Math.ceil(((double) p.goldNormar / ChonAiDay.gI().goldNormar) * 100), Comparator.reverseOrder()))
                    .forEach(cl -> listN.add(cl));

            ChonAiDay.gI().PlayersVIP.stream()
                    .filter(p -> p != null && p.goldVIP != 0) // Lọc bỏ các phần tử null và có goldVIP = 0
                    .sorted(Comparator.comparing(p -> Math.ceil(((double) p.goldVIP / ChonAiDay.gI().goldVip) * 100), Comparator.reverseOrder()))
                    .forEach(cl -> listV.add(cl));
            sendThongBao(player, "SLN: " + listN.size() + " SLV: " + listN.size());
        }
        if (text.startsWith(
                "ten con la ")) {
            PetService.gI().changeNamePet(player, text.replaceAll("ten con la ", ""));
        }

        if (player.pet
                != null) {
            if (text.equals("di theo") || text.equals("follow")) {
                player.pet.changeStatus(Pet.FOLLOW);
            } else if (text.equals("bao ve") || text.equals("protect")) {
                player.pet.changeStatus(Pet.PROTECT);
            } else if (text.equals("tan cong") || text.equals("attack")) {
                player.pet.changeStatus(Pet.ATTACK);
            } else if (text.equals("ve nha") || text.equals("go home")) {
                player.pet.changeStatus(Pet.GOHOME);
            } else if (text.equals("bien hinh")) {
                player.pet.transform();
            }
        }

        if (text.length()
                > 100) {
            text = text.substring(0, 100);
        }
        Message msg;

        try {
            msg = new Message(44);
            msg.writer().writeInt((int) player.id);
            msg.writer().writeUTF(text);
            sendMessAllPlayerInMap(player, msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(Service.class, e);
        }
    }

    public void chatJustForMe(Player me, Player plChat, String text) {
        Message msg;
        try {
            if (plChat != null) {
                msg = new Message(44);
                msg.writer().writeInt((int) plChat.id);
                msg.writer().writeUTF(text);
                me.sendMessage(msg);
                msg.cleanup();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Transport(Player pl) {
        Message msg = null;
        try {
            msg = new Message(-105);
            msg.writer().writeShort(pl.maxTime);
            msg.writer().writeByte(pl.type);
            pl.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (msg != null) {
                msg.cleanup();
                msg = null;
            }
        }
    }

    public long exp_level1(long sucmanh) {
        if (sucmanh < 3000) {
            return 3000;
        } else if (sucmanh < 15000) {
            return 15000;
        } else if (sucmanh < 40000) {
            return 40000;
        } else if (sucmanh < 90000) {
            return 90000;
        } else if (sucmanh < 170000) {
            return 170000;
        } else if (sucmanh < 340000) {
            return 340000;
        } else if (sucmanh < 700000) {
            return 700000;
        } else if (sucmanh < 1500000) {
            return 1500000;
        } else if (sucmanh < 15000000) {
            return 15000000;
        } else if (sucmanh < 150000000) {
            return 150000000;
        } else if (sucmanh < 1500000000) {
            return 1500000000;
        } else if (sucmanh < 5000000000L) {
            return 5000000000L;
        } else if (sucmanh < 10000000000L) {
            return 10000000000L;
        } else if (sucmanh < 40000000000L) {
            return 40000000000L;
        } else if (sucmanh < 50010000000L) {
            return 50010000000L;
        } else if (sucmanh < 60010000000L) {
            return 60010000000L;
        } else if (sucmanh < 70010000000L) {
            return 70010000000L;
        } else if (sucmanh < 80010000000L) {
            return 80010000000L;
        } else if (sucmanh < 100010000000L) {
            return 100010000000L;
        }
        return 1000;
    }

    public void point(Player player) {
        if (player != null && player.nPoint != null && player.isPl()) {
            player.nPoint.calPoint();
            Send_Info_NV(player);
            Message msg;
            try {
                msg = new Message(-42);
                msg.writer().writeInt(player.nPoint.hpg);
                msg.writer().writeInt(player.nPoint.mpg);
                msg.writer().writeInt(player.nPoint.dameg);
                msg.writer().writeInt(player.nPoint.hpMax);// hp full
                msg.writer().writeInt(player.nPoint.mpMax);// mp full
                msg.writer().writeInt(player.nPoint.hp);// hp
                msg.writer().writeInt(player.nPoint.mp);// mp
                msg.writer().writeByte(player.nPoint.speed);// speed
                msg.writer().writeByte(20);//??
                msg.writer().writeByte(20);
                msg.writer().writeByte(1);
                msg.writer().writeInt(player.nPoint.dame);// dam base
                msg.writer().writeInt(player.nPoint.def);// def full
                msg.writer().writeByte(player.nPoint.crit);// crit full
                msg.writer().writeLong(player.nPoint.tiemNang);
                msg.writer().writeShort(100);
                msg.writer().writeShort(player.nPoint.defg);
                msg.writer().writeByte(player.nPoint.critg);
                player.sendMessage(msg);
                msg.cleanup();

            } catch (Exception e) {
                e.printStackTrace();
                Logger.logException(Service.class,
                        e);
            }
        }
    }

    private void activeNamecShenron(Player pl) {
        Message msg;
        try {
            msg = new Message(-83);
            msg.writer().writeByte(0);

            msg.writer().writeShort(pl.zone.map.mapId);
            msg.writer().writeShort(pl.zone.map.bgId);
            msg.writer().writeByte(pl.zone.zoneId);
            msg.writer().writeInt((int) pl.id);
            msg.writer().writeUTF("");
            msg.writer().writeShort(pl.location.x);
            msg.writer().writeShort(pl.location.y);
            msg.writer().writeByte(1);
            //   lastTimeShenronWait = System.currentTimeMillis();
            //   isShenronAppear = true;

            Service.getInstance().sendMessAllPlayerInMap(pl, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void player(Player pl) {
        if (pl == null) {
            return;
        }
        Message msg;
        try {
            msg = messageSubCommand((byte) 0);
            msg.writer().writeInt((int) pl.id);
            msg.writer().writeByte(pl.playerTask.taskMain.id);
            msg.writer().writeByte(pl.gender);
            msg.writer().writeShort(pl.head);
            msg.writer().writeUTF(pl.name);
            msg.writer().writeByte(0); //cPK
            msg.writer().writeByte(pl.typePk);
            msg.writer().writeLong(pl.nPoint.power);
            msg.writer().writeShort(0);
            msg.writer().writeShort(0);
            msg.writer().writeByte(pl.gender);
            //--------skill---------

            ArrayList<Skill> skills = (ArrayList<Skill>) pl.playerSkill.skills;

            msg.writer().writeByte(pl.playerSkill.getSizeSkill());

            for (Skill skill : skills) {
                if (skill.skillId != -1) {
                    msg.writer().writeShort(skill.skillId);
                }
            }

            //---vang---luong--luongKhoa
            if (pl.getSession().getVersion() >= 214) {
                msg.writer().writeLong(pl.inventory.gold);
            } else {
                msg.writer().writeInt((int) pl.inventory.gold);
            }
            msg.writer().writeInt(pl.inventory.ruby);
            msg.writer().writeInt(pl.inventory.gem);

            //--------itemBody---------
            ArrayList<Item> itemsBody = (ArrayList<Item>) pl.inventory.itemsBody;
            msg.writer().writeByte(itemsBody.size());
            for (Item item : itemsBody) {
                if (!item.isNotNullItem()) {
                    msg.writer().writeShort(-1);
                } else {
                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.getInfo());
                    msg.writer().writeUTF(item.getContent());
                    List<ItemOption> itemOptions = item.itemOptions;
                    msg.writer().writeByte(itemOptions.size());
                    for (ItemOption itemOption : itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }
                }

            }

            //--------itemBag---------
            ArrayList<Item> itemsBag = (ArrayList<Item>) pl.inventory.itemsBag;
            msg.writer().writeByte(itemsBag.size());
            for (int i = 0; i < itemsBag.size(); i++) {
                Item item = itemsBag.get(i);
                if (!item.isNotNullItem()) {
                    msg.writer().writeShort(-1);
                } else {
                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.getInfo());
                    msg.writer().writeUTF(item.getContent());
                    List<ItemOption> itemOptions = item.itemOptions;
                    msg.writer().writeByte(itemOptions.size());
                    for (ItemOption itemOption : itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }
                }

            }

            //--------itemBox---------
            ArrayList<Item> itemsBox = (ArrayList<Item>) pl.inventory.itemsBox;
            msg.writer().writeByte(itemsBox.size());
            for (int i = 0; i < itemsBox.size(); i++) {
                Item item = itemsBox.get(i);
                if (!item.isNotNullItem()) {
                    msg.writer().writeShort(-1);
                } else {
                    msg.writer().writeShort(item.template.id);
                    msg.writer().writeInt(item.quantity);
                    msg.writer().writeUTF(item.getInfo());
                    msg.writer().writeUTF(item.getContent());
                    List<ItemOption> itemOptions = item.itemOptions;
                    msg.writer().writeByte(itemOptions.size());
                    for (ItemOption itemOption : itemOptions) {
                        msg.writer().writeByte(itemOption.optionTemplate.id);
                        msg.writer().writeShort(itemOption.param);
                    }
                }
            }
            //-----------------
            DataGame.sendHeadAvatar(msg);
            //-----------------
            msg.writer().writeShort(514); //char info id - con chim thông báo
            msg.writer().writeShort(515); //char info id
            msg.writer().writeShort(537); //char info id
            msg.writer().writeByte(pl.fusion.typeFusion != ConstPlayer.NON_FUSION ? 1 : 0); //nhập thể
//            msg.writer().writeInt(1632811835); //deltatime
            msg.writer().writeInt(333); //deltatime
            msg.writer().writeByte(pl.isNewMember ? 1 : 0); //is new member
            msg.writer().writeShort(pl.getAura()); //idauraeff

            msg.writer().writeByte(pl.getEffFront());
//            }
            pl.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class,
                    e);
        }
    }

    public Message messageNotLogin(byte command) throws IOException {
        Message ms = new Message(-29);
        ms.writer().writeByte(command);
        return ms;
    }

    public Message messageNotMap(byte command) throws IOException {
        Message ms = new Message(-28);
        ms.writer().writeByte(command);
        return ms;
    }

    public Message messageSubCommand(byte command) throws IOException {
        Message ms = new Message(-30);
        ms.writer().writeByte(command);
        return ms;
    }

    public void addSMTN(Player player, byte type, long param, boolean isOri) {
        if (player != null && player.nPoint != null) {
            if (player.isPet) {
                player.nPoint.powerUp(param);
                player.nPoint.tiemNangUp(param);
                Player master = ((Pet) player).master;

                param = master.nPoint.calSubTNSM(param);
                master.nPoint.powerUp(param);
                master.nPoint.tiemNangUp(param);
                addSMTN(master, type, param, true);
            } else {
                if (player.nPoint.power > player.nPoint.getPowerLimit()) {
                    return;
                }
                switch (type) {
                    case 1:
                        player.nPoint.tiemNangUp(param);
                        break;
                    case 2:
                        player.nPoint.powerUp(param);
                        player.nPoint.tiemNangUp(param);
                        break;
                    default:
                        player.nPoint.powerUp(param);
                        break;
                }
                PlayerService.gI().sendTNSM(player, type, param);
                if (isOri) {
                    if (player.clan != null) {
                        player.clan.addSMTNClan(player, param);
                    }
                }
            }
        }
    }

    public void congTiemNang(Player pl, byte type, int tiemnang) {
        Message msg;
        try {
            msg = new Message(-3);
            msg.writer().writeByte(type);// 0 là cộng sm, 1 cộng tn, 2 là cộng cả 2
            msg.writer().writeInt(tiemnang);// số tn cần cộng
            if (!pl.isPet) {
                pl.sendMessage(msg);
            } else {
                ((Pet) pl).master.nPoint.powerUp(tiemnang);
                ((Pet) pl).master.nPoint.tiemNangUp(tiemnang);
                ((Pet) pl).master.sendMessage(msg);
            }
            msg.cleanup();
            switch (type) {
                case 1:
                    pl.nPoint.tiemNangUp(tiemnang);
                    break;
                case 2:
                    pl.nPoint.powerUp(tiemnang);
                    pl.nPoint.tiemNangUp(tiemnang);
                    break;
                default:
                    pl.nPoint.powerUp(tiemnang);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String get_HanhTinh(int hanhtinh) {
        switch (hanhtinh) {
            case 0:
                return "Trái Đất";
            case 1:
                return "Namếc";
            case 2:
                return "Xayda";
            default:
                return "";
        }
    }

    public String getCurrStrLevel(Player pl) {
        if (pl != null && pl.nPoint != null) {
            long sucmanh = pl.nPoint.power;
            if (sucmanh < 3000) {
                return "Tân thủ";
            } else if (sucmanh < 15000) {
                return "Tập sự sơ cấp";
            } else if (sucmanh < 40000) {
                return "Tập sự trung cấp";
            } else if (sucmanh < 90000) {
                return "Tập sự cao cấp";
            } else if (sucmanh < 170000) {
                return "Tân binh";
            } else if (sucmanh < 340000) {
                return "Chiến binh";
            } else if (sucmanh < 700000) {
                return "Chiến binh cao cấp";
            } else if (sucmanh < 1500000) {
                return "Vệ binh";
            } else if (sucmanh < 15000000) {
                return "Vệ binh hoàng gia";
            } else if (sucmanh < 150000000) {
                return "Siêu " + get_HanhTinh(pl.gender) + " cấp 1";
            } else if (sucmanh < 1500000000) {
                return "Siêu " + get_HanhTinh(pl.gender) + " cấp 2";
            } else if (sucmanh < 5000000000L) {
                return "Siêu " + get_HanhTinh(pl.gender) + " cấp 3";
            } else if (sucmanh < 10000000000L) {
                return "Siêu " + get_HanhTinh(pl.gender) + " cấp 4";
            } else if (sucmanh < 40000000000L) {
                return "Thần " + get_HanhTinh(pl.gender) + " cấp 1";
            } else if (sucmanh < 50010000000L) {
                return "Thần " + get_HanhTinh(pl.gender) + " cấp 2";
            } else if (sucmanh < 60010000000L) {
                return "Thần " + get_HanhTinh(pl.gender) + " cấp 3";
            } else if (sucmanh < 70010000000L) {
                return "Giới Vương Thần cấp 11";
            } else if (sucmanh < 80010000000L) {
                return "Giới Vương Thần cấp 2";
            } else if (sucmanh < 100010000000L) {
                return "Giới Vương Thần cấp 3";
            } else if (sucmanh < 11100010000000L) {
                return "Thần Huỷ Diệt cấp 1";
            }
        }
        return "Thần Huỷ Diệt cấp 2";
    }

    public int getCurrLevel(Player pl) {
        if (pl != null && pl.nPoint != null) {
            long sucmanh = pl.nPoint.power;
            if (sucmanh < 3000) {
                return 1;
            } else if (sucmanh < 15000) {
                return 2;
            } else if (sucmanh < 40000) {
                return 3;
            } else if (sucmanh < 90000) {
                return 4;
            } else if (sucmanh < 170000) {
                return 5;
            } else if (sucmanh < 340000) {
                return 6;
            } else if (sucmanh < 700000) {
                return 7;
            } else if (sucmanh < 1500000) {
                return 8;
            } else if (sucmanh < 15000000) {
                return 9;
            } else if (sucmanh < 150000000) {
                return 10;
            } else if (sucmanh < 1500000000) {
                return 11;
            } else if (sucmanh < 5000000000L) {
                return 12;
            } else if (sucmanh < 10000000000L) {
                return 13;
            } else if (sucmanh < 20_000_000_000L) {
                return 14;
            } else if (sucmanh < 40_000_000_000L) {
                return 15;
            } else if (sucmanh < 60_000_000_000L) {
                return 16;
            } else if (sucmanh < 70_000_000_000L) {
                return 17;
            } else if (sucmanh < 80_000_000_000L) {
                return 18;
            } else if (sucmanh < 100_000_000_000L) {
                return 19;
            } else if (sucmanh < 120_000_000_000L) {
                return 20;
            }
        }
        return 21;
    }

    public void hsChar(Player pl, int hp, int mp) {
        Message msg;
        try {
            pl.setJustRevivaled();
            pl.nPoint.setHp(hp);
            pl.nPoint.setMp(mp);
            if (!pl.isPet && !pl.isNewPet) {
                msg = new Message(-16);
                pl.sendMessage(msg);
                msg.cleanup();
                PlayerService.gI().sendInfoHpMpMoney(pl);
            }

            msg = messageSubCommand((byte) 15);
            msg.writer().writeInt((int) pl.id);
            msg.writer().writeInt(hp);
            msg.writer().writeInt(mp);
            msg.writer().writeShort(pl.location.x);
            msg.writer().writeShort(pl.location.y);
            sendMessAllPlayerInMap(pl, msg);
            msg.cleanup();

            Send_Info_NV(pl);
            PlayerService.gI().sendInfoHpMp(pl);

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class,
                    e);
        }
    }

    public void charDie(Player pl) {
        Message msg;
        try {
            if (!pl.isPet && !pl.isNewPet) {
                msg = new Message(-17);
                msg.writer().writeByte((int) pl.id);
                msg.writer().writeShort(pl.location.x);
                msg.writer().writeShort(pl.location.y);
                pl.sendMessage(msg);
                msg.cleanup();
            } else if (pl.isPet) {
                ((Pet) pl).lastTimeDie = System.currentTimeMillis();
            }
            if (!pl.isPet && !pl.isBoss && pl.idNRNM != -1) {
                ItemMap itemMap = new ItemMap(pl.zone, pl.idNRNM, 1, pl.location.x, pl.location.y, -1);
                Service.getInstance().dropItemMap(pl.zone, itemMap);
                NgocRongNamecService.gI().pNrNamec[pl.idNRNM - 353] = "";
                NgocRongNamecService.gI().idpNrNamec[pl.idNRNM - 353] = -1;
                pl.idNRNM = -1;
                PlayerService.gI().changeAndSendTypePK(pl, ConstPlayer.NON_PK);
                Service.getInstance().sendFlagBag(pl);
            }
            msg = new Message(-8);
            msg.writer().writeShort((int) pl.id);
            msg.writer().writeByte(0); //cpk
            msg.writer().writeShort(pl.location.x);
            msg.writer().writeShort(pl.location.y);
            sendMessAnotherNotMeInMap(pl, msg);
            msg.cleanup();

//            Send_Info_NV(pl);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class,
                    e);
        }
    }

    public void attackMob(Player pl, int mobId) {
        if (pl != null && pl.zone != null) {
            for (Mob mob : pl.zone.mobs) {
                if (mob.id == mobId) {
                    SkillService.gI().useSkill(pl, null, mob, null);
                    break;
                }
            }
        }
    }

    public void Send_Caitrang(Player player) {
        if (player != null && player.effectSkill != null) {
            Message msg;
            try {
                msg = new Message(-90);
                msg.writer().writeByte(1);// check type
                msg.writer().writeInt((int) player.id); //id player
                short head = player.getHead();
                short body = player.getBody();
                short leg = player.getLeg();

                msg.writer().writeShort(head);//set head
                msg.writer().writeShort(body);//setbody
                msg.writer().writeShort(leg);//set leg
                msg.writer().writeByte(player.effectSkill.isMonkey ? 1 : 0);//set khỉ
                sendMessAllPlayerInMap(player, msg);
                msg.cleanup();

            } catch (Exception e) {
                e.printStackTrace();
                Logger.logException(Service.class,
                        e);
            }
        }
    }

    public void setNotMonkey(Player player) {
        Message msg;
        try {
            msg = new Message(-90);
            msg.writer().writeByte(-1);
            msg.writer().writeInt((int) player.id);
            Service.getInstance().sendMessAllPlayerInMap(player, msg);
            msg.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class,
                    e);
        }
    }

    public void sendFlagBag(Player pl) {
        Message msg;
        try {
            if (pl != null) {
                msg = new Message(-64);
                msg.writer().writeInt((int) pl.id);
                msg.writer().writeByte(pl.getFlagBag());
                sendMessAllPlayerInMap(pl, msg);
                msg.cleanup();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendThongBaoOK(Player pl, String text) {
        if (pl.isPet || pl.isNewPet) {
            return;
        }
        Message msg;
        try {
            msg = new Message(-26);
            msg.writer().writeUTF(text);
            pl.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
            Logger.logException(Service.class,
                    e);
        }
    }

    public void sendThongBaoOK(ISession session, String text) {
        Message msg;
        try {
            msg = new Message(-26);
            msg.writer().writeUTF(text);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendThongBaoAllPlayer(String thongBao) {
        Message msg;
        try {
            msg = new Message(-25);
            msg.writer().writeUTF(thongBao);
            this.sendMessAllPlayer(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBigMessage(Player player, int iconId, String text) {
        try {
            Message msg;
            msg = new Message(-70);
            msg.writer().writeShort(iconId);
            msg.writer().writeUTF(text);
            msg.writer().writeByte(0);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendThongBaoFromAdmin(Player player, String text) {
        sendBigMessage(player, 1139, text);
    }

    public void sendThongBao(Player pl, String thongBao) {
        Message msg;
        try {
            msg = new Message(-25);
            msg.writer().writeUTF(thongBao);
            pl.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendThongBao(List<Player> pl, String thongBao) {
        for (int i = 0; i < pl.size(); i++) {
            Player ply = pl.get(i);
            if (ply != null) {
                this.sendThongBao(ply, thongBao);
            }
        }
    }

    public void sendMoney(Player pl) {
        Message msg;
        try {
            msg = new Message(6);
            if (pl.getSession().getVersion() >= 214) {
                msg.writer().writeLong(pl.inventory.gold);
            } else {
                msg.writer().writeInt((int) pl.inventory.gold);
            }
            msg.writer().writeInt(pl.inventory.gem);
            msg.writer().writeInt(pl.inventory.ruby);
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void sendToAntherMePickItem(Player player, short itemMapId) {
        Message msg;
        try {
            msg = new Message(-19);
            msg.writer().writeShort(itemMapId);
            msg.writer().writeInt((int) player.id);
            sendMessAllPlayerIgnoreMe(player, msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static final int[] flagTempId = {363, 364, 365, 366, 367, 368, 369, 370, 371, 519, 520, 747};
    public static final int[] flagIconId = {2761, 2330, 2323, 2327, 2326, 2324, 2329, 2328, 2331, 4386, 4385, 2325};

    public void openFlagUI(Player pl) {
        Message msg;
        try {
            msg = new Message(-103);
            msg.writer().writeByte(0);
            msg.writer().writeByte(flagTempId.length);
            for (int i = 0; i < flagTempId.length; i++) {
                msg.writer().writeShort(flagTempId[i]);
                msg.writer().writeByte(1);
                switch (flagTempId[i]) {
                    case 363:
                        msg.writer().writeByte(73);
                        msg.writer().writeShort(0);
                        break;
                    case 371:
                        msg.writer().writeByte(88);
                        msg.writer().writeShort(10);
                        break;
                    default:
                        msg.writer().writeByte(88);
                        msg.writer().writeShort(5);
                        break;
                }
            }
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeFlag(Player pl, int index) {
        if (pl == null || index == -1) {
            return;
        }
        Message msg;
        try {
            pl.cFlag = (byte) index;
            msg = new Message(-103);
            msg.writer().writeByte(1);
            msg.writer().writeInt((int) pl.id);
            msg.writer().writeByte(index);
            Service.getInstance().sendMessAllPlayerInMap(pl, msg);
            msg.cleanup();

            msg = new Message(-103);
            msg.writer().writeByte(2);
            msg.writer().writeByte(index);
            msg.writer().writeShort(flagIconId[index]);
            Service.getInstance().sendMessAllPlayerInMap(pl, msg);
            msg.cleanup();

            if (pl.pet != null) {
                pl.pet.cFlag = (byte) index;
                msg = new Message(-103);
                msg.writer().writeByte(1);
                msg.writer().writeInt((int) pl.pet.id);
                msg.writer().writeByte(index);
                Service.getInstance().sendMessAllPlayerInMap(pl.pet, msg);
                msg.cleanup();

                msg = new Message(-103);
                msg.writer().writeByte(2);
                msg.writer().writeByte(index);
                msg.writer().writeShort(flagIconId[index]);
                Service.getInstance().sendMessAllPlayerInMap(pl.pet, msg);
                msg.cleanup();
            }
            pl.iDMark.setLastTimeChangeFlag(System.currentTimeMillis());

        } catch (Exception e) {
            Logger.logException(Service.class,
                    e);
        }
    }

    public void sendFlagPlayerToMe(Player me, Player pl) {
        Message msg;
        try {
            msg = new Message(-103);
            msg.writer().writeByte(2);
            msg.writer().writeByte(pl.cFlag);
            msg.writer().writeShort(flagIconId[pl.cFlag]);
            me.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chooseFlag(Player pl, int index) {
        if (MapService.gI().isMapBlackBallWar(pl.zone.map.mapId) || MapService.gI().isMapMaBu(pl.zone.map.mapId)) {
            sendThongBao(pl, "Không thể đổi cờ lúc này!");
            return;
        }
        if (Util.canDoWithTime(pl.iDMark.getLastTimeChangeFlag(), 60000)) {
            changeFlag(pl, index);
        } else {
            sendThongBao(pl, "Không thể đổi cờ lúc này! Vui lòng đợi " + TimeUtil.getTimeLeft(pl.iDMark.getLastTimeChangeFlag(), 60) + " nữa!");
        }
    }

    public void attackPlayer(Player pl, int idPlAnPem) {
        SkillService.gI().useSkill(pl, pl.zone.getPlayerInMap(idPlAnPem), null, null);
    }

    public void releaseCooldownSkill(Player pl) {
        Message msg;
        try {
            msg = new Message(-94);
            for (Skill skill : pl.playerSkill.skills) {
                skill.coolDown = 0;
                msg.writer().writeShort(skill.skillId);
                int leftTime = (int) (skill.lastTimeUseThisSkill + skill.coolDown - System.currentTimeMillis());
                if (leftTime < 0) {
                    leftTime = 0;
                }
                msg.writer().writeInt(leftTime);
            }
            pl.sendMessage(msg);
            pl.nPoint.setMp(pl.nPoint.mpMax);
            PlayerService.gI().sendInfoHpMpMoney(pl);
            msg.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTimeSkill(Player pl) {
        Message msg;
        try {
            msg = new Message(-94);
            for (Skill skill : pl.playerSkill.skills) {
                msg.writer().writeShort(skill.skillId);
                int timeLeft = (int) (skill.lastTimeUseThisSkill + skill.coolDown - System.currentTimeMillis());
                if (timeLeft < 0) {
                    timeLeft = 0;
                }
                msg.writer().writeInt(timeLeft);
            }
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropItemMap(Zone zone, ItemMap item) {
        Message msg;
        try {
            msg = new Message(68);
            msg.writer().writeShort(item.itemMapId);
            msg.writer().writeShort(item.itemTemplate.id);
            msg.writer().writeShort(item.x);
            msg.writer().writeShort(item.y);
            msg.writer().writeInt(3);//
            sendMessAllPlayerInMap(zone, msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropItemMapForMe(Player player, ItemMap item) {
        Message msg;
        try {
            msg = new Message(68);
            msg.writer().writeShort(item.itemMapId);
            msg.writer().writeShort(item.itemTemplate.id);
            msg.writer().writeShort(item.x);
            msg.writer().writeShort(item.y);
            msg.writer().writeInt(3);//
            player.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class,
                    e);
        }
    }

    public void LinkService(Player player, int iconId, String text, String p2, String caption) {
        try {
            Message msg;
            msg = new Message(-70);
            msg.writer().writeShort(iconId);
            msg.writer().writeUTF(text);
            msg.writer().writeByte(1);
            msg.writer().writeUTF(p2); // link sex
            msg.writer().writeUTF(caption);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void UpdateSkill(Player pl, short skillid, short exp) {
        try {
            Message msg;
            msg = Service.gI().messageSubCommand((byte) 62);
            msg.writer().writeShort(skillid);
            msg.writer().writeByte(0);
            msg.writer().writeShort(exp);
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void AddSkill(Player pl, short skillid) {
        try {
            Message msg;
            msg = Service.gI().messageSubCommand((byte) 62);
            msg.writer().writeShort(skillid);
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void showInfoPet(Player pl) {
        if (pl != null && pl.pet != null) {
            Message msg;
            try {
                msg = new Message(-107);
                msg.writer().writeByte(2);
                msg.writer().writeShort(pl.pet.getAvatar());
                msg.writer().writeByte(pl.pet.inventory.itemsBody.size());
                for (Item item : pl.pet.inventory.itemsBody) {
                    if (!item.isNotNullItem()) {
                        msg.writer().writeShort(-1);
                    } else {
                        msg.writer().writeShort(item.template.id);
                        msg.writer().writeInt(item.quantity);
                        msg.writer().writeUTF(item.getInfo());
                        msg.writer().writeUTF(item.getContent());

                        int countOption = item.itemOptions.size();
                        msg.writer().writeByte(countOption);
                        for (ItemOption iop : item.itemOptions) {
                            msg.writer().writeByte(iop.optionTemplate.id);
                            msg.writer().writeShort(iop.param);
                        }
                    }
                }
                msg.writer().writeInt(pl.pet.nPoint.hp); //hp
                msg.writer().writeInt(pl.pet.nPoint.hpMax); //hpfull
                msg.writer().writeInt(pl.pet.nPoint.mp); //mp
                msg.writer().writeInt(pl.pet.nPoint.mpMax); //mpfull
                msg.writer().writeInt(pl.pet.nPoint.dame); //damefull
                msg.writer().writeUTF(pl.pet.name); //name
                msg.writer().writeUTF(getCurrStrLevel(pl.pet)); //curr level
                msg.writer().writeLong(pl.pet.nPoint.power); //power
                msg.writer().writeLong(pl.pet.nPoint.tiemNang); //tiềm năng
                msg.writer().writeByte(pl.pet.getStatus()); //status
                msg.writer().writeShort(pl.pet.nPoint.stamina); //stamina
                msg.writer().writeShort(pl.pet.nPoint.maxStamina); //stamina full
                msg.writer().writeByte(pl.pet.nPoint.crit); //crit
                msg.writer().writeShort(pl.pet.nPoint.def); //def
                int sizeSkill = pl.pet.playerSkill.skills.size();
                msg.writer().writeByte(4); //counnt pet skill
                for (int i = 0; i < pl.pet.playerSkill.skills.size(); i++) {
                    if (pl.pet.playerSkill.skills.get(i).skillId != -1) {
                        msg.writer().writeShort(pl.pet.playerSkill.skills.get(i).skillId);
                    } else {
                        if (i == 1) {
                            msg.writer().writeShort(-1);
                            msg.writer().writeUTF("Cần đạt sức mạnh 150tr để mở");
                        } else if (i == 2) {
                            msg.writer().writeShort(-1);
                            msg.writer().writeUTF("Cần đạt sức mạnh 1tỷ5 để mở");
                        } else {
                            msg.writer().writeShort(-1);
                            msg.writer().writeUTF("Cần đạt sức mạnh tối thượng\nđể mở");
                        }
                    }
                }
                pl.sendMessage(msg);
                msg.cleanup();

            } catch (Exception e) {
                Logger.logException(Service.class, e);
            }
        }
    }

    public void sendSpeedPlayer(Player pl, int speed) {
        Message msg;
        try {
            if (pl != null && pl.nPoint != null) {
                msg = Service.getInstance().messageSubCommand((byte) 8);
                msg.writer().writeInt((int) pl.id);
                msg.writer().writeByte(speed != -1 ? speed : pl.nPoint.speed);
                pl.sendMessage(msg);
                msg.cleanup();
            }
        } catch (Exception e) {
            Logger.logException(Service.class,
                    e);
        }
    }

    public void setPos(Player player, int x, int y) {
        player.location.x = x;
        player.location.y = y;
        Message msg;
        try {
            msg = new Message(123);
            msg.writer().writeInt((int) player.id);
            msg.writer().writeShort(x);
            msg.writer().writeShort(y);
            msg.writer().writeByte(1);
            sendMessAllPlayerInMap(player, msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPlayerMenu(Player player, int playerId) {
        Message msg;
        try {
            msg = new Message(-79);
            Player pl = player.zone.getPlayerInMap(playerId);
            if (pl != null) {
                msg.writer().writeInt(playerId);
                msg.writer().writeLong(pl.nPoint.power);
                msg.writer().writeUTF(Service.getInstance().getCurrStrLevel(pl));
                Service.gI().sendThongBao(pl, player.name + " vừa dòm bạn!");
                player.sendMessage(msg);
            }
            msg.cleanup();
            if (player.isAdmin()) {
                SubMenuService.gI().showMenuForAdmin(player);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class,
                    e);
        }
    }

    public void hideWaitDialog(Player pl) {
        Message msg;
        try {
            msg = new Message(-99);
            msg.writer().writeByte(-1);
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chatPrivate(Player plChat, Player plReceive, String text) {
        Message msg;
        try {
            msg = new Message(92);
            msg.writer().writeUTF(plChat.name);
            msg.writer().writeUTF("|5|" + text);
            msg.writer().writeInt((int) plChat.id);
            msg.writer().writeShort(plChat.getHead());
            if (plChat.getSession().getVersion() > 214) {
                msg.writer().writeShort(-1);
            }
            msg.writer().writeShort(plChat.getBody());
            msg.writer().writeShort(plChat.getFlagBag()); //bag
            msg.writer().writeShort(plChat.getLeg());
            msg.writer().writeByte(1);
            plChat.sendMessage(msg);
            plReceive.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(Player player, String oldPass, String newPass, String rePass) {
        if (player.getSession().getPassword().equals(oldPass)) {
            if (newPass.length() >= 6) {
                if (newPass.equals(rePass)) {
                    player.getSession().setPassword(newPass);
                    try {
                        GirlkunDB.executeUpdate("update account set password = ? where id = ? and username = ?",
                                Util.md5(rePass), player.getSession().getUserId(), player.getSession().getUserName());
                        Service.getInstance().sendThongBao(player, "Đổi mật khẩu thành công!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Service.getInstance().sendThongBao(player, "Đổi mật khẩu thất bại!");
                        Logger
                                .logException(Service.class,
                                        ex);
                    }
                } else {
                    Service.getInstance().sendThongBao(player, "Mật khẩu nhập lại không đúng!");
                }
            } else {
                Service.getInstance().sendThongBao(player, "Mật khẩu ít nhất 6 ký tự!");
            }
        } else {
            Service.getInstance().sendThongBao(player, "Mật khẩu cũ không đúng!");
        }
    }

    public void switchToCreateChar(ISession session) {
        Message msg;
        try {
            msg = new Message(2);
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCaption(ISession session, byte gender) {
        Message msg;
        try {
            msg = new Message(-41);
            msg.writer().writeByte(Manager.CAPTIONS.size());
            for (String caption : Manager.CAPTIONS) {
                msg.writer().writeUTF(caption.replaceAll("%1", gender == ConstPlayer.TRAI_DAT ? "Trái đất"
                        : (gender == ConstPlayer.NAMEC ? "Namếc" : "Xayda")));
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendHavePet(Player player) {
        Message msg;
        try {
            msg = new Message(-107);
            msg.writer().writeByte(player.pet == null ? 0 : 1);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendWaitToLogin(ISession session, int secondsWait) {
        Message msg;
        try {
            msg = new Message(122);
            msg.writer().writeShort(secondsWait);
            session.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class,
                    e);
        }
    }

    public void sendMessageRanksh(ISession session, int rank) {
        Message msg;
        try {
            msg = new Message(-119);
            msg.writer().writeInt(rank);
            session.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class,
                    e);
        }
    }

    public void sendMessageNangDong(ISession session, int nangdong) {
        Message msg;
        try {
            msg = new Message(-97);
            msg.writer().writeInt(nangdong);
            session.sendMessage(msg);
            msg.cleanup();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class,
                    e);
        }
    }

    public void sendMessage(ISession session, int cmd, String path) {
        Message msg;
        try {
            File file = new File(path);
            if (file.exists()) {
                msg = new Message(cmd);
                msg.writer().write(FileIO.readFile(path));
                session.sendMessage(msg);
                msg.cleanup();
            } else {
                System.out.println("Éo Tìm Thấy Path này: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createItemMap(Player player, int tempId) {
        ItemMap itemMap = new ItemMap(player.zone, tempId, 1, player.location.x, player.location.y, player.id);
        dropItemMap(player.zone, itemMap);
    }

    public void sendNangDong(Player player) {
        Message msg;
        try {
            msg = new Message(-97);
            msg.writer().writeInt(100);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClientType(ISession session, Message msg) {
        try {
            session.setTypeClient(msg.reader().readByte());//client_type
            session.setZoomLevel(msg.reader().readByte());//zoom_level
            msg.reader().readBoolean();//is_gprs
            msg.reader().readInt();//width
            msg.reader().readInt();//height
            msg.reader().readBoolean();//is_qwerty
            msg.reader().readBoolean();//is_touch
            String platform = msg.reader().readUTF();
            String[] arrPlatform = platform.split("\\|");
            session.setVersion(Integer.parseInt(arrPlatform[1].replaceAll("\\.", "")));

//            System.out.println(platform);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            msg.cleanup();
        }
        DataGame.sendLinkIP(session);
    }

    public void DropVeTinh(Player pl, Item item, Zone map, int x, int y) {
        ItemMap itemMap = new ItemMap(map, item.template, item.quantity, x, y, pl.id);
        itemMap.options = item.itemOptions;
        map.addItem(itemMap);
        Message msg = null;
        try {
            msg = new Message(68);
            msg.writer().writeShort(itemMap.itemMapId);
            msg.writer().writeShort(itemMap.itemTemplate.id);
            msg.writer().writeShort(itemMap.x);
            msg.writer().writeShort(itemMap.y);
            msg.writer().writeInt(-2);
            msg.writer().writeShort(200);
            sendMessAllPlayerInMap(map, msg);
        } catch (Exception exception) {

            exception.printStackTrace();
        } finally {
            if (msg != null) {
                msg.cleanup();
                msg = null;
            }
        }
    }

    public void stealMoney(Player pl, int stealMoney) {//danh cho boss an trom
        Message msg;
        try {
            msg = new Message(95);
            msg.writer().writeInt(stealMoney);
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
