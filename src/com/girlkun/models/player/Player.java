package com.girlkun.models.player;

import TaskBoMong.AchievementPlayer;
import com.Arriety.card.Card;
import com.girlkun.models.map.MapMaBu.MapMaBu;
import com.girlkun.models.skill.PlayerSkill;

import java.util.List;

import com.girlkun.models.clan.Clan;
import com.girlkun.models.intrinsic.IntrinsicPlayer;
import com.girlkun.models.item.Item;
import com.girlkun.models.item.ItemTime;
import com.girlkun.models.npc.specialnpc.MagicTree;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.consts.ConstTask;
import com.girlkun.models.npc.specialnpc.MabuEgg;
import com.girlkun.models.mob.MobMe;
import com.girlkun.data.DataGame;
import com.girlkun.models.clan.ClanMember;
import com.girlkun.models.map.TrapMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.map.bdkb.BanDoKhoBau;
import com.girlkun.models.map.bdkb.BanDoKhoBauService;
import com.girlkun.models.map.blackball.BlackBallWar;
import com.girlkun.models.map.doanhtrai.DoanhTraiService;
import com.girlkun.models.map.mappvp.mapPvp;
import com.girlkun.models.matches.IPVP;
import com.girlkun.models.matches.TYPE_LOSE_PVP;
import com.girlkun.models.npc.specialnpc.EggLinhThu;
import com.girlkun.models.skill.Skill;
import com.girlkun.network.session.ISession;
import com.girlkun.services.Service;
import com.girlkun.models.task.TaskPlayer;
import com.girlkun.network.io.Message;
import com.girlkun.server.Client;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.FriendAndEnemyService;
import com.girlkun.services.MapService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.TaskService;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.services.func.ChonAiDay;
import com.girlkun.services.func.CombineNew;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

import java.util.ArrayList;

public class Player {

    public long lastTimeLogin;

    private ISession session;

    public byte countBDKB;
    public boolean firstJoinBDKB;
    public long lastimeJoinBDKB;
    public int goldChallenge;
    public boolean receivedWoodChest;
    public int levelWoodChest;
    public List<String> textRuongGo = new ArrayList<>();

    public int bdkb_countPerDay;
    public long bdkb_lastTimeJoin;
    public boolean bdkb_isJoinBdkb;
    public boolean isLinhThuFollow;
    public boolean beforeDispose;

    public boolean isPet;
    public boolean isNewPet;
    public boolean isBoss;
    public boolean isReferee;

    public long lasttimebufvttl;
    public long lasttimebufvtsl;
    public boolean buffvttt = false;
    public boolean buffvtpt = false;
    public int NguHanhSonPoint = 0;
    public SkillSpecial skillSpecial;
    public Player player;
    public IPVP pvp;
    public int pointPvp;
    public byte maxTime = 30;
    public byte type = 0;

    public int mapIdBeforeLogout;
    public List<Zone> mapBlackBall;
    public List<Zone> mapMaBu;

    public long rankSieuHang;
    public long numKillSieuHang;

    public Zone zone;
    public Zone mapBeforeCapsule;
    public List<Zone> mapCapsule;
    public Pet pet;
    public NewPet newpet;
    public MobMe mobMe;
    public Location location;
    public SetClothes setClothes;
    public EffectSkill effectSkill;
    public MabuEgg mabuEgg;
    public EggLinhThu egglinhthu;
    public TaskPlayer playerTask;
    public ItemTime itemTime;
    public Fusion fusion;
    public MagicTree magicTree;
    public IntrinsicPlayer playerIntrinsic;
    public Inventory inventory;
    public PlayerSkill playerSkill;
    public CombineNew combineNew;
    public IDMark iDMark;
    public Charms charms;
    public EffectSkin effectSkin;
    public Gift gift;
    public NPoint nPoint;
    public RewardBlackBall rewardBlackBall;
    public EffectFlagBag effectFlagBag;
    public FightMabu fightMabu;

    public AchievementPlayer achievement;

    public Clan clan;
    public ClanMember clanMember;

