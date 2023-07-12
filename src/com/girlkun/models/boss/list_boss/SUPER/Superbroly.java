package com.girlkun.models.boss.list_boss.SUPER;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.PetService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;

public class Superbroly extends Boss {

    boolean xhpnext;

    public Superbroly(int bossID, BossData bossData) throws Exception {
        super(bossID, bossData);
    }

    @Override
    public void reward(Player plKill) {
        if (plKill.pet == null) {
            PetService.gI().createNormalPet(plKill);
        }
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if ((!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000))) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            if (!piercing) {
                if ((plAtt.playerSkill.skillSelect.template.id == Skill.ANTOMIC || plAtt.playerSkill.skillSelect.template.id == Skill.KAMEJOKO || plAtt.playerSkill.skillSelect.template.id == Skill.MASENKO)) {
                    this.chat("Xí hụt");
                    damage = 0;
                }
                if (!(plAtt.playerSkill.skillSelect.template.id == Skill.TU_SAT || plAtt.playerSkill.skillSelect.template.id == Skill.MAKANKOSAPPO || plAtt.playerSkill.skillSelect.template.id == Skill.QUA_CAU_KENH_KHI)) {
                    if (damage >= this.nPoint.hpMax / 100) {
                        damage = this.nPoint.hpMax / 100;
                    }
                }
            }
            this.nPoint.subHP(damage);
            if (!xhpnext && this.nPoint.hp > this.nPoint.hpMax * 2 / 3) {
                xhpnext = true;
            }
            if (this.nPoint.hp <= this.nPoint.hpMax / 2 && xhpnext) {
                this.nPoint.hpMax *= 1.5;
                xhpnext = false;
            }
            if (isDie()) {
                if (this.newpet != null) {
                    ChangeMapService.gI().exitMap(this.newpet);
                    this.newpet.dispose();
                    this.newpet = null;
                }
                this.setDie(plAtt);
                die(plAtt);
                int[] idmapbroly = new int[]{5, 6, 10, 11, 12, 13, 19, 20, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
                int indexmapxh = Util.nextInt(idmapbroly.length);
                int hpbroly = this.nPoint.hpMax / 50;
                if (hpbroly > 16_000_000) {
                    hpbroly = 16_000_000;
                }
                int damebroly = this.nPoint.dame / 50;
                if (damebroly > 1_000_000) {
                    damebroly = 1_000_000;
                }
                BossData brolythuong = new BossData(
                        "Broly " + (this.id - BossID.SUPER_BROLY),
                        ConstPlayer.XAYDA,
                        new short[]{291, 292, 293, -1, -1, -1},
                        damebroly,
                        new int[]{hpbroly},
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
                BossManager.gI().createBoss((int) (this.id - BossID.SUPER_BROLY + BossID.BROLY_THUONG), brolythuong);
            }
            return damage;
        } else {
            return 0;
        }
    }

    @Override
    public void active() {
        super.active();
    }
    private long st;

    @Override
    public void joinMap() {
        super.joinMap();
        short[][] PET_ID = {{285, 286, 287}, {288, 289, 290}, {282, 283, 284}, {304, 305, 303}};
        int genderpet = Util.nextInt(2);
        PetService.Pet2(this, PET_ID[genderpet][0], PET_ID[genderpet][1], PET_ID[genderpet][2], "Đệ tử");
        Service.getInstance().point(this);
    }

    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
    }

}
