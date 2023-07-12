package com.girlkun.models.boss;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.list_boss.AnTrom;
import com.girlkun.models.boss.list_boss.BLACK.*;
import com.girlkun.models.boss.list_boss.NgucTu.CoolerGold;
import com.girlkun.models.boss.list_boss.Doraemon.Doraemon;
import com.girlkun.models.boss.list_boss.FideBack.Kingcold;
import com.girlkun.models.boss.list_boss.Mabu;
import com.girlkun.models.boss.list_boss.NgucTu.Cumber;
import com.girlkun.models.boss.list_boss.cell.Xencon;
import com.girlkun.models.boss.list_boss.ginyu.TDST;
import com.girlkun.models.boss.list_boss.android.*;
import com.girlkun.models.boss.list_boss.cell.SieuBoHung;
import com.girlkun.models.boss.list_boss.cell.XenBoHung;
import com.girlkun.models.boss.list_boss.doanh_trai.TrungUyTrang;
import com.girlkun.models.boss.list_boss.Doraemon.Nobita;
import com.girlkun.models.boss.list_boss.Doraemon.Xeko;
import com.girlkun.models.boss.list_boss.Doraemon.Xuka;
import com.girlkun.models.boss.list_boss.FideBack.FideRobot;
import com.girlkun.models.boss.list_boss.NgucTu.SongokuTaAc;
import com.girlkun.models.boss.list_boss.fide.Fide;
import com.girlkun.models.boss.list_boss.Doraemon.Chaien;
import com.girlkun.models.boss.list_boss.Mabu12h.BuiBui;
import com.girlkun.models.boss.list_boss.Mabu12h.MabuBoss;
import com.girlkun.models.boss.list_boss.Mabu12h.BuiBui2;
import com.girlkun.models.boss.list_boss.Mabu12h.Drabura;
import com.girlkun.models.boss.list_boss.Mabu12h.Drabura2;
import com.girlkun.models.boss.list_boss.Mabu12h.Yacon;
import com.girlkun.models.boss.list_boss.SUPER.Brolythuong;
import com.girlkun.models.boss.list_boss.SUPER.Superbroly;
import com.girlkun.models.boss.list_boss.SoiHecQuyn;
import com.girlkun.models.boss.list_boss.TDST_NM.*;
import com.girlkun.models.boss.list_boss.doanh_trai.NinjaAoTim;
import com.girlkun.models.boss.list_boss.doanh_trai.RobotVeSi;
import com.girlkun.models.boss.list_boss.doanh_trai.TrungUyThep;
import com.girlkun.models.boss.list_boss.doanh_trai.TrungUyXanhLo;
import com.girlkun.models.boss.list_boss.nappa.*;
import com.girlkun.models.boss.list_boss.phoban.TrungUyXanhLoBdkb;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.network.io.Message;
import com.girlkun.server.ServerManager;
import com.girlkun.services.ItemMapService;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;
import com.girlkun.models.boss.BossID;
import com.girlkun.utils.Logger;
import static com.girlkun.utils.Logger.PURPLE;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class BossManager implements Runnable {

    private static BossManager I;
    public static final byte ratioReward = 2;

    public static BossManager gI() {
        if (BossManager.I == null) {
            BossManager.I = new BossManager();
        }
        return BossManager.I;
    }

    private BossManager() {
        this.bosses = new ArrayList<>();
    }

    private boolean loadedBoss;
    private final List<Boss> bosses;

    public void addBoss(Boss boss) {
        this.bosses.add(boss);
    }

    public void removeBoss(Boss boss) {
        this.bosses.remove(boss);
    }

    public void loadBoss() {
        if (this.loadedBoss) {
            return;
        }
        try {
            this.createBoss(BossID.TDST);
            this.createBoss(BossID.PIC);
            this.createBoss(BossID.POC);
            this.createBoss(BossID.KING_KONG);
            this.createBoss(BossID.SONGOKU_TA_AC);
            this.createBoss(BossID.CUMBER);
            this.createBoss(BossID.COOLER_GOLD);
            this.createBoss(BossID.XEN_BO_HUNG);
            this.createBoss(BossID.SIEU_BO_HUNG);
            this.createBoss(BossID.XEN_CON_1);
            this.createBoss(BossID.XEN_CON_1);
            this.createBoss(BossID.XEN_CON_1);
            this.createBoss(BossID.XEN_CON_1);
            this.createBoss(BossID.DORAEMON);
            this.createBoss(BossID.NOBITA);
            this.createBoss(BossID.XUKA);
            this.createBoss(BossID.CHAIEN);
            this.createBoss(BossID.XEKO);
            this.createBoss(BossID.BLACK);
            this.createBoss(BossID.ZAMASZIN);
            this.createBoss(BossID.BLACK2);
            this.createBoss(BossID.ZAMASMAX);
            this.createBoss(BossID.BLACK);
            this.createBoss(BossID.BLACK3);
            this.createBoss(BossID.KUKU);
            this.createBoss(BossID.MAP_DAU_DINH);
            this.createBoss(BossID.RAMBO);
            this.createBoss(BossID.FIDE);
            this.createBoss(BossID.DR_KORE);
            this.createBoss(BossID.ANDROID_14);
            this.createBoss(BossID.MABU);
            for (int i = 0; i < 20; i++) {
                this.createBoss(BossID.SOI_HEC_QUYN_BEO + i);
            }
            this.createBoss(BossID.SO_4_NM);
            this.createBoss(BossID.SO_3_NM);
            this.createBoss(BossID.SO_2_NM);
            this.createBoss(BossID.SO_1_NM);
            this.createBoss(BossID.TIEU_DOI_TRUONG_NM);
            for (int i = 0; i < 20; i++) {
                this.createBoss(BossID.AN_TROM + i);
            }
            for (int i = 1; i <= 20; i++) {
                this.createBoss(BossID.BROLY_THUONG + i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.loadedBoss = true;
        new Thread(BossManager.I, "Update boss").start();
    }

    public Boss createBossBdkb(int bossID, int dame, int hp, Zone zone) {
        try {
            switch (bossID) {
                case BossID.TRUNG_UY_XANH_LO_BDKB:
                    return new TrungUyXanhLoBdkb(dame, hp, zone);
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boss createBoss(int bossID, BossData bossdata) {
        try {
            if (bossID <= BossID.SUPER_BROLY + 20 && bossID >= BossID.SUPER_BROLY) {
                return new Superbroly(bossID, bossdata);
            }
            if (bossID <= BossID.BROLY_THUONG + 20 && bossID >= BossID.BROLY_THUONG) {
                return new Brolythuong(bossID, bossdata);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Boss createBossDoanhTrai(int bossID, int dame, int hp, Zone zone) {
        System.err.println("create boss donh trai");
        try {
            switch (bossID) {
                case BossID.TRUNG_UY_TRANG:
                    return new TrungUyTrang(dame, hp, zone);
                case BossID.TRUNG_UY_XANH_LO:
                    return new TrungUyXanhLo(dame, hp, zone);
                case BossID.TRUNG_UY_THEP:
                    return new TrungUyThep(dame, hp, zone);
                case BossID.NINJA_AO_TIM:
                    return new NinjaAoTim(dame, hp, zone);
                case BossID.ROBOT_VE_SI1:
                case BossID.ROBOT_VE_SI2:
                case BossID.ROBOT_VE_SI3:
                case BossID.ROBOT_VE_SI4:
                    return new RobotVeSi(bossID, dame, hp, zone);
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boss createBoss(int bossID) {
//        Logger.log(Logger.PURPLE, "create boss " + bossID + "\n");
        try {
            int[] idmapbroly = new int[]{5, 6, 10, 11, 12, 13, 19, 20, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
            int indexmapxh = Util.nextInt(idmapbroly.length);
            if (bossID <= BossID.BROLY_THUONG + 20 && bossID >= BossID.BROLY_THUONG) {
                BossData brolythuong = new BossData(
                        "Broly " + (bossID - BossID.BROLY_THUONG),
                        ConstPlayer.XAYDA,
                        new short[]{291, 292, 293, -1, -1, -1},
                        100,
                        new int[]{500},
                        new int[]{idmapbroly[indexmapxh]},
                        new int[][]{
                            {Skill.KAMEJOKO, 7, 5000},
                            {Skill.ANTOMIC, 7, 5000},
                            {Skill.MASENKO, 7, 5000},
                            {Skill.DEMON, 7, 1000},
                            {Skill.GALICK, 7, 1000},
                            {Skill.DRAGON, 7, 1000},
                            {Skill.TAI_TAO_NANG_LUONG, 7, 20000}},
                        new String[]{"|-2|Làm sao mà đỡ được!"}, //text chat 1
                        new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
                        new String[]{"|-1|Chết mọe m chưa con!",
                            "|-1|Tobe continue.."}, //text chat 3
                        604_800_000
                );
                return new Brolythuong(bossID, brolythuong);
            }

            switch (bossID) {
                case BossID.KUKU:
                    return new Kuku();
                case BossID.MAP_DAU_DINH:
                    return new MapDauDinh();
                case BossID.RAMBO:
                    return new Rambo();
                case BossID.DRABURA:
                    return new Drabura();
                case BossID.DRABURA_2:
                    return new Drabura2();
                case BossID.BUI_BUI:
                    return new BuiBui();
                case BossID.BUI_BUI_2:
                    return new BuiBui2();
                case BossID.YA_CON:
                    return new Yacon();
                case BossID.MABU_12H:
                    return new MabuBoss();
                case BossID.FIDE:
                    return new Fide();
                case BossID.DR_KORE:
                    return new DrKore();
                case BossID.ANDROID_19:
                    return new Android19();
                case BossID.ANDROID_13:
                    return new Android13();
                case BossID.ANDROID_14:
                    return new Android14();
                case BossID.ANDROID_15:
                    return new Android15();
                case BossID.PIC:
                    return new Pic();
                case BossID.POC:
                    return new Poc();
                case BossID.KING_KONG:
                    return new KingKong();
                case BossID.XEN_BO_HUNG:
                    return new XenBoHung();
                case BossID.SIEU_BO_HUNG:
                    return new SieuBoHung();
                case BossID.XUKA:
                    return new Xuka();
                case BossID.NOBITA:
                    return new Nobita();
                case BossID.XEKO:
                    return new Xeko();
                case BossID.CHAIEN:
                    return new Chaien();
                case BossID.DORAEMON:
                    return new Doraemon();
                case BossID.VUA_COLD:
                    return new Kingcold();
                case BossID.FIDE_ROBOT:
                    return new FideRobot();
                case BossID.ZAMASMAX:
                    return new ZamasMax();
                case BossID.ZAMASZIN:
                    return new ZamasKaio();
                case BossID.BLACK2:
                    return new SuperBlack2();
                case BossID.BLACK1:
                    return new BlackGokuTl();
                case BossID.BLACK:
                    return new Black();
                case BossID.BLACK3:
                    return new BlackGokuBase();
                case BossID.XEN_CON_1:
                    return new Xencon();
                case BossID.MABU:
                    return new Mabu();
                case BossID.TDST:
                    return new TDST();
                case BossID.COOLER_GOLD:
                    return new CoolerGold();
                case BossID.CUMBER:
                    return new Cumber();
                case BossID.SONGOKU_TA_AC:
                    return new SongokuTaAc();
                case BossID.SO_4_NM:
                    return new So4Nm();
                case BossID.SO_3_NM:
                    return new So3Nm();
                case BossID.SO_2_NM:
                    return new So2Nm();
                case BossID.SO_1_NM:
                    return new So1Nm();
                case BossID.TIEU_DOI_TRUONG_NM:
                    return new TDT_NM();
                default:
                    if (BossID.isBossSoiHecQuyn(bossID)) {
                        return new SoiHecQuyn(bossID);
                    }
                    if (BossID.isAnTrom(bossID)) {
                        return new AnTrom(bossID);
                    }
                    return null;
            }
        } catch (Exception e) {
            Logger.log(Logger.PURPLE, "Lỗi Boss: " + bossID);
            e.printStackTrace();
            return null;
        }
    }

    public boolean existBossOnPlayer(Player player) {
        return player.zone.getBosses().size() > 0;
    }

    public void FindBoss(Player player, int id) {
//        System.out.println("id: " + id);
        Boss boss = BossManager.gI().getBossById(id);
        if (boss != null && !boss.isDie()) {
            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
            if (z.getNumOfPlayers() < z.maxPlayer) {
                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                Service.getInstance().sendMoney(player);
            } else {
                Service.getInstance().sendThongBao(player, "Khu vực đang full.");
            }
        } else {
            Service.gI().sendThongBao(player, "Chê cụ òi");
        }
    }

    public void showListBoss(Player player) {
        if (!player.isAdmin()) {
            return;
        }
        Message msg;
        try {
            msg = new Message(-96);
            msg.writer().writeByte(0);
            msg.writer().writeUTF("Boss");
            long aliveBossCount = bosses.stream()
                    .filter(boss -> !MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0]) && !MapService.gI().isMapDoanhTrai(boss.data[0].getMapJoin()[0]) && !MapService.gI().isMapBanDoKhoBau(boss.data[0].getMapJoin()[0]) && !MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0]))
                    .filter(boss -> boss.zone != null)
                    .filter(boss -> !BossID.isAnTrom((int) boss.id) && !BossID.isBossSoiHecQuyn((int) boss.id) && !BossID.isBrolyOrSuper((int) boss.id))
                    .count();
            msg.writer().writeByte((int) aliveBossCount);
            for (int i = 0; i < bosses.size(); i++) {
                Boss boss = this.bosses.get(i);
                if (MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapBanDoKhoBau(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapDoanhTrai(boss.data[0].getMapJoin()[0])) {
                    continue;
                }
                if (BossID.isAnTrom((int) boss.id) || BossID.isBossSoiHecQuyn((int) boss.id) || BossID.isBrolyOrSuper((int) boss.id)) {
                    continue;
                }
                if (boss.zone == null) {
                    continue;
                }
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeShort(boss.data[0].getOutfit()[0]);
                if (player.getSession().getVersion() > 214) {
                    msg.writer().writeShort(-1);
                }
                msg.writer().writeShort(boss.data[0].getOutfit()[1]);
                msg.writer().writeShort(boss.data[0].getOutfit()[2]);
                msg.writer().writeUTF(boss.data[0].getName());
                msg.writer().writeUTF("Sống");
                msg.writer().writeUTF(boss.zone.map.mapName + "(" + boss.zone.map.mapId + ") khu " + boss.zone.zoneId + "");
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boss getBossById(int bossId) {
        return BossManager.gI().bosses.stream().filter(boss -> boss.id == bossId && !boss.isDie()).findFirst().orElse(null);
    }

    @Override
    public void run() {
        while (ServerManager.isRunning) {
            try {
                long st = System.currentTimeMillis();
                List<Boss> bossesCopy = new ArrayList<>(this.bosses);
                for (Boss boss : bossesCopy) {
                    boss.update();
                }
                long timeSleep = Math.abs(150 - (System.currentTimeMillis() - st));
//                Thread.sleep(timeSleep);
                Thread.sleep(300);
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