    public List<Friend> friends;
    public List<Enemy> enemies;

    public long id;
    public String name;
    public byte gender;
    public boolean isNewMember = true;
    public short head;

    public byte typePk;

    public byte cFlag;

    public boolean haveTennisSpaceShip;

    public boolean justRevived;
    public long lastTimeRevived;

    public int violate;
    public byte totalPlayerViolate;
    public long timeChangeZone;
    public long lastTimeUseOption;

    public short idNRNM = -1;
    public short idGo = -1;
    public long lastTimePickNRNM;
    public int goldNormar;
    public int goldVIP;
    public long lastTimeWin;
    public boolean isWin;

    public boolean haveDuongTang;
    public boolean haveRuaCon;

    public List<Card> Cards = new ArrayList<>();
    public short idAura = -1;
    public List<Integer> idEffChar = new ArrayList<>();

    public Player() {
        lastTimeUseOption = System.currentTimeMillis();
        location = new Location();
        nPoint = new NPoint(this);
        inventory = new Inventory();
        playerSkill = new PlayerSkill(this);
        setClothes = new SetClothes(this);
        effectSkill = new EffectSkill(this);
        fusion = new Fusion(this);
        playerIntrinsic = new IntrinsicPlayer();
        rewardBlackBall = new RewardBlackBall(this);
        effectFlagBag = new EffectFlagBag();
        fightMabu = new FightMabu(this);
        //----------------------------------------------------------------------
        iDMark = new IDMark();
        combineNew = new CombineNew();
        playerTask = new TaskPlayer();
        friends = new ArrayList<>();
        enemies = new ArrayList<>();
        itemTime = new ItemTime(this);
        charms = new Charms();
        gift = new Gift(this);
        effectSkin = new EffectSkin(this);
        achievement = new AchievementPlayer(this);
        skillSpecial = new SkillSpecial(this);
//        this.typePk = 5; //trạng thái pk toàn server
    }

    //--------------------------------------------------------------------------
    public boolean isDie() {
        if (this.nPoint != null) {
            return this.nPoint.hp <= 0;
        }
        return true;
    }

    //--------------------------------------------------------------------------
    public void setSession(ISession session) {
        this.session = session;
    }

    public void sendMessage(Message msg) {
        if (this.session != null) {
            session.sendMessage(msg);
        }
    }

    public ISession getSession() {
        return this.session;
    }

    public boolean isPl() {
        return !isPet && !isBoss && !isNewPet && !isReferee;
    }

