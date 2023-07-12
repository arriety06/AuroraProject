
package com.girlkun.models.boss.list_boss.TDST_NM;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.SkillService;
import com.girlkun.services.TaskService;
import com.girlkun.utils.SkillUtil;
import com.girlkun.utils.Util;
import java.util.Random;

/**
 *
 * @author vdin2
 */
public class So3Nm extends Boss {

    public So3Nm() throws Exception {
        super(BossID.SO_3_NM, BossesData.SO_3_NM);
    }
    @Override
    public void reward(Player plKill) {
        int slhndrop = Util.nextInt(10, 20);
        ItemMap caitrang = new ItemMap(this.zone, 430, 1,this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id);
        caitrang.options.add(new Item.ItemOption(50, Util.nextInt(10, 25)));
        caitrang.options.add(new Item.ItemOption(77, Util.nextInt(10, 25)));
        caitrang.options.add(new Item.ItemOption(103, Util.nextInt(10, 25)));
        caitrang.options.add(new Item.ItemOption(101, Util.nextInt(10, 25)));
        caitrang.options.add(new Item.ItemOption(94, Util.nextInt(10, 25)));
        caitrang.options.add(new Item.ItemOption(93, Util.nextInt(3, 7)));
        Service.gI().dropItemMap(this.zone, caitrang);
        for(int i = 0; i < slhndrop; i++){
            Service.gI().dropItemMap(this.zone, new ItemMap(zone, 861, 10, Util.nextInt(-5, 5)*i+this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), -1));
        }
    }

  
   @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        
    }
    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage/1);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                  damage = damage/1;
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    
    }
    @Override
    public void attack() {
        if (Util.canDoWithTime(this.lastTimeAttack, 100) && this.typePk == ConstPlayer.PK_ALL) {
            this.lastTimeAttack = System.currentTimeMillis();
            try {
                Player pl = getPlayerAttack();
                if (pl == null || pl.isDie() || pl.isNewPet || pl.isBoss) {
                    return;
                }
                this.playerSkill.skillSelect = this.playerSkill.skills.get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
                if (Util.getDistance(this, pl) <= this.getRangeCanAttackWithSkillSelect()) {
                    if (Util.isTrue(5, 20)) {
                        if (SkillUtil.isUseSkillChuong(this)) {
                            this.moveTo(pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(20, 200)),
                                    Util.nextInt(10) % 2 == 0 ? pl.location.y : pl.location.y - Util.nextInt(0, 70));
                        } else {
                            this.moveTo(pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(10, 40)),
                                    Util.nextInt(10) % 2 == 0 ? pl.location.y : pl.location.y - Util.nextInt(0, 50));
                        }
                    }
                    SkillService.gI().useSkill(this, pl, null,null);
                    checkPlayerDie(pl);
                } else {
                    if (Util.isTrue(1, 2)) {
                        this.moveToPlayer(pl);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    }

