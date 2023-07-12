/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlkun.models.boss.list_boss;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.*;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;

/**
 * @author by Aurura
 */
public class MewMew extends Boss {

    public int idNpc;
    protected Player playerAtt;

    public MewMew(int bossID, BossData bossData, Zone zone, int x, int y, int IDNPC) throws Exception {
        super(bossID, bossData);
        this.zone = zone;
        this.location.x = x;
        this.location.y = y;
        this.idNpc = IDNPC;
    }

    @Override
    public void joinMap() {
        if (this.zone != null) {
            if (this.currentLevel == 0) {
                ChangeMapService.gI().changeMap(this, this.zone, 400, 408);
            }
        }
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage / 7);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage / 4;
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
                ChangeMapService.gI().exitMap(this);
                Service.gI().sendEffectHideNPC(plAtt, (byte) idNpc, (byte) 1);

            }
            return damage;
        } else {
            return 0;
        }
    }

    @Override
    public void attack() {
        super.attack();
        if (playerTarger == null || playerTarger.isDie()) {
            Service.gI().sendEffectHideNPC(playerTarger, (byte) idNpc, (byte) 1);
            ChangeMapService.gI().exitMap(this);
            synchronized (this) {
                BossManager.gI().removeBoss(this);
            }
            this.bossStatus = null;
            this.lastZone = null;
            this.playerTarger = null;
            this.bossAppearTogether = null;
            this.parentBoss = null;
            this.zoneFinal = null;
            this.dispose();
        }
    }
    long lasttimemove;

    @Override
    public void leaveMap() {
        super.leaveMap();
        synchronized (this) {
            BossManager.gI().removeBoss(this);
        }
        this.bossStatus = null;
        this.lastZone = null;
        this.playerTarger = null;
        this.bossAppearTogether = null;
        this.parentBoss = null;
        this.zoneFinal = null;
        this.dispose();
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.attack();
    }

    @Override
    public void die(Player plKill) {
        super.die(plKill);
    }

}
