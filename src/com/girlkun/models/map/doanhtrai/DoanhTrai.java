package com.girlkun.models.map.doanhtrai;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.clan.Clan;
import com.girlkun.models.mob.Mob;
import com.girlkun.services.ItemTimeService;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;
import java.util.Arrays;

/**
 *
 * - Stole By Arriety
 */
@Data
public class DoanhTrai {

    //bang hội đủ số người mới đc mở
    public static final int N_PLAYER_CLAN = 0;
    //số người đứng cùng khu
    public static final int N_PLAYER_MAP = 0;
    public static final int AVAILABLE = 20; // số lượng doanh trại trong server
    public static final int TIME_DOANH_TRAI = 1_800_000;

    private int id;
    private List<Zone> zones;
    private Clan clan;

    private long lastTimeOpen;
    private long timeDone;
    public boolean timePickDragonBall;

    List<Integer> listMap = Arrays.asList(53, 58, 59, 60, 61, 62, 55, 56, 54, 57);
    private int currentIndexMap = -1;
    private List<Boss> bossDoanhTrai;

    public DoanhTrai(int id) {
        this.id = id;
        this.zones = new ArrayList<>();
        this.bossDoanhTrai = new ArrayList<>();
    }

    public Zone getMapById(int mapId) {
        for (Zone zone : this.zones) {
            if (zone.map.mapId == mapId) {
                return zone;
            }
        }
        return null;
    }

    public void openDoanhTrai(Player player) {
        this.lastTimeOpen = System.currentTimeMillis();
        this.timeDone = lastTimeOpen + TIME_DOANH_TRAI;
        this.clan = player.clan;
        player.clan.doanhTrai = this;
        player.clan.doanhTrai_playerOpen = player.name;
        player.clan.doanhTrai_lastTimeOpen = this.lastTimeOpen;
        //Khởi tạo quái, boss
//        this.init();
        //Đưa thành viên vào doanh trại
        for (Player pl : player.clan.membersInGame) {
            if (pl == null || pl.zone == null) {
                continue;
            }

            ItemTimeService.gI().sendTextDoanhTrai(pl);
            if (player.zone.equals(pl.zone)) {
                ChangeMapService.gI().changeMapInYard(pl, 53, -1, 60);
            }

        }
    }

    public void init() {
        long totalDame = 0;
        long totalHp = 0;
        if (this.clan != null && !this.clan.membersInGame.isEmpty()) {
            List<Player> playersIngame = this.clan.membersInGame;
            if (!playersIngame.isEmpty()) {
                for (int i = 0; i < playersIngame.size(); i++) {
                    Player pl = playersIngame.get(i);
                    if (pl != null && pl.nPoint != null) {
                        totalDame += pl.nPoint.dame;
                        totalHp += pl.nPoint.hpMax;
                    }
                }
            }
        }

        //Hồi sinh quái và thả boss
        if (!this.zones.isEmpty()) {
            List<Zone> zoness = this.zones;
            for (int i = 0; i < zoness.size(); i++) {
                Zone zone = zoness.get(i);
                if (zone != null && zone.map.mapId == this.listMap.get(this.currentIndexMap)) {
                    long newDameMob = (totalHp / 20);
                    long newHpMob = totalDame * 3;//(totalDame * 20);
                    if (!zone.mobs.isEmpty()) {
                        List<Mob> mobss = zone.mobs;
                        if (!mobss.isEmpty()) {
                            for (int j = 0; j < mobss.size(); j++) {
                                Mob mob = mobss.get(j);
                                if (mob != null && mob.point != null) {
                                    mob.point.dame = (int) (newDameMob > 2_000_000_000 ? 2_000_000_000 : newDameMob);
                                    mob.point.maxHp = (int) (newHpMob > 2_000_000_000 ? 2_000_000_000 : newHpMob);
                                    mob.hoiSinh();
                                }
                            }
                        }
                    }
                    int idBoss = this.getIdBoss(zone.map.mapId);
                    if (idBoss != -1) {
                        if (idBoss != 1111111111) {
                            bossDoanhTrai.add(BossManager.gI().createBossDoanhTrai(idBoss, (int) (newDameMob * 3 > 2_000_000_000 ? 2_000_000_000 : newDameMob * 3), (int) (newHpMob * 3 > 2_000_000_000 ? 2_000_000_000 : newHpMob * 3), zone));
                        } else { // là vệ sĩ
                            for (Byte k = 0; k < 4; k++) {
                                bossDoanhTrai.add(BossManager.gI().createBossDoanhTrai(BossID.ROBOT_VE_SI1 + k, (int) (newDameMob * 4 > 2_000_000_000 ? 2_000_000_000 : newDameMob * 4), (int) (newHpMob * 4 > 2_000_000_000 ? 2_000_000_000 : newHpMob * 4), zone));
                            }
                        }
                    }
                }
            }
        }

    }

