package com.girlkun.models.npc;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.network.io.Message;
import com.girlkun.server.Client;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;
import java.util.List;

public abstract class Npc implements IAtionNpc {

    public int mapId;
    public Map map;

    public int status;

    public int cx;

    public int cy;

    public int tempId;

    public int avartar;

    public BaseMenu baseMenu;

    protected Npc(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        this.map = MapService.gI().getMapById(mapId);
        this.mapId = mapId;
        this.status = status;
        this.cx = cx;
        this.cy = cy;
        this.tempId = tempId;
        this.avartar = avartar;
        Manager.NPCS.add(this);
    }

    public void initBaseMenu(String text) {
        text = text.substring(1);
        String[] data = text.split("\\|");
        baseMenu = new BaseMenu();
        baseMenu.npcId = tempId;
        baseMenu.npcSay = data[0].replaceAll("<>", "\n");
        baseMenu.menuSelect = new String[data.length - 1];
        for (int i = 0; i < baseMenu.menuSelect.length; i++) {
            baseMenu.menuSelect[i] = data[i + 1].replaceAll("<>", "\n");
        }
    }

    public void createOtherMenu(Player player, int indexMenu, String npcSay, String... menuSelect) {
        Message msg;
        try {
            player.iDMark.setIndexMenu(indexMenu);
            msg = new Message(32);
            msg.writer().writeShort(tempId);
            msg.writer().writeUTF(npcSay);
            msg.writer().writeByte(menuSelect.length);
            for (String menu : menuSelect) {
                msg.writer().writeUTF(menu);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createOtherMenu(Player player, int indexMenu, String npcSay, String[] menuSelect, Object object) {
        NpcFactory.PLAYERID_OBJECT.put(player.id, object);
        Message msg;
        try {
            player.iDMark.setIndexMenu(indexMenu);
            msg = new Message(32);
            msg.writer().writeShort(tempId);
            msg.writer().writeUTF(npcSay);
            msg.writer().writeByte(menuSelect.length);
            for (String menu : menuSelect) {
                msg.writer().writeUTF(menu);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {
            player.iDMark.setIndexMenu(ConstNpc.BASE_MENU);
            try {
                if (baseMenu != null) {
                    baseMenu.openMenu(player);
                } else {
                    Message msg;
                    msg = new Message(32);
                    msg.writer().writeShort(tempId);
                    msg.writer().writeUTF("NPC[" + this.tempId + "][" + this.mapId + "]\n Đang cập nhật...");
                    msg.writer().writeByte(1);
                    msg.writer().writeUTF("Không");
                    player.sendMessage(msg);
                    msg.cleanup();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.logException(Npc.class, e);
            }
        }
    }

    public void npcChat(Player player, String text) {
        Message msg;
        try {
            msg = new Message(124);
            msg.writer().writeShort(tempId);
            msg.writer().writeUTF(text);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class, e);
        }
    }

    public long LastTimeAutoChat = 0;

    public void udNpc() {
        try {
            if (this.map != null) {
                AutoChat();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi ở ndNpc class Npc");
        }
    }

    public void AutoChat() {
        try {
            if (this.map != null) {
                if (!this.map.zones.isEmpty()) {
                    List<Zone> zoness = this.map.zones;
                    if (!zoness.isEmpty()) {
                        for (int i = 0; i < zoness.size(); i++) {
                            Zone zone = zoness.get(i);
                            if (zone != null && !zone.getPlayers().isEmpty()) {
                                List<Player> players = zone.getPlayers();
                                if (!players.isEmpty()) {
                                    for (int j = 0; j < players.size(); j++) {
                                        Player pl = players.get(j);
                                        if (pl != null && pl.zone != null && pl.zone.map != null
                                                && pl.zone.map.mapId == mapId && pl.isPl()) {
                                            if (Util.canDoWithTime(LastTimeAutoChat, 5000)) {
                                                npcChat(pl, getText(tempId));
                                                LastTimeAutoChat = System.currentTimeMillis();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi ở autochat class Npc");
        }
    }

    private String getText(int id) {
        if (id == 13) {
            return textQuyLao[Util.nextInt(0, textQuyLao.length - 1)];
        } else if (id == 54) {
            return textLyTieuNuong[Util.nextInt(0, textLyTieuNuong.length - 1)];
        } else if (id == 22) {
            return textLyTrongTai[Util.nextInt(0, textLyTrongTai.length - 1)];
        } else if (id == 98) {
            return noel[Util.nextInt(0, noel.length - 1)];
        } else {
            return textSanta[Util.nextInt(0, textSanta.length - 1)];
        }
    }

    private static final String[] textLyTieuNuong = new String[]{
        "Con số gì đây..",};

    private static final String[] textLyTrongTai = new String[]{
        "Đại Hội Võ Thuật lần thứ 23 đã chính thức khai mạc",
        "Còn chờ gì nữa mà không đăng kí tham gia để nhận nhiều phẩn quà hấp dẫn"
    };
    private static final String[] noel = new String[]{
        "Hô Hô Hô bất ngờ chưa thằng lồn",
        "Chào Iem Nha <3"
    };

    private static final String[] textSanta = new String[]{
        "Mọi sự cố gắng đều sẽ được đền đáp xứng đáng.",};

    private static final String[] textQuyLao = new String[]{
        "Lá Là La...",
        "Ngày tươi đẹp nhất là ngày được may mắn nhìn thấy em",
        "Tình yêu không cần phải hoàn hảo, chỉ cần sự chân thật. ",
        "Tôi là một ngọn lửa cháy lên, nhưng sẽ không thể bị lụi tàn. "
    };

    public void npcChat(String text) {
        Message msg;
        try {
            msg = new Message(124);
            msg.writer().writeShort(tempId);
            msg.writer().writeUTF(text);
            for (Zone zone : map.zones) {
                Service.getInstance().sendMessAllPlayerInMap(zone, msg);
            }
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(Service.class, e);
        }
    }

    public boolean canOpenNpc(Player player) {
        if (this.tempId == ConstNpc.DAU_THAN) {
            if (player.zone.map.mapId == 21
                    || player.zone.map.mapId == 22
                    || player.zone.map.mapId == 5
                    || player.zone.map.mapId == 23) {
                return true;
            } else {
                Service.getInstance().hideWaitDialog(player);
                Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                return false;
            }
        }
        if (player.zone.map.mapId == this.mapId
                && Util.getDistance(this.cx, this.cy, player.location.x, player.location.y) <= 60) {
            player.iDMark.setNpcChose(this);
            return true;
        } else {
            Service.getInstance().hideWaitDialog(player);
            Service.getInstance().sendThongBao(player, "Không thể thực hiện khi đứng quá xa");
            return false;
        }
    }

}