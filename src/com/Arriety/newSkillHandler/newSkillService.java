package com.Arriety.newSkillHandler;

import com.girlkun.models.mob.Mob;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.network.io.Message;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aurura
 */
public class newSkillService {

    public static newSkillService instance;

    public static newSkillService gI() {
        if (instance == null) {
            instance = new newSkillService();
        }
        return instance;
    }

    public void sendGongSkill(Player pl, int idSkill, byte typeFrame, short timeGongSkill, byte isFly, byte Dir) {
        Message msg;
        try {
            msg = new Message(-45);
            msg.writeByte(20);
            msg.writeInt((int) pl.id);
            msg.writeShort(idSkill);
            msg.writeByte(typeFrame);
            msg.writeByte(Dir);
            msg.writeShort(timeGongSkill);
            msg.writeByte(isFly);
            msg.writeByte(0);
            msg.writeByte(0);
            Service.gI().sendMessAllPlayerInMap(pl, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void sendChuongSkill(Player pl, int idSkill, short x, short y, short timeChuong) {
        Message msg;
        try {
            msg = new Message(-45);
            msg.writeByte(21);
            msg.writeInt((int) pl.id);
            msg.writeShort(idSkill);
            msg.writeShort(x);
            msg.writeShort(y);
            msg.writeShort(timeChuong);
            msg.writeShort(1000);
            msg.writeByte(0);
            msg.writeByte(0);
            msg.writeByte(0);
            Service.gI().sendMessAllPlayerInMap(pl, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void MaPhongBa(Player pluseskill, short x, short y, List<Player> players, List<Mob> mobs) {
        Message msg;
        try {
            msg = new Message(-45);
            msg.writeByte(21);
            msg.writeInt((int) pluseskill.id);
            msg.writeShort(26);
            msg.writeShort(x);
            msg.writeShort(y);
            msg.writeShort(100000);//time
            msg.writeShort(0);//range
            msg.writeByte(0);
            msg.writeByte(mobs.size() + players.size());
            for (Mob mob : mobs) {
                msg.writeByte(0);
                msg.writeByte(mob.id);
                new Thread(() -> {
                    try {
                        Thread.sleep(2800);
                        mob.effectSkill.isMaPhong3 = true;
                        mob.effectSkill.timeMaPhong3 = 100000;
                        mob.effectSkill.lastTimeMaPhong3 = System.currentTimeMillis();
                        Service.gI().ChangeMonsterBody(pluseskill, 1, mob.id, 11166);
                        mob.injured(pluseskill, 11166, false);
                    } catch (Exception e) {

                    }

                }).start();
            }
            for (Player pl : players) {
                msg.writeByte(1);
                msg.writeInt((int) pl.id);
                new Thread(() -> {
                    try {
                        Thread.sleep(2800);
                        pl.effectSkill.isMaPhong3 = true;
                        pl.effectSkill.TimeMaPhong3 = 12000;
                        pl.effectSkill.lastTimeMaPhong3 = System.currentTimeMillis();
                        Service.gI().Send_Caitrang(pl);
                    } catch (Exception e) {

                    }

                }).start();
            }
            msg.writeByte(0);
            Service.gI().sendMessAllPlayerInMap(pluseskill, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }
}
