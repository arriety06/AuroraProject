package com.girlkun.models.map.bdkb;

import com.girlkun.jdbc.daos.GodGK;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.list_boss.phoban.TrungUyXanhLoBdkb;
import com.girlkun.models.clan.Clan;
import com.girlkun.models.map.Zone;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.player.Player;
import com.girlkun.server.Client;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Khánh Đẹp Zoai
 */
@Data
public class BanDoKhoBau {

    public static final int N_PLAYER_CLAN = 0;
    public static final int AVAILABLE = 20; // số lượng bdkb trong server
    public static final int TIME_BAN_DO_KHO_BAU = 1_800_000;

    public static final int MAX_HP_MOB = 230_220_020;
    public static final int MIN_HP_MOB = 33000;

    public static final int MAX_DAME_MOB = 2_000_000;
    public static final int MIN_DAME_MOB = 3000;

    private int id;
    private List<Zone> zones;
    private Clan clan;

    private long lastTimeOpen;

    public int level;

    List<Integer> listMap = Arrays.asList(135, 138, 136, 137);
    private int currentIndexMap = -1;

    TrungUyXanhLoBdkb boss;

    public boolean timePickReward;
    public long lasttimepick;
    private long timeDone;
    public boolean timePickDragonBall;
    public long LastTimeChat;

    public BanDoKhoBau(int id) {
        this.id = id;
        this.zones = new ArrayList<>();
    }

    public Zone getMapById(int mapId) {
        for (Zone zone : this.zones) {
            if (zone.map.mapId == mapId) {
                return zone;
            }
        }
        return null;
    }

    public void openBDKB(Player player, int level) {
        this.lastTimeOpen = System.currentTimeMillis();
        this.timeDone = lastTimeOpen + TIME_BAN_DO_KHO_BAU;
        this.clan = player.clan;
        this.level = level;
        player.clan.banDoKhoBau = this;
        player.clan.banDoKhoBau_playerOpen = player.name;
        player.clan.banDoKhoBau_lastTimeOpen = this.lastTimeOpen;
        player.clan.banDoKhoBau_haveGone = false;
        player.bdkb_isJoinBdkb = true;
        player.bdkb_countPerDay++;
        player.bdkb_lastTimeJoin = System.currentTimeMillis();
        ChangeMapService.gI().goToDBKB(player);
        for (Player pl : player.clan.membersInGame) {
            if (pl == null || pl.zone == null) {
                continue;
            }
            ItemTimeService.gI().sendTextBanDoKhoBau(pl);
        }

    }

    public void init() {
        //Hồi sinh quái và thả boss
        for (Zone zone : this.zones) {
            if (zone.map.mapId == this.listMap.get(this.currentIndexMap)) {
                long newHpMob = (((MAX_HP_MOB - MIN_HP_MOB) + MIN_HP_MOB) / 110) * (this.level);
                long newDameMob = (((MAX_DAME_MOB - MIN_DAME_MOB) + MIN_DAME_MOB) / 110) * (this.level);
                for (Mob mob : zone.mobs) {
                    mob.point.dame = (int) (newDameMob);
                    mob.point.maxHp = (int) (newHpMob);
                    mob.hoiSinh();
                }
                int idBoss = (zone.map.mapId == 137 ? BossID.TRUNG_UY_XANH_LO_BDKB : -1);
                if (idBoss != -1) {
                    boss = (TrungUyXanhLoBdkb) BossManager.gI().createBossBdkb(idBoss, (int) (newDameMob * 4 > 2_000_000_000 ? 2_000_000_000 : newDameMob * 4), (int) (newHpMob * 5 > 2_000_000_000 ? 2_000_000_000 : newHpMob * 5), zone);
                }
            }
        }
    }

    public void update() {
        try {
            long now = System.currentTimeMillis();
            int secondsLeft = (int) ((timeDone - now) / 1000);
            if (secondsLeft < 0) {
                secondsLeft = 0;
            }
            if (timePickReward) {
                timeDone = System.currentTimeMillis() + 30000;
                timePickReward = false;
            }
            if (timeDone - now <= 30_000 && timeDone - now > 0) {
                for (Zone zone : zones) {
                    if (MapService.gI().isMapBanDoKhoBau(zone.map.mapId)) {
                        List<Player> playersMap = zone.getPlayers();
                        if (!playersMap.isEmpty()) {
                            for (int i = 0; i < playersMap.size(); i++) {
                                Player player = playersMap.get(i);
                                if (player != null && player.clan != null && player.clan.banDoKhoBau != null) {
                                    if (Util.canDoWithTime(LastTimeChat, 6000)) {
                                        LastTimeChat = System.currentTimeMillis();
                                        Service.gI().sendThongBao(player, "Hang kho báu sẽ sập sau " + secondsLeft + " giây nữa, gét gô gét gô!");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (now > timeDone) {
                for (Zone zone : zones) {
                    if (MapService.gI().isMapBanDoKhoBau(zone.map.mapId)) {
                        List<Player> playersMap = zone.getPlayers();
                        if (!playersMap.isEmpty()) {
                            for (int i = 0; i < playersMap.size(); i++) {
                                Player player = playersMap.get(i);
                                if (player != null && player.clan != null && player.clan.banDoKhoBau != null) {
                                    ketthucbdkb(player);
                                }
                            }
                        }
                    }
                }
                if (this.clan != null && this.clan.banDoKhoBau != null) {
                    this.clan.banDoKhoBau.dispose();
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
            System.out.println("Lỗi update ở class BĐKB");
        }
    }

    private void ketthucbdkb(Player player) {
        if (player == null && player.zone == null && player.clan == null && !player.isPl()) {
            return;
        }
        if (MapService.gI().isMapBanDoKhoBau(player.zone.map.mapId)) {
            Service.getInstance().sendThongBao(player, "Trận đại chiến đã kết thúc, tàu vận chuyển sẽ đưa bạn về nhà");
            ItemTimeService.gI().sendTextTime(player, (byte) 0, "Vdin Đẹp Troai", 0);
            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
        }
    }

    public void dispose() {
        try {
            if (boss != null) {
                boss.changeStatus(BossStatus.LEAVE_MAP);
                boss = null;
            }
            if (this.clan != null && this.clan.banDoKhoBau != null) {
                this.clan.banDoKhoBau = null;
            }
            if (this.clan != null) {
                this.clan = null;
            }
            timePickReward = false;
            currentIndexMap = -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