    public void update() {
        if (!this.beforeDispose) {
            try {
                if (!iDMark.isBan()) {

                    if (nPoint != null) {
                        nPoint.update();
                    }
                    if (fusion != null) {
                        fusion.update();
                    }
                    if (effectSkin != null) {
                        effectSkill.update();
                    }
                    if (mobMe != null) {
                        mobMe.update();
                    }
                    if (effectSkin != null) {
                        effectSkin.update();
                    }
                    if (pet != null) {
                        pet.update();
                    }
                    if (newpet != null) {
                        newpet.update();
                    }
                    if (magicTree != null) {
                        magicTree.update();
                    }
                    if (itemTime != null) {
                        itemTime.update();
                    }
                    if (this != null && this.nPoint != null && this.isPl()) {
                        long power = this.nPoint.power;
                        if (power > 20000 && power < 400000000000L) {
                            if (!idEffChar.contains(58)) {
                                Service.gI().addEffectChar(this, 58, 1, -1, -1, 0);
                            }
                        } else {
                            if (idEffChar.contains(58)) {
                                Service.gI().removeEff(this, 58);
                                idEffChar.removeIf(id -> id == 58);
                            }
                        }
                    }
                    if (this.isPl()) {
                        BlackBallWar.gI().update(this);
                        MapMaBu.gI().update(this);
//                        mapPvp.gI().update(this);
                    }
                    if (this.iDMark != null && !isBoss && this.iDMark.isGotoFuture() && Util.canDoWithTime(this.iDMark.getLastTimeGoToFuture(), 6000)) {
                        ChangeMapService.gI().changeMapBySpaceShip(this, 102, -1, Util.nextInt(60, 200));
                        this.iDMark.setGotoFuture(false);
                    }
                    if (this.iDMark != null && this.iDMark.isGoToBDKB() && Util.canDoWithTime(this.iDMark.getLastTimeGoToBDKB(), 6000)) {
                        ChangeMapService.gI().changeMapBySpaceShip(this, 135, -1, 35);
                        this.iDMark.setGoToBDKB(false);
                    }
                    if (this.zone != null) {
                        TrapMap trap = this.zone.isInTrap(this);
                        if (trap != null) {
                            trap.doPlayer(this);
                        }
                    }
                    if (location != null && location.lastTimeplayerMove < System.currentTimeMillis() - 30 * 60 * 1000) {
                        Client.gI().kickSession(getSession());
                    }
                } else {
                    if (Util.canDoWithTime(iDMark.getLastTimeBan(), 5000)) {
                        Client.gI().kickSession(session);
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();
                Logger.logException(Player.class, e, "Lỗi tại player: " + this.name);
            }
        }
    }
    //--------------------------------------------------------------------------
    /*
     * {380, 381, 382}: ht lưỡng long nhất thể xayda trái đất
     * {383, 384, 385}: ht porata xayda trái đất
     * {391, 392, 393}: ht namếc
     * {870, 871, 872}: ht c2 trái đất
     * {873, 874, 875}: ht c2 namếc
     * {867, 878, 869}: ht c2 xayda
     */
    private static final short[][] idOutfitFusion = {
        {380, 381, 382}, {870, 871, 872}, {391, 392, 393},
        {870, 871, 872}, {873, 874, 875}, {867, 868, 869},
        {2024, 2025, 2026}, {2027, 2028, 2029}, {2030, 2031, 2032},};

    public byte getAura() {
        if (this.inventory.itemsBody.isEmpty() || this.inventory.itemsBody.size() < 10) {
            return -1;
        }
        Item item = this.inventory.itemsBody.get(5);
        if (!item.isNotNullItem()) {
            return -1;
        }
        if (item.template.id == 1106) {
            return 0;
        } else if (item.template.id == 422) {
            return 11;
        } else if (item.template.id == 2035) {
            return 15;
        } else if (item.template.id == 2092) {
            return 11;
        } else {
            return -1;
        }

    }

    public byte getEffFront() {
        if (this.inventory.itemsBody.isEmpty() || this.inventory.itemsBody.size() < 10) {
            return -1;
        }
        int[] levels = new int[5];
        Item.ItemOption[] options = new Item.ItemOption[5];

        Item[] items = new Item[]{
            this.inventory.itemsBody.get(0),
            this.inventory.itemsBody.get(1),
            this.inventory.itemsBody.get(2),
            this.inventory.itemsBody.get(3),
            this.inventory.itemsBody.get(4)
        };
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            for (Item.ItemOption io : item.itemOptions) {
                if (io.optionTemplate.id == 72) {
                    levels[i] = io.param;
                    options[i] = io;
                    break;
                }
            }
        }
        int minLevel = Integer.MAX_VALUE;
        int count = 0;
        for (int level : levels) {
            if (level >= 4 && level <= 8) {
                minLevel = Math.min(minLevel, level);
                count++;
            }
        }
        if (count == 5) {
//            System.out.println(" level " + minLevel);
            return (byte) minLevel;
        } else {
            return -1;
        }
    }

    public short getHead() {
        if (nPoint != null && effectSkill != null) {
            if (nPoint.isHoaBang) {
                return 1210;
            }
            if (nPoint.IsBiHoaDa) {
                return 454;
            }
            if (effectSkill.isMaPhong3) {
                return 1221;
            }
            if (effectSkill != null && effectSkill.isMonkey) {
                return (short) ConstPlayer.HEADMONKEY[effectSkill.levelMonkey - 1];
            } else if (effectSkill != null && effectSkill.isSocola) {
                return 412;
            } else if (fusion != null && fusion.typeFusion != ConstPlayer.NON_FUSION) {
                if (fusion.typeFusion == ConstPlayer.LUONG_LONG_NHAT_THE) {
                    return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 0][0];
                } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA) {
                    return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 1][0];
                } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA2) {
                    return idOutfitFusion[3 + this.gender][0];
                } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA3) {
                    return idOutfitFusion[6 + this.gender][0];
                }
            } else if (inventory != null && inventory.itemsBody.get(5).isNotNullItem()) {
                int head = inventory.itemsBody.get(5).template.head;
                if (head != -1) {
                    return (short) head;
                }
            }
        }
        return this.head;
    }

    public short getBody() {
        if (nPoint != null && effectSkill != null) {
            if (nPoint.isHoaBang) {
                return 1211;
            }
            if (nPoint.IsBiHoaDa) {
                return 455;
            }
            if (effectSkill.isMaPhong3) {
                return 1222;
            }
            if (effectSkill != null && effectSkill.isMonkey) {
                return 193;
            } else if (effectSkill != null && effectSkill.isSocola) {
                return 413;
            } else if (fusion != null && fusion.typeFusion != ConstPlayer.NON_FUSION) {
                if (fusion.typeFusion == ConstPlayer.LUONG_LONG_NHAT_THE) {
                    return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 0][1];
                } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA) {
                    return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 1][1];
                } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA2) {
                    return idOutfitFusion[3 + this.gender][1];
                } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA3) {
                    return idOutfitFusion[6 + this.gender][1];
                }
            } else if (inventory != null && inventory.itemsBody.get(5).isNotNullItem()) {
                int body = inventory.itemsBody.get(5).template.body;
                if (body != -1) {
                    return (short) body;
                }
            }
            if (inventory != null && inventory.itemsBody.get(0).isNotNullItem()) {
                return inventory.itemsBody.get(0).template.part;
            }
        }
        return (short) (gender == ConstPlayer.NAMEC ? 59 : 57);
    }

    public short getLeg() {
        if (nPoint != null && effectSkill != null) {
            if (nPoint.isHoaBang) {
                return 1212;
            }
            if (nPoint.IsBiHoaDa) {
                return 456;
            }
            if (effectSkill.isMaPhong3) {
                return 1223;
            }
            if (effectSkill != null && effectSkill.isMonkey) {
                return 194;
            } else if (effectSkill != null && effectSkill.isSocola) {
                return 414;
            } else if (fusion != null && fusion.typeFusion != ConstPlayer.NON_FUSION) {
                if (fusion.typeFusion == ConstPlayer.LUONG_LONG_NHAT_THE) {
                    return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 0][2];
                } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA) {
                    return idOutfitFusion[this.gender == ConstPlayer.NAMEC ? 2 : 1][2];
                } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA2) {
                    return idOutfitFusion[3 + this.gender][2];
                } else if (fusion.typeFusion == ConstPlayer.HOP_THE_PORATA3) {
                    return idOutfitFusion[6 + this.gender][2];
                }
            } else if (inventory != null && inventory.itemsBody.get(5).isNotNullItem()) {
                int leg = inventory.itemsBody.get(5).template.leg;
                if (leg != -1) {
                    return (short) leg;
                }
            }
            if (inventory != null && inventory.itemsBody.get(1).isNotNullItem()) {
                return inventory.itemsBody.get(1).template.part;
            }
        }
        return (short) (gender == 1 ? 60 : 58);
    }

    public short getFlagBag() {
        if (this != null && this.iDMark != null && this.inventory != null && this.inventory.itemsBody != null) {
            if (this.iDMark.isHoldBlackBall()) {
                return 31;
            } else if (this.idNRNM >= 353 && this.idNRNM <= 359) {
                return 30;
            }
            if (this.inventory.itemsBody.size() == 11) { // 11 type flag bag
                if (this.inventory.itemsBody.get(8).isNotNullItem()) {
                    return this.inventory.itemsBody.get(8).template.part;
                }
            }
            if (TaskService.gI().getIdTask(this) == ConstTask.TASK_3_2) {
                return 28;
            }
            if (this.clan != null) {
                return (short) this.clan.imgId;
            }
        }
        return -1;
    }

    public short getMount() {
        if (this.inventory.itemsBody.isEmpty() || this.inventory.itemsBody.size() < 10) {
            return -1;
        }
        Item item = this.inventory.itemsBody.get(9);
        if (!item.isNotNullItem()) {
            return -1;
        }
        if (item.template.type == 24) {
            if (item.template.gender == 3 || item.template.gender == this.gender) {
                return item.template.id;
            } else {
                return -1;
            }
        } else {
            if (item.template.id < 500) {
                return item.template.id;
            } else {
                return (short) DataGame.MAP_MOUNT_NUM.get(String.valueOf(item.template.id));
            }
        }

    }

    //--------------------------------------------------------------------------
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            boolean isSkillChuong = false;
            if (plAtt != null) {
                switch (plAtt.playerSkill.skillSelect.template.id) {
                    case Skill.KAMEJOKO:
                    case Skill.MASENKO:
                    case Skill.ANTOMIC:
                        if (this.nPoint.voHieuChuong > 0) {
                            PlayerService.gI().hoiPhuc(this, 0, damage * this.nPoint.voHieuChuong / 100);
                            return 0;
                        }
                }
            }
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 100)) {
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            if (isMobAttack && this.charms.tdBatTu > System.currentTimeMillis() && damage >= this.nPoint.hp) {
                damage = this.nPoint.hp - 1;
            }
            if (plAtt != null) {
                if (isSkillChuong && plAtt.nPoint.multicationChuong > 0 && Util.canDoWithTime(plAtt.nPoint.lastTimeMultiChuong, PlayerSkill.TIME_MUTIL_CHUONG)) {
                    damage *= plAtt.nPoint.multicationChuong;
                    plAtt.nPoint.lastTimeMultiChuong = System.currentTimeMillis();
                }
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                if (plAtt != null && this.zone.map.mapId == 129) {
                    plAtt.pointPvp++;
                }
                setDie(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }

    protected void setDie(Player plAtt) {
        //xóa phù
        if (this.effectSkin.xHPKI > 1) {
            this.effectSkin.xHPKI = 1;
            Service.getInstance().point(this);
        }
        //xóa tụ skill đặc biệt
        this.playerSkill.prepareQCKK = false;
        this.playerSkill.prepareLaze = false;
        this.playerSkill.prepareTuSat = false;
        //xóa hiệu ứng skill
        this.effectSkill.removeSkillEffectWhenDie();
        //
        nPoint.setHp(0);
        nPoint.setMp(0);
        //xóa trứng
        if (this.mobMe != null) {
            this.mobMe.mobMeDie();
        }
        Service.getInstance().charDie(this);
        //add kẻ thù
        if (!this.isPet && !this.isNewPet && !this.isBoss && plAtt != null && !plAtt.isPet && !plAtt.isNewPet && !plAtt.isBoss) {
            if (!plAtt.itemTime.isUseAnDanh) {
                FriendAndEnemyService.gI().addEnemy(this, plAtt);
            }
            if (this.isPl() && plAtt != null && plAtt.isPl()) {
                plAtt.achievement.plusCount(3);
            }
        }
        //kết thúc pk
        if (this.pvp != null) {
            this.pvp.lose(this, TYPE_LOSE_PVP.DEAD);
        }
        if (MapService.gI().ismappvphonchien(this.zone.map.mapId) && MapService.gI().ismappvphonchien(plAtt.zone.map.mapId) && !this.isPet && !this.isNewPet && !this.isBoss && plAtt != null && !plAtt.isPet && !plAtt.isNewPet && !plAtt.isBoss) {
            this.inventory.gold -= 200_000_000;
            plAtt.inventory.gold += 200_000_000;
            Service.gI().sendMoney(this);
            Service.gI().sendMoney(plAtt);
            Service.getInstance().sendThongBao(this, "Mất 200tr nha con, ngu chưa!!");
            Service.getInstance().sendThongBao(plAtt, "Ngol Ngol, húp được 200tr");
        }
        if (this.nPoint.power >= 1_000_000) {
            long powersub = this.nPoint.power / 10000;
            nPoint.powerSub(powersub);
        }
        BlackBallWar.gI().dropBlackBall(this);

    }

    //--------------------------------------------------------------------------
    public void setClanMember() {
        if (this.clanMember != null) {
            this.clanMember.powerPoint = this.nPoint.power;
            this.clanMember.head = this.getHead();
            this.clanMember.body = this.getBody();
            this.clanMember.leg = this.getLeg();
        }
    }

    public boolean isAdmin() {
        return this.session.isAdmin();
    }

    public void setJustRevivaled() {
        this.justRevived = true;
        this.lastTimeRevived = System.currentTimeMillis();
    }

    public void preparedToDispose() {

    }

    public void dispose() {
        if (pet != null) {
            pet.dispose();
            pet = null;
        }
        if (newpet != null) {
            newpet.dispose();
            newpet = null;
        }
        if (mapBlackBall != null) {
            mapBlackBall.clear();
            mapBlackBall = null;
        }
        zone = null;
        mapBeforeCapsule = null;
        if (mapMaBu != null) {
            mapMaBu.clear();
            mapMaBu = null;
        }
        zone = null;
        mapBeforeCapsule = null;
        if (mapCapsule != null) {
            mapCapsule.clear();
            mapCapsule = null;
        }
        if (mobMe != null) {
            mobMe.dispose();
            mobMe = null;
        }
        location = null;
        if (setClothes != null) {
            setClothes.dispose();
            setClothes = null;
        }
        if (effectSkill != null) {
            effectSkill.dispose();
            effectSkill = null;
        }
        if (skillSpecial != null) {
            skillSpecial.dispose();
            skillSpecial = null;
        }
        if (mabuEgg != null) {
            mabuEgg.dispose();
            mabuEgg = null;
        }
        if (egglinhthu != null) {
            egglinhthu.dispose();
            egglinhthu = null;
        }
        if (playerTask != null) {
            playerTask.dispose();
            playerTask = null;
        }
        if (itemTime != null) {
            itemTime.dispose();
            itemTime = null;
        }
        if (fusion != null) {
            fusion.dispose();
            fusion = null;
        }
        if (magicTree != null) {
            magicTree.dispose();
            magicTree = null;
        }
        if (playerIntrinsic != null) {
            playerIntrinsic.dispose();
            playerIntrinsic = null;
        }
        if (inventory != null) {
            inventory.dispose();
            inventory = null;
        }
        if (playerSkill != null) {
            playerSkill.dispose();
            playerSkill = null;
        }
        if (combineNew != null) {
            combineNew.dispose();
            combineNew = null;
        }
        if (iDMark != null) {
            iDMark.dispose();
            iDMark = null;
        }
        if (charms != null) {
            charms.dispose();
            charms = null;
        }
        if (effectSkin != null) {
            effectSkin.dispose();
            effectSkin = null;
        }
        if (gift != null) {
            gift.dispose();
            gift = null;
        }
        if (nPoint != null) {
            nPoint.dispose();
            nPoint = null;
        }
        if (rewardBlackBall != null) {
            rewardBlackBall.dispose();
            rewardBlackBall = null;
        }
        if (effectFlagBag != null) {
            effectFlagBag.dispose();
            effectFlagBag = null;
        }
        if (pvp != null) {
            pvp.dispose();
            pvp = null;
        }
        effectFlagBag = null;
        clan = null;
        clanMember = null;
        friends = null;
        enemies = null;
        session = null;
        name = null;
    }

    public String percentGold(int type) {
        try {
            if (type == 0) {
                double percent = ((double) this.goldNormar / ChonAiDay.gI().goldNormar) * 100;
                return String.valueOf(Math.ceil(percent));
            } else if (type == 1) {
                double percent = ((double) this.goldVIP / ChonAiDay.gI().goldVip) * 100;
                return String.valueOf(Math.ceil(percent));
            }
        } catch (ArithmeticException e) {
            e.printStackTrace();
            return "0";
        }
        return "0";
    }
}
//nplayer
