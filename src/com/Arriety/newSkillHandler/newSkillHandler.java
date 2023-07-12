/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Arriety.newSkillHandler;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.player.Pet;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.MapService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.SkillService;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arriety
 */
public class newSkillHandler {

    public static newSkillHandler instance;

    public static newSkillHandler gI() {
        if (instance == null) {
            instance = new newSkillHandler();
        }
        return instance;
    }

    public void ChuongKamehameha(Player pl, short timeGong, byte Dir, short x, short y, short timeChuong, int damage, int rangeX) {

        new Thread(() -> {
            try {
                newSkillService.gI().sendGongSkill(pl, 24, (byte) 1, (short) timeGong, (byte) 0, Dir);
                Thread.sleep(timeGong);
                newSkillService.gI().sendChuongSkill(pl, 24, x, y, (short) (timeChuong + timeGong));
                pl.effectSkill.TimeChuongKamehameha = timeGong + 2200 + System.currentTimeMillis();
                while (System.currentTimeMillis() < pl.effectSkill.TimeChuongKamehameha) {
                    for (Mob mob : pl.zone.mobs) {
                        if ((Dir == 1 ? (mob.location.x > pl.location.x && mob.location.x < x) : (pl.location.x > mob.location.x && mob.location.x > x))
                                && (y > mob.location.x ? y - mob.location.y <= pl.playerSkill.skillSelect.dy : mob.location.y - y <= pl.playerSkill.skillSelect.dy)) {
                            mob.injured(pl, damage, true);
                        }
                    }
                    for (Player player : pl.zone.getHumanoids()) {
                        if ((Dir == 1 ? (player.location.x > pl.location.x && player.location.x < x) : (pl.location.x > player.location.x && player.location.x > x))
                                && (y > player.location.x ? y - player.location.y <= pl.playerSkill.skillSelect.dy : player.location.y - y <= pl.playerSkill.skillSelect.dy)) {

                            SkillService.gI().playerAttackPlayer(pl, player, false);
                        }
                    }

                    for (Player player : pl.zone.getBosses()) {
                        if ((Dir == 1 ? (player.location.x > pl.location.x && player.location.x < x) : (pl.location.x > player.location.x && player.location.x > x))
                                && (y > player.location.x ? y - player.location.y <= pl.playerSkill.skillSelect.dy : player.location.y - y <= pl.playerSkill.skillSelect.dy)) {
                            SkillService.gI().playerAttackPlayer(pl, player, false);

                        }
                    }
                    Thread.sleep(500);
                }
            } catch (Exception e) {

            }

        }).start();

    }

    public void Galickgun(Player pl, short timeGong, byte Dir, short x, short y, short timeChuong, int damage, int rangeX) {

        new Thread(() -> {
            try {
                newSkillService.gI().sendGongSkill(pl, 25, (byte) 2, (short) timeGong, (byte) 0, Dir);
                Thread.sleep(timeGong);
                newSkillService.gI().sendChuongSkill(pl, 25, x, y, (short) (timeChuong + timeGong));
                pl.effectSkill.TimeChuongGalick = timeGong + 2200 + System.currentTimeMillis();
                while (System.currentTimeMillis() < pl.effectSkill.TimeChuongGalick) {
                    for (Mob mob : pl.zone.mobs) {
                        if ((Dir == 1 ? (mob.location.x > pl.location.x && mob.location.x < x) : (pl.location.x > mob.location.x && mob.location.x > x))
                                && (y > mob.location.x ? y - mob.location.y <= pl.playerSkill.skillSelect.dy : mob.location.y - y <= pl.playerSkill.skillSelect.dy)) {
                            mob.injured(pl, damage, true);
                        }
                    }
                    for (Player player : pl.zone.getHumanoids()) {
                        if ((Dir == 1 ? (player.location.x > pl.location.x && player.location.x < x) : (pl.location.x > player.location.x && player.location.x > x))
                                && (y > player.location.x ? y - player.location.y <= pl.playerSkill.skillSelect.dy : player.location.y - y <= pl.playerSkill.skillSelect.dy)) {
                            SkillService.gI().playerAttackPlayer(pl, player, false);
                        }
                    }
                    for (Player player : pl.zone.getBosses()) {
                        if ((Dir == 1 ? (player.location.x > pl.location.x && player.location.x < x) : (pl.location.x > player.location.x && player.location.x > x))
                                && (y > player.location.x ? y - player.location.y <= pl.playerSkill.skillSelect.dy : player.location.y - y <= pl.playerSkill.skillSelect.dy)) {
                            SkillService.gI().playerAttackPlayer(pl, player, false);
                        }
                    }
                    Thread.sleep(500);
                }
            } catch (Exception e) {

            }

        }).start();

    }
    private boolean canAttackPlayer(Player p1, Player p2) {
        if (p1.isDie() || p2.isDie() || p1.isNewPet || p2.isNewPet) {
            return false;
        }

        if (p1.typePk == ConstPlayer.PK_ALL || p2.typePk == ConstPlayer.PK_ALL) {
            return true;
        }
        if ((p1.cFlag != 0 && p2.cFlag != 0)
                && (p1.cFlag == 8 || p2.cFlag == 8 || p1.cFlag != p2.cFlag)) {
            return true;
        }
        if (p1.pvp == null || p2.pvp == null) {
            return false;
        }
        if (p1.pvp.isInPVP(p2) || p2.pvp.isInPVP(p1)) {
            return true;
        }
        return false;
    }
    
    public void MaPhong3(Player player) {
        List<Mob> mobs;
        List<Player> players;
        mobs = new ArrayList<>();
        players = new ArrayList<>();
        if (!MapService.gI().isMapOffline(player.zone.map.mapId)) {
            List<Player> playersMap = player.zone.getHumanoids();
            for (Player pl : playersMap) {
                if (pl != null && !player.equals(pl) && canAttackPlayer(player, pl)) {
                    if (player.isPet && ((Pet) player).master.equals(pl)) {
                        continue;
                    }
                    EffectSkillService.gI().startStun(pl, System.currentTimeMillis(), 4400);
                    players.add(pl);
                }
            }
        }

        for (Mob mob : player.zone.mobs) {
                mobs.add(mob);
                if(mob.id != 0){
                    mob.effectSkill.startStun(System.currentTimeMillis(), 4400);
                }
        }

        new Thread(
                () -> {
                    try {
                        newSkillService.gI().sendGongSkill(player, 26, (byte) 3, (short) 2200, (byte) 0, (byte) 0);
//                        Thread.sleep(2200);
                        newSkillService.gI().MaPhongBa(player, (short) player.location.x, (short) player.location.y, players, mobs);
                    } catch (Exception e) {

                    }

                }
        ).start();

    }
}