    private int getIdBoss(int mapId) {
        switch (mapId) {
            case 59:
                return BossID.TRUNG_UY_TRANG;
            case 62:
                return BossID.TRUNG_UY_XANH_LO;
            case 55:
                return BossID.TRUNG_UY_THEP;
            case 54:
                return BossID.NINJA_AO_TIM;
            case 57:
                return 1111111111; // check vệ sĩ
            default:
                return -1;
        }
    }

    public void DropNgocRong() {
        for (Zone zone : zones) {
            ItemMap itemMap = null;
            switch (zone.map.mapId) {
                case 53:
                    itemMap = new ItemMap(zone, Util.nextInt(16, 20), 1, 917, 384, -1);
                    itemMap.isDoanhTraiBall = true;
                    Service.gI().dropItemMap(zone, itemMap);
                    break;
                case 58:
                    itemMap = new ItemMap(zone, Util.nextInt(16, 20), 1, 658, 336, -1);
                    itemMap.isDoanhTraiBall = true;
                    Service.gI().dropItemMap(zone, itemMap);
                    break;
                case 59:
                    itemMap = new ItemMap(zone, Util.nextInt(16, 20), 1, 675, 240, -1);
                    itemMap.isDoanhTraiBall = true;
                    Service.gI().dropItemMap(zone, itemMap);
                    break;
                case 60:
                    itemMap = new ItemMap(zone, Util.nextInt(16, 20), 1, Util.nextInt(725, 1241), 384, -1);
                    itemMap.isDoanhTraiBall = true;
                    Service.gI().dropItemMap(zone, itemMap);
                    break;
                case 61:
                    itemMap = new ItemMap(zone, Util.nextInt(16, 20), 1, 789, 264, -1);
                    itemMap.isDoanhTraiBall = true;
                    Service.gI().dropItemMap(zone, itemMap);
                    break;
                case 62:
                    itemMap = new ItemMap(zone, Util.nextInt(16, 20), 1, Util.nextInt(197, 1294), 384, -1);
                    itemMap.isDoanhTraiBall = true;
                    Service.gI().dropItemMap(zone, itemMap);
                    break;
                case 55:
                    itemMap = new ItemMap(zone, Util.nextInt(16, 20), 1, 422, 288, -1);
                    itemMap.isDoanhTraiBall = true;
                    Service.gI().dropItemMap(zone, itemMap);
                    break;
                case 56:
                    itemMap = new ItemMap(zone, Util.nextInt(16, 20), 1, 789, 312, -1);
                    itemMap.isDoanhTraiBall = true;
                    Service.gI().dropItemMap(zone, itemMap);
                    break;
                case 54:
                    itemMap = new ItemMap(zone, Util.nextInt(16, 20), 1, 211, 1228, -1);
                    itemMap.isDoanhTraiBall = true;
                    Service.gI().dropItemMap(zone, itemMap);
                    break;
            }
        }

    }

    public void update() {
        try {
            long now = System.currentTimeMillis();
            if (timePickDragonBall) {
                timeDone = System.currentTimeMillis() + 300000;
                timePickDragonBall = false;
            }
            if (now > timeDone) {
                for (Zone zone : zones) {
                    if (MapService.gI().isMapDoanhTrai(zone.map.mapId)) {
                        List<Player> playersMap = zone.getPlayers();
                        if (!playersMap.isEmpty()) {
                            for (int i = 0; i < playersMap.size(); i++) {
                                Player player = playersMap.get(i);
                                if (player != null && player.clan != null && player.clan.doanhTrai != null) {
                                    ketthucDoanhTrai(player);
                                }
                            }
                        }
                    }
                }
                if (this.clan != null && this.clan.doanhTrai != null) {
                    this.clan.doanhTrai.dispose();
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
            System.out.println("Lỗi update ở class Doanh trại");
        }
    }

    private void ketthucDoanhTrai(Player player) {
        if (player == null && player.zone == null && player.clan == null && !player.isPl()) {
            return;
        }
        if (MapService.gI().isMapDoanhTrai(player.zone.map.mapId)) {
            Service.getInstance().sendThongBao(player, "Trận đại chiến đã kết thúc, tàu vận chuyển sẽ đưa bạn về nhà");
            ItemTimeService.gI().sendTextTime(player, (byte) 0, "Aurura", 0);
            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
        }
    }

    public void dispose() {
        try {
            if (!bossDoanhTrai.isEmpty()) {
                List<Boss> bossMap = bossDoanhTrai;
                if (!bossMap.isEmpty()) {
                    for (int i = 0; i < bossMap.size(); i++) {
                        Boss b = bossMap.get(i);
                        if (b != null) {
                            b.changeStatus(BossStatus.LEAVE_MAP);
                            b = null;
                        }
                    }
                }
            }
            if (this.clan != null && this.clan.doanhTrai != null) {
                this.clan.doanhTrai = null;
            }
            if (this.clan != null) {
                this.clan = null;
            }
            if (this.bossDoanhTrai != null) {
                this.bossDoanhTrai.clear();
            }
            timePickDragonBall = false;
            currentIndexMap = -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - Stole By Arriety
 */
