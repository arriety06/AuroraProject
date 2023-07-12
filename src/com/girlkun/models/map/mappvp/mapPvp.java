package com.girlkun.models.map.mappvp;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.player.Player;
import com.girlkun.services.MapService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.TimeUtil;
import java.util.List;

/**
 *
 * @author by Péo Đì Zai
 */
public class mapPvp {

    public static final byte HOUR_OPEN_MAP_PVP = 8;
    public static final byte MIN_OPEN_MAP_PVP = 0;
    public static final byte SECOND_OPEN_MAP_PVP = 0;

    public static final byte HOUR_CLOSE_MAP_PVP = 9;
    public static final byte MIN_CLOSE_MAP_PVP = 0;
    public static final byte SECOND_CLOSE_MAP_PVP = 0;

//    public static final int AVAILABLE = 7;
    private static mapPvp i;

    public static long TIME_OPEN_PVP;
    public static long TIME_CLOSE_PVP;

    private int day = -1;

    public static mapPvp gI() {
        if (i == null) {
            i = new mapPvp();
        }
        i.setTimeJoinmappvp();
        return i;
    }

    public void setTimeJoinmappvp() {
        if (i.day == -1 || i.day != TimeUtil.getCurrDay()) {
            i.day = TimeUtil.getCurrDay();
            try {
                TIME_OPEN_PVP = TimeUtil.getTime(TimeUtil.getTimeNow("dd/MM/yyyy") + " " + HOUR_OPEN_MAP_PVP + ":" + MIN_OPEN_MAP_PVP + ":" + SECOND_OPEN_MAP_PVP, "dd/MM/yyyy HH:mm:ss");
                TIME_CLOSE_PVP = TimeUtil.getTime(TimeUtil.getTimeNow("dd/MM/yyyy") + " " + HOUR_CLOSE_MAP_PVP + ":" + MIN_CLOSE_MAP_PVP + ":" + SECOND_CLOSE_MAP_PVP, "dd/MM/yyyy HH:mm:ss");
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private void kickOutOfmappvp(Player player) {
        if (MapService.gI().ismappvphonchien(player.zone.map.mapId)) {
            if (player != null && player.zone != null && !player.isNewPet && player.isPl()) {
                Service.getInstance().sendThongBao(player, "Hết thời gian rồi, tàu vận chuyển sẽ đưa bạn về nhà");
                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
            }
        }
    }

    private void ketthucmappvp(Player player) {
        player.zone.finishmappvp = true;
        List<Player> playersMap = player.zone.getPlayers();
        for (int i = playersMap.size() - 1; i >= 0; i--) {
            Player pl = playersMap.get(i);
            kickOutOfmappvp(pl);
        }
    }

    public void joinmappvp(Player player) {
        boolean changed = false;
        if (player.clan != null) {
            List<Player> players = player.zone.getPlayers();
            for (Player pl : players) {
                if (pl.clan != null && !player.equals(pl) && player.clan.equals(pl.clan) && !player.isBoss) {
                    Service.getInstance().changeFlag(player, 8);
                    changed = true;
                    break;
                }
            }
        }
        if (!changed && !player.isBoss) {
            Service.getInstance().changeFlag(player, 8);
        }
    }

    public void update(Player player) {
        if (player.isPl() && player != null && player.zone != null && player.zone.map != null) {
            try {
                long now = System.currentTimeMillis();
                if (!(now > TIME_OPEN_PVP && now < TIME_CLOSE_PVP) && MapService.gI().ismappvphonchien(player.zone.map.mapId)) {
                    ketthucmappvp(player);
                }
                if (MapService.gI().ismappvphonchien(player.zone.map.mapId) && (now > TIME_OPEN_PVP && now < TIME_CLOSE_PVP)) {
                    if (player.typePk != ConstPlayer.PK_ALL && !player.isDie()) {
                        PlayerService.gI().changeAndSendTypePK(player, ConstPlayer.PK_ALL);
                    }
                    if (player.inventory.gold < 500_000_000) {
                        Service.getInstance().sendThongBao(player, "Dưới 500tr vàng rồi, đừng nghiện nữa!!");
                        ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
                    }
                }
            } catch (Exception ex) {
                System.err.println("Lỗi update ở class MapPvp");
                ex.printStackTrace();
            }
        }
    }
}
