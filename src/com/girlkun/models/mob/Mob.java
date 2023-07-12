package com.girlkun.models.mob;

import com.girlkun.consts.ConstMap;
import com.girlkun.consts.ConstMob;
import com.girlkun.consts.ConstTask;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;

import java.util.List;

import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Location;
import com.girlkun.models.player.Pet;
import com.girlkun.models.player.Player;
import com.girlkun.models.reward.ItemMobReward;
import com.girlkun.models.reward.MobReward;
import com.girlkun.models.skill.PlayerSkill;
import com.girlkun.models.skill.Skill;
import com.girlkun.network.io.Message;
import com.girlkun.server.Maintenance;
import com.girlkun.server.Manager;
import com.girlkun.server.ServerManager;
import com.girlkun.services.*;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;
import java.io.IOException;

import java.util.ArrayList;

public class Mob {

    public int id;
    public byte type;
    public Zone zone;
    public int tempId;
    public String name;
    public byte level;

    public MobPoint point;
    public MobEffectSkill effectSkill;
    public Location location;

    public byte pDame;
    public int pTiemNang;
    private long maxTiemNang;

    public long lastTimeDie;
    public int lvMob = 0;
    public int status = 5;
    public int sys = 0;

    public Mob(Mob mob) {
        this.point = new MobPoint(this);
        this.effectSkill = new MobEffectSkill(this);
        this.location = new Location();
        this.id = mob.id;
        type = mob.type;
        this.tempId = mob.tempId;
        this.level = mob.level;
        this.point.setHpFull(mob.point.getHpFull());
        this.point.sethp(this.point.getHpFull());
        this.location.x = mob.location.x;
        this.location.y = mob.location.y;
        this.pDame = mob.pDame;
        this.pTiemNang = mob.pTiemNang;
        this.setTiemNang();
    }

    public Mob() {
        this.point = new MobPoint(this);
        this.effectSkill = new MobEffectSkill(this);
        this.location = new Location();
    }

    public boolean isSieuQuai() {
        return this.lvMob > 0;
    }

    public void setTiemNang() {
        this.maxTiemNang = (long) this.point.getHpFull() * (this.pTiemNang + Util.nextInt(-2, 2)) / 100;
    }

    private long lastTimeAttackPlayer;

    public boolean isDie() {
        return this.point.gethp() <= 0;
    }

    private long totalDameHiru = 0;

    public long processingDameInjuredHiru(long dame) {
        totalDameHiru += dame;
        if (totalDameHiru > 3_000_000) {
            totalDameHiru = 0;
            if (!this.zone.getPlayers().isEmpty()) {
                if (Util.nextInt(0, 1000) < 25) {
                    ItemMap itemMap = new ItemMap(this.zone, 568, 1,
                            this.location.x + Util.nextInt(-50, 50), this.zone.map.yPhysicInTop(this.location.x, this.location.y), this.zone.getPlayers().get(Util.nextInt(0, this.zone.getPlayers().size() - 1)).id);
                    itemMap.options.add(new Item.ItemOption(30, 0));
                    itemMap.isHirudegarn = true;
                    Service.getInstance().dropItemMap(this.zone, itemMap);
                }
                for (byte i = 0; i < Util.nextInt(5, 10); i++) {
                    Service.getInstance().dropItemMap(this.zone, new ItemMap(zone, 190, Util.nextInt(10000, 33333),
                            this.location.x + Util.nextInt(-50, 50),
                            this.zone.map.yPhysicInTop(this.location.x, this.location.y),
                            -1));
                }

            }
        }

        return dame;
    }

