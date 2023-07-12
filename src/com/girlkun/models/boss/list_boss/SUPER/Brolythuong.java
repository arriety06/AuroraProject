package com.girlkun.models.boss.list_boss.SUPER;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossStatus;
import static com.girlkun.models.boss.BossStatus.ACTIVE;
import static com.girlkun.models.boss.BossStatus.CHAT_E;
import static com.girlkun.models.boss.BossStatus.CHAT_S;
import static com.girlkun.models.boss.BossStatus.DIE;
import static com.girlkun.models.boss.BossStatus.JOIN_MAP;
import static com.girlkun.models.boss.BossStatus.LEAVE_MAP;
import static com.girlkun.models.boss.BossStatus.RESPAWN;
import static com.girlkun.models.boss.BossStatus.REST;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

public class Brolythuong extends Boss {
    boolean xhpnext;
    public Brolythuong(int bossID, BossData bossData) throws Exception {
        super(bossID, bossData);
    }
    @Override
    public void reward(Player plKill) {
        //vật phẩm rơi khi diệt boss nhân bản
        
    }
    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && (Util.isTrue(this.nPoint.tlNeDon, 1000))) {
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
            if (!piercing){
                if ((plAtt.playerSkill.skillSelect.template.id == Skill.ANTOMIC || plAtt.playerSkill.skillSelect.template.id == Skill.KAMEJOKO || plAtt.playerSkill.skillSelect.template.id == Skill.MASENKO)) {
                    this.chat("Xí hụt");
                    damage = 0;
                }
                if (!(plAtt.playerSkill.skillSelect.template.id == Skill.TU_SAT || plAtt.playerSkill.skillSelect.template.id == Skill.MAKANKOSAPPO || plAtt.playerSkill.skillSelect.template.id == Skill.QUA_CAU_KENH_KHI)){
                    if (damage >= this.nPoint.hpMax/100) {
                        damage = this.nPoint.hpMax/100;
                    }
                }
            }
            this.nPoint.subHP(damage);
            if (!xhpnext && this.nPoint.hp > this.nPoint.hpMax*2/3){
                xhpnext = true;
            }
            if (this.nPoint.hp <= this.nPoint.hpMax/2 && xhpnext){
                this.nPoint.hpMax *= 1.5;
                this.nPoint.dame = this.nPoint.dame + this.nPoint.hpMax/50;
                xhpnext = false;
            }
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
                if (this.nPoint.hpMax >= 1_000_000){
                    int hpbroly = (this.nPoint.hpMax/100)*150;
                    if (hpbroly > 18_000_000){
                        hpbroly = 18_000_000;
                    }
                    int damebroly = (this.nPoint.dame/100)*150;
                    if (damebroly > 1_000_000){
                        damebroly = 1_000_000;
                    }
                    BossData superBroly = new BossData(
                    "Super Broly " + (this.id - BossID.BROLY_THUONG),
                    ConstPlayer.XAYDA,
                    new short[]{294, 295, 296, -1, -1, -1},
                    damebroly,
                    new int[]{hpbroly},
                    new int[]{this.zone.map.mapId},
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
                    BossManager.gI().createBoss((int) (this.id - BossID.BROLY_THUONG + BossID.SUPER_BROLY),superBroly);
                } else {
                    int[] idmapbroly = new int[]{5,6,10,11,12,13,19,20,27,28,29,30,31,32,33,34,35,36,37,38};
                    int indexmapxh = Util.nextInt(idmapbroly.length);
                    int hpbroly = this.nPoint.hpMax/10;
                    if (hpbroly < 500){
                        hpbroly = 500;
                    }
                    int damebroly = this.nPoint.dame/10;
                    if (damebroly < 50){
                        damebroly = 50;
                    }
                    BossData brolythuong = new BossData(
                    "Broly " + (this.id - BossID.BROLY_THUONG),
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
                    BossManager.gI().createBoss((int) this.id,brolythuong);
                }
            }
            return damage;
        } else {
            return 0;
        }
    }
    
    private long st;
    @Override
    public void joinMap() {
        super.joinMap();
        
    }

    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
    }
    

}