    public synchronized void injured(Player plAtt, int damage, boolean dieWhenHpFull) {
        if (!this.isDie()) {
            if (this.tempId == 70) {
                damage = (int) processingDameInjuredHiru(damage);
            }
            if (damage >= this.point.hp) {
                damage = this.point.hp;
            }

//            if (this.zone.map.mapId == 112) {
//                plAtt.NguHanhSonPoint++;
//            }
            if (!dieWhenHpFull) {
                if (this.point.hp == this.point.maxHp && damage >= this.point.hp) {
                    damage = this.point.hp - 1;
                }
                if (this.tempId == 0 && damage > 10) {
                    damage = 10;
                }
            }
            if (plAtt != null
                    && plAtt.playerSkill != null
                    && plAtt.playerSkill.skillSelect.template != null
                    && plAtt.playerSkill.skillSelect != null) {
                switch (plAtt.playerSkill.skillSelect.template.id) {
                    case Skill.KAMEJOKO:
                    case Skill.MASENKO:
                    case Skill.ANTOMIC:
                        if (plAtt.nPoint.multicationChuong > 0 && Util.canDoWithTime(plAtt.nPoint.lastTimeMultiChuong, PlayerSkill.TIME_MUTIL_CHUONG)) {
                            damage *= plAtt.nPoint.multicationChuong;
                            plAtt.nPoint.lastTimeMultiChuong = System.currentTimeMillis();
                        }

                }
            }
            this.point.hp -= damage;

            if (this.isDie()) {
                this.lvMob = 0;
                this.status = 0;
                this.sendMobDieAffterAttacked(plAtt, damage);
                TaskService.gI().checkDoneTaskKillMob(plAtt, this);
                TaskService.gI().checkDoneSideTaskKillMob(plAtt, this);
                if (this.isSieuQuai()) {
                    plAtt.achievement.plusCount(12);
                }
                this.lastTimeDie = System.currentTimeMillis();
                if (this.id == 13) {
                    this.zone.isbulon13Alive = false;
                }
                if (this.id == 14) {
                    this.zone.isbulon14Alive = false;
                }
            } else {
                boolean crit = false;
                if (plAtt != null && plAtt.nPoint != null) {
                    crit = plAtt.nPoint.isCrit;
                }
                this.sendMobStillAliveAffterAttacked(damage, crit);
            }
            if (plAtt != null) {
                Service.gI().addSMTN(plAtt, (byte) 2, getTiemNangForPlayer(plAtt, damage), true);

            }
        }
    }

    public long getTiemNangForPlayer(Player pl, long dame) {
        int levelPlayer = Service.getInstance().getCurrLevel(pl);
        int n = levelPlayer - this.level;
        long pDameHit = dame * 100 / point.getHpFull();
        long tiemNang = pDameHit * maxTiemNang / 100;
        if (pl != null && pl.nPoint != null) {
            if (tiemNang <= 0) {
                tiemNang = 1;
            }
            if (n >= 0) {
                for (int i = 0; i < n; i++) {
                    long sub = tiemNang * 15 / 100;
                    if (sub <= 0) {
                        sub = 1;
                    }
                    tiemNang -= sub;
                }
            } else {
                for (int i = 0; i < -n; i++) {
                    long add = tiemNang * 5 / 100;
                    if (add <= 0) {
                        add = 1;
                    }
                    tiemNang += add;
                }
            }
            if (tiemNang <= 0) {
                tiemNang = 1;
            }
            tiemNang = (int) pl.nPoint.calSucManhTiemNang(tiemNang);
        }
        return tiemNang;
    }

    public void update() {
        try {
            if (this.zone != null) {
                if (this.isDie()) {
                    switch (zone.map.type) {
                        case ConstMap.MAP_DOANH_TRAI:
                            if (this.zone.isTrungUyTrangAlive && this.tempId == 22 && this.zone.map.mapId == 59) {
                                if (Util.canDoWithTime(lastTimeDie, 5000)) {
                                    if (this.id == 13) {
                                        this.zone.isbulon13Alive = true;
                                    }
                                    if (this.id == 14) {
                                        this.zone.isbulon14Alive = true;
                                    }
                                    this.hoiSinh();
                                    this.sendMobHoiSinh();
                                }
                            }
                            break;
                        case ConstMap.MAP_BAN_DO_KHO_BAU:
                            if (this.tempId == 72 || this.tempId == 71) {//ro bot bao ve
                                if (System.currentTimeMillis() - this.lastTimeDie > 3000) {
                                    try {
                                        Message t = new Message(102);
                                        t.writer().writeByte((this.tempId == 71 ? 7 : 6));
                                        Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                                        t.cleanup();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            break;
                        case 6:
                            if (this.tempId == 77) {// gau
                                if (System.currentTimeMillis() - this.lastTimeDie > 3_600_000) {
                                    try {
                                        int actionNewBoss = Util.nextInt(0, 4);
                                        Message msg = new Message(102);
                                        msg.writer().writeByte(actionNewBoss);
                                        msg.writer().writeByte(id); // index boss
                                        Service.gI().sendMessAllPlayerInMap(this.zone, msg);
                                        msg.cleanup();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            break;
                        case 7:
                            if (this.tempId == 70) {//hirru
                                if (System.currentTimeMillis() - this.lastTimeDie > 5000) {
                                    if (this.sys == 0 || this.sys == 1) {
                                        this.sys += 1;
                                        if (this.sys == 1) {
                                            try {
                                                Message t = new Message(101);//transsssssform goldddddddd
                                                t.writer().writeByte(6); // action
                                                t.writer().writeShort(this.location.x); //x
                                                t.writer().writeShort(this.location.y); //y
                                                Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                                                t.cleanup();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else if (this.sys == 2) {
                                            try {
                                                Message t = new Message(101);//transsssssform haftbody
                                                t.writer().writeByte(5); // action
                                                Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                                                t.cleanup();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        this.hoiSinh();
                                        this.sendMobHoiSinh();
                                    } else if (this.sys == 2) {
                                        // time hồi sinh sau 1 ngày
                                        if (System.currentTimeMillis() - this.lastTimeDie > 3_600_000) {
                                            this.sys = 0;
                                            this.hoiSinh();
                                            this.sendMobHoiSinh();
                                        } else {
                                            try {
                                                Message t = new Message(101);
                                                t.writer().writeByte(9);
                                                Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                                                t.cleanup();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (Util.canDoWithTime(lastTimeDie, 8000)) {
                                    this.randomSieuQuai();
                                    this.hoiSinh();
                                    this.sendMobHoiSinh();
                                }
                            }
                            break;
                        default:
                            if (Util.canDoWithTime(lastTimeDie, 8000)) {
                                this.randomSieuQuai();
                                this.hoiSinh();
                                this.sendMobHoiSinh();
                            }
                    }
                }
                if (this.effectSkill != null) {
                    effectSkill.update();
                }
                attackPlayer();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi update ở Mob");
        }
    }

    private void gauTuongCuopAT(List<Player> listChar, long dame) {
        byte actionNewBoss = (byte) Util.nextInt(11, 20);
        Player pl = getPlayerCanAttack();
        Message msg = null;
        try {
            msg = new Message(102);
            msg.writer().writeByte(actionNewBoss);
            msg.writer().writeByte(id);
            msg.writer().writeByte(listChar.size());
            for (Player player : listChar) {
                msg.writer().writeInt((int) player.id);
                msg.writer().writeInt((int) player.injured(null, (int) dame, false, true));
            }
            int dir = this.location.x - listChar.get(0).location.x <= 0 ? 1 : -1;
            msg.writer().writeByte(dir);
            Service.gI().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private List<Player> getListPlayerCanAttack() {
        List<Player> plAttack = new ArrayList<>();
        int distance = (this.tempId == 71 ? 250 : 600);
        try {
            if (this.zone != null && !this.zone.getNotBosses().isEmpty()) {
                List<Player> players = this.zone.getNotBosses();
                if (!players.isEmpty()) {
                    for (int i = 0; i < players.size(); i++) {
                        Player pl = players.get(i);
                        if (pl != null && pl.zone != null && pl.effectSkin != null && !pl.isDie() && !pl.isBoss && !pl.effectSkin.isVoHinh && !pl.isNewPet && !pl.isReferee && pl.zone.equals(this.zone)) {
                            int dis = Util.getDistance(pl, this);
                            if (dis <= distance) {
                                plAttack.add(pl);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return plAttack;
    }

    private void sendMobBossHiruAttack(List<Player> players, long dame) {
        int action = Util.nextInt(0, 4);
        if (action != 3) {
            try {
                Message t = new Message(101);
                t.writer().writeByte(action); //actioin
                t.writer().writeByte(players.size()); //numchar
                for (Byte i = 0; i < players.size(); i++) {
                    t.writer().writeInt((int) players.get(i).id); // pl id
                    int newDame = (int) dame + (players.get(i).nPoint.hpMax / 3) * 2;
                    long damedame = players.get(i).injured(null, (int) (Util.nextInt(players.get(i).nPoint.hpMax / 20, newDame / 4)), false, true);
                    if (players.get(i).nPoint.hp > 2_100_000_000) {
                        damedame = 0;
                    }
                    t.writer().writeInt((damedame > 2_100_000_000 ? 2_100_000_000 : (int) damedame)); //dame
                }
                Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                t.cleanup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (!this.zone.getPlayers().isEmpty()) {
                    Message t = new Message(101);
                    t.writer().writeByte(3); // action
                    short x = (short) this.zone.getPlayers().get(Util.nextInt(0, this.zone.getPlayers().size() - 1)).location.x;
                    this.location.x = x;
                    t.writer().writeShort(x); //x
                    t.writer().writeShort(this.location.y); //y
                    Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                    t.cleanup();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void attackPlayer() {
        try {
            if (this.zone != null && this.effectSkill != null && !isDie() && !effectSkill.isHaveEffectSkill() && tempId != 0) {
                if ((this.tempId == 72 || this.tempId == 71)) {
                    if (Util.canDoWithTime(lastTimeAttackPlayer, 500)) {
                        List<Player> pl = getListPlayerCanAttack();
                        if (!pl.isEmpty()) {
                            this.sendMobBossBdkbAttack(pl, this.point.getDameAttack());
                        } else {
                            if (this.tempId == 71) {
                                Player plA = getPlayerCanAttack();
                                if (plA != null && plA.location != null && plA.zone != null && plA.zone.equals(this.zone)) {
                                    try {
                                        Message t = new Message(102);
                                        t.writer().writeByte(5);
                                        t.writer().writeByte(plA.location.x);
                                        this.location.x = plA.location.x;
                                        Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                                        t.cleanup();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
                        this.lastTimeAttackPlayer = System.currentTimeMillis();
                    }
                } else if (this.tempId == 70) {
                    if (Util.canDoWithTime(lastTimeAttackPlayer, 1500)) {
                        List<Player> pl = getListPlayerCanAttack();
                        if (!pl.isEmpty()) {
                            this.sendMobBossHiruAttack(pl, this.point.getDameAttack() / 60);
                        } else {
                            if (!this.zone.getPlayers().isEmpty()) {
                                try {
                                    Message t = new Message(101);// move tele
                                    t.writer().writeByte(8); // action
                                    short x = (short) this.zone.getPlayers().get(Util.nextInt(0, this.zone.getPlayers().size() - 1)).location.x;
                                    this.location.x = x;
                                    t.writer().writeShort(x); //x
                                    t.writer().writeShort(this.location.y); //y
                                    Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                                    t.cleanup();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        this.lastTimeAttackPlayer = System.currentTimeMillis();
                    }
                } else if (Util.canDoWithTime(lastTimeAttackPlayer, 2000)) {
                    Player pl = getPlayerCanAttack();
                    if (pl != null && pl.zone.equals(this.zone)) {
                        this.mobAttackPlayer(pl);
                    }
                    this.lastTimeAttackPlayer = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi ở attackplayer clas Mob");
        }
    }

    private Player getPlayerCanAttack() {
        int distance = 50;
        Player plAttack = null;
        try {
            if (this.zone != null && !this.zone.getNotBosses().isEmpty()) {
                List<Player> players = this.zone.getNotBosses();
                if (!players.isEmpty()) {
                    for (int i = 0; i < players.size(); i++) {
                        Player pl = players.get(i);
                        if (pl != null && pl.zone != null && pl.effectSkin != null && !pl.isDie() && !pl.isBoss && !pl.effectSkin.isVoHinh && !pl.isNewPet && !pl.isReferee && pl.zone.equals(this.zone)) {
                            int dis = Util.getDistance(pl, this);
                            if (dis <= distance) {
                                plAttack = pl;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plAttack;
    }

    //**************************************************************************
    private void mobAttackPlayer(Player player) {
        int dameMob = this.point.getDameAttack();
        if (player.charms.tdDaTrau > System.currentTimeMillis()) {
            dameMob /= 2;
        }
        if (this.isSieuQuai()) {
            dameMob = player.nPoint.hpMax / 10;
        }
        int dame = player.injured(null, dameMob, false, true);
        this.sendMobAttackMe(player, dame);
        this.sendMobAttackPlayer(player);
    }

    private void sendMobAttackMe(Player player, int dame) {
        if (!player.isPet && !player.isNewPet) {
            Message msg;
            try {
                msg = new Message(-11);
                msg.writer().writeByte(this.id);
                msg.writer().writeInt(dame); //dame
                player.sendMessage(msg);
                msg.cleanup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    private Player getPlayerCanAttack() {
//        int distance = 100;
//        Player plAttack = null;
//        try {
//            List<Player> players = this.zone.getNotBosses();
//            for (Player pl : players) {
//                if (this.playerAttackList != null && !this.playerAttackList.isEmpty()) {
//                    Player firstPlayer = this.playerAttackList.get(0);
//                    if (!firstPlayer.isDie() && !firstPlayer.isBoss && !firstPlayer.effectSkin.isVoHinh) {
//                        int dis = Util.getDistance(firstPlayer, this);
//                        if (dis <= 400) {
//                            plAttack = firstPlayer;
//                            distance = dis;
//                        }
//                        return plAttack;
//                    }
//                }
//                if (this.playerAttackList == null || !pl.isDie() && !pl.isBoss && !pl.effectSkin.isVoHinh) {
//                    int dis = Util.getDistance(pl, this);
//                    if (dis <= distance) {
//                        plAttack = pl;
//                        distance = dis;
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return plAttack;
//    }
    public List<Player> playerAttackList = new ArrayList<>();

    public void addPlayerAttack(Player player) {
        if (player != null && !this.isDie()) {
            this.playerAttackList.add(player);
        } else {
            this.playerAttackList.clear();
        }

    }

    public void sendMobDieAfterMobMeAttacked(Player plKill, int dameHit) {
        this.status = 0;
        Message msg;
        try {
            if (this.id == 13) {
                this.zone.isbulon13Alive = false;
            }
            if (this.id == 14) {
                this.zone.isbulon14Alive = false;
            }
            msg = new Message(-12);
            msg.writer().writeByte(this.id);
            msg.writer().writeInt(dameHit);
            msg.writer().writeBoolean(false); // crit

            List<ItemMap> items = mobReward(plKill, this.dropItemTask(plKill), msg);
            Service.getInstance().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
            hutItem(plKill, items);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logException(Mob.class, e);
        }
        this.lastTimeDie = System.currentTimeMillis();
    }

    private void sendMobBossBdkbAttack(List<Player> players, long dame) {
        try {
            if (!players.isEmpty()) {
                if (this.tempId == 72) {
                    try {
                        Message t = new Message(102);
                        int action = Util.nextInt(0, 2);
                        t.writer().writeByte(action);
                        if (action != 1) {
                            this.location.x = players.get(Util.nextInt(0, players.size() - 1)).location.x;
                        }
                        t.writer().writeByte(players.size());
                        for (Byte i = 0; i < players.size(); i++) {
                            t.writer().writeInt((int) players.get(i).id);
                            t.writer().writeInt((int) players.get(i).injured(null, (int) dame, false, true));
                        }
                        Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                        t.cleanup();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (this.tempId == 71) {
                    try {
                        Message t = new Message(102);
                        t.writer().writeByte(Util.getOne(3, 4));
                        t.writer().writeByte(players.size());
                        for (Byte i = 0; i < players.size(); i++) {
                            t.writer().writeInt((int) players.get(i).id);
                            t.writer().writeInt((int) players.get(i).injured(null, (int) dame, false, true));
                        }
                        Service.getInstance().sendMessAllPlayerInMap(this.zone, t);
                        t.cleanup();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi sendMobBossBdkbAttack class Mob");
        }
    }

    private void sendMobAttackPlayer(Player player) {
        Message msg;
        try {
            msg = new Message(-10);
            msg.writer().writeByte(this.id);
            msg.writer().writeInt((int) player.id);
            msg.writer().writeInt(player.nPoint.hp);
            Service.getInstance().sendMessAnotherNotMeInMap(player, msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hoiSinh() {
        this.status = 5;
        this.point.hp = this.point.maxHp;
        this.setTiemNang();
    }

    public boolean mapcosieuquairoi() {
        for (Mob mob : this.zone.mobs) {
            if (mob.isSieuQuai()) {
                return true;
            }
        }
        return false;
    }

    public void randomSieuQuai() {
        if (this.tempId != 0 && MapService.gI().isMapKhongCoSieuQuai(this.zone.map.mapId) && Util.isTrue(40, 100) && !mapcosieuquairoi()) {
            this.lvMob = 1;
//            this.point.maxHp *= 2;
        }
    }

    private void sendMobHoiSinh() {
        Message msg;
        try {
            msg = new Message(-13);
            msg.writer().writeByte(this.id);
            msg.writer().writeByte(this.tempId);
            msg.writer().writeByte(lvMob);
            msg.writer().writeInt(this.point.hp);
            Service.getInstance().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //**************************************************************************
    private void sendMobDieAffterAttacked(Player plKill, int dameHit) {// fix bug
        Message msg;
        try {
            if (plKill != null && plKill.nPoint != null && this.zone != null) {
                msg = new Message(-12);
                msg.writer().writeByte(this.id);
                msg.writer().writeInt(dameHit);
                msg.writer().writeBoolean(plKill.nPoint.isCrit); // crit
                List<ItemMap> items = mobReward(plKill, this.dropItemTask(plKill), msg);
                Service.getInstance().sendMessAllPlayerInMap(this.zone, msg);
                msg.cleanup();
                if (plKill != null && plKill.zone != null && this.zone != null && plKill.zone.equals(this.zone)) {
                    hutItem(plKill, items);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hutItem(Player player, List<ItemMap> items) {
        if (!player.isPet) {
            if (player.charms.tdThuHut > System.currentTimeMillis()) {
                for (ItemMap item : items) {
                    if (item != null && item.itemTemplate.id != 590) {
                        ItemMapService.gI().pickItem(player, item.itemMapId, true);
                    } else {
                        continue;
                    }
                }
            }
        } else {
            if (((Pet) player).master.charms.tdThuHut > System.currentTimeMillis()) {
                for (ItemMap item : items) {
                    if (item != null && item.itemTemplate.id != 590) {
                        ItemMapService.gI().pickItem(((Pet) player).master, item.itemMapId, true);
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    private List<ItemMap> mobReward(Player player, ItemMap itemTask, Message msg) {
        List<ItemMap> itemReward = new ArrayList<>();
        try {
            itemReward = this.getItemMobReward(player, this.location.x + Util.nextInt(-10, 10),
                    this.zone.map.yPhysicInTop(this.location.x, this.location.y));
            if (itemTask != null) {
                itemReward.add(itemTask);
            }

            if (this.tempId == 70) {
                if (Util.nextInt(0, 1200) < 150) {
                    ItemMap itemMap = new ItemMap(this.zone, 568, 1,
                            this.location.x + Util.nextInt(-50, 50), this.zone.map.yPhysicInTop(this.location.x, this.location.y), player.id);
                    itemMap.options.add(new Item.ItemOption(30, 0));
                    if (itemMap != null) {
                        itemReward.add(itemMap);
                    }
                }
            }
            msg.writer().writeByte(itemReward.size()); //sl item roi
            for (ItemMap itemMap : itemReward) {
                msg.writer().writeShort(itemMap.itemMapId);// itemmapid
                msg.writer().writeShort(itemMap.itemTemplate.id); // id item
                msg.writer().writeShort(itemMap.x); // xend item
                msg.writer().writeShort(itemMap.y); // yend item
                msg.writer().writeInt((int) itemMap.playerId); // id nhan nat
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemReward;
    }

    public List<ItemMap> getItemMobReward(Player player, int x, int yEnd) {
        List<ItemMap> list = new ArrayList<>();
        MobReward mobReward = Manager.MOB_REWARDS.get(this.tempId);
        if (mobReward == null) {
            return list;
        }
        List<ItemMobReward> items = mobReward.getItemReward();
        List<ItemMobReward> golds = mobReward.getGoldReward();
        if (!items.isEmpty()) {
            ItemMobReward item = items.get(Util.nextInt(0, items.size() - 1));
            ItemMap itemMap = item.getItemMap(zone, player, x, yEnd);
            if (itemMap != null) {
                list.add(itemMap);
            }
        }
        if (!golds.isEmpty()) {
            ItemMobReward gold = golds.get(Util.nextInt(0, golds.size() - 1));
            ItemMap itemMap = gold.getItemMap(zone, player, x, yEnd);
            if (itemMap != null) {
                list.add(itemMap);
            }
        }
        //drop set kich hoat by new member
        if (Util.isTrue(5, 100)) {
            if (player.isNewMember && MapService.gI().isMapSetKichHoat(this.zone.map.mapId)) {
                list.add(ArrietyDrop.DropItemSetKichHoat(player, 1, this.location.x, yEnd));
            }
        }
        //drop thức ăn
        if (Util.isTrue(100.0f, 100)) {
            if (player.setClothes.setThanLinh == 5 && MapService.gI().isMapCold(player.zone.map)) {
                list.add(new ItemMap(zone, ArrietyDrop.list_thuc_an[Util.nextInt(ArrietyDrop.list_thuc_an.length)], 1, x, yEnd, player.id));
            }
        }
        // mảnh thiên xứ
        if (Util.isTrue(100.0f, 100)) {
            if (MapService.gI().isMapUpManhTS(player.zone.map) && player.setClothes.SetHuyDiet == 5) {
                list.add(new ItemMap(zone, ArrietyDrop.list_manh_thien_xu[Util.nextInt(ArrietyDrop.list_manh_thien_xu.length)], 1, x, yEnd, player.id));
            }
        }
        if (player.itemTime.isUseMayDo && Util.isTrue(15, 100) && this.tempId > 57 && this.tempId < 66) {
            list.add(new ItemMap(zone, 380, 1, x, player.location.y, player.id));
        }
        if (player.itemTime.isUseMayDo2 && Util.isTrue(5, 100) && this.tempId > 1 && this.tempId < 81) {
            list.add(new ItemMap(zone, 723, 1, x, player.location.y, player.id));// cai nay sua sau nha
        }

        if (player.itemTime.isUseMayDoCa && Util.isTrue(5, 100) && this.tempId > 1 && this.tempId < 14) {// ti
            list.add(new ItemMap(zone, 1003, 1, x, player.location.y, player.id));
        }
        if (player.itemTime.isUseMayDoTom && Util.isTrue(5, 100) && this.tempId > 1 && this.tempId < 14) {
            list.add(new ItemMap(zone, 696, 1, x, player.location.y, player.id));
        }
        if (player.itemTime.isUseMayDoOc && Util.isTrue(5, 100) && this.tempId > 1 && this.tempId < 14) {
            list.add(new ItemMap(zone, 695, 1, x, player.location.y, player.id));
        }
        if (player.itemTime.isUseMayDoCua && Util.isTrue(5, 100) && this.tempId > 1 && this.tempId < 14) {
            list.add(new ItemMap(zone, 697, 1, x, player.location.y, player.id));
        }
//        if (player.isPet && Util.isTrue(1, 900)) {
//            list.add(new ItemMap(zone, 669, 1, x, player.location.y, player.id));
//        }
//        if (player.isPet && Util.isTrue(10, 100)) {
//            list.add(new ItemMap(zone, 670, 1, x, player.location.y, player.id));
//        }

        if (player != null && player.clan != null && player.clan.banDoKhoBau != null && MapService.gI().isMapBanDoKhoBau(player.zone.map.mapId)) {
            int level = player.clan.banDoKhoBau.level;
            int slhn = Util.nextInt(1, 3) * (level / 10);
            int slv = Util.nextInt(1, 3) * (level / 10);
            int tlGold = (player.nPoint.getTlGold() + 100);
            if (Util.nextInt(0, 100) < 100) {
                for (int i = 0; i < slv; i++) {
                    list.add(new ItemMap(zone, 190, (Math.round(Util.nextInt(150000, 250000) / 100) * tlGold), Util.nextInt(-5, 5) * i + x, yEnd, -1));
                }
            }
        }
        list.add(new ItemMap(zone, 190, 100000, x, yEnd, player.id));
        return list;
    }

    private ItemMap dropItemTask(Player player) {
        ItemMap itemMap = null;
        switch (this.tempId) {
            case ConstMob.KHUNG_LONG:
            case ConstMob.LON_LOI:
            case ConstMob.QUY_DAT:
                if (TaskService.gI().getIdTask(player) == ConstTask.TASK_2_0) {
                    itemMap = new ItemMap(this.zone, 73, 1, this.location.x, this.location.y, player.id);
                }
                break;
        }
        if (itemMap != null) {
            return itemMap;
        }
        return null;
    }

    private void sendMobStillAliveAffterAttacked(int dameHit, boolean crit) {
        Message msg;
        try {
            msg = new Message(-9);
            msg.writer().writeByte(this.id);
            msg.writer().writeInt(this.point.gethp());
            msg.writer().writeInt(dameHit);
            msg.writer().writeBoolean(crit); // chí mạng
            msg.writer().writeInt(-1);
            Service.getInstance().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
