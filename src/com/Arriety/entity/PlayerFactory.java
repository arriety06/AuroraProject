/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Arriety.entity;

import com.Arriety.card.Card;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.database.GirlkunDB;
import static com.girlkun.jdbc.daos.GodGK.loadOptionCard;
import com.girlkun.models.clan.Clan;
import com.girlkun.models.clan.ClanMember;
import com.girlkun.models.item.Item;
import com.girlkun.models.item.ItemTime;
import com.girlkun.models.npc.specialnpc.EggLinhThu;
import com.girlkun.models.npc.specialnpc.MabuEgg;
import com.girlkun.models.npc.specialnpc.MagicTree;
import com.girlkun.models.player.*;
import com.girlkun.models.skill.Skill;
import com.girlkun.models.task.TaskMain;
import com.girlkun.server.Manager;
import com.girlkun.server.ServerManager;
import com.girlkun.services.*;
import com.girlkun.utils.SkillUtil;
import com.girlkun.utils.TimeUtil;
import com.girlkun.utils.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.sql.Timestamp;
import java.util.Date;
import org.json.simple.JSONObject;

/**
 *
 * @Stole By Arriety
 */
public class PlayerFactory {

    public static PlayerEntity createEntity(Player player) {
        if (null == player) {
            return null;
        }
        if (player.iDMark.isLoadedAllDataPlayer()) {
            JSONArray dataArray = new JSONArray();

            //data kim lượng
            dataArray.add(Math.min(player.inventory.gold, Inventory.LIMIT_GOLD));
            dataArray.add(player.inventory.gem);
            dataArray.add(player.inventory.ruby);
            dataArray.add(player.inventory.coupon);
            dataArray.add(player.inventory.event);
            String inventory = dataArray.toJSONString();
            dataArray.clear();

            int mapId = -1;
            mapId = player.mapIdBeforeLogout;
            int x = player.location.x;
            int y = player.location.y;
            int hp = player.nPoint.hp;
            int mp = player.nPoint.mp;
            if (player.isDie()) {
                mapId = player.gender + 21;
                x = 300;
                y = 336;
                hp = 1;
                mp = 1;
            } else {
                if (MapService.gI().isMapDoanhTrai(mapId) || MapService.gI().isMapBlackBallWar(mapId)
                        || MapService.gI().isMapBanDoKhoBau(mapId) || MapService.gI().isMapMaBu(mapId)) {
                    mapId = player.gender + 21;
                    x = 300;
                    y = 336;
                }
            }

            //data vị trí
            dataArray.add(mapId);
            dataArray.add(x);
            dataArray.add(y);
            String location = dataArray.toJSONString();
            dataArray.clear();

            //data chỉ số
            dataArray.add(player.nPoint.limitPower);
            dataArray.add(player.nPoint.power);
            dataArray.add(player.nPoint.tiemNang);
            dataArray.add(player.nPoint.stamina);
            dataArray.add(player.nPoint.maxStamina);
            dataArray.add(player.nPoint.hpg);
            dataArray.add(player.nPoint.mpg);
            dataArray.add(player.nPoint.dameg);
            dataArray.add(player.nPoint.defg);
            dataArray.add(player.nPoint.critg);
            dataArray.add(player.inventory.pointNangDong);
            dataArray.add(hp);
            dataArray.add(mp);
            dataArray.add(player.numKillSieuHang);
            dataArray.add(player.rankSieuHang);
            dataArray.add(player.nPoint.hpMax);
            dataArray.add(player.nPoint.mpMax);
            dataArray.add(player.nPoint.dame);
            dataArray.add(player.nPoint.def);
            String point = dataArray.toJSONString();
            dataArray.clear();

            //data đậu thần
            dataArray.add(player.magicTree.level);
            dataArray.add(player.magicTree.currPeas);
            dataArray.add(player.magicTree.isUpgrade ? 1 : 0);
            dataArray.add(player.magicTree.lastTimeHarvest);
            dataArray.add(player.magicTree.lastTimeUpgrade);
            String magicTree = dataArray.toJSONString();
            dataArray.clear();

            //data body
            JSONArray dataItem = new JSONArray();
            for (Item item : player.inventory.itemsBody) {
                JSONArray opt = new JSONArray();
                if (item.isNotNullItem()) {
                    dataItem.add(item.template.id);
                    dataItem.add(item.quantity);
                    JSONArray options = new JSONArray();
                    for (Item.ItemOption io : item.itemOptions) {
                        opt.add(io.optionTemplate.id);
                        opt.add(io.param);
                        options.add(opt.toJSONString());
                        opt.clear();
                    }
                    dataItem.add(options.toJSONString());
                } else {
                    dataItem.add(-1);
                    dataItem.add(0);
                    dataItem.add(opt.toJSONString());
                }
                dataItem.add(item.createTime);
                dataArray.add(dataItem.toJSONString());
                dataItem.clear();
            }
            String itemsBody = dataArray.toJSONString();
            dataArray.clear();

            //data bag
            for (Item item : player.inventory.itemsBag) {
                JSONArray opt = new JSONArray();
                if (item.isNotNullItem()) {
                    dataItem.add(item.template.id);
                    dataItem.add(item.quantity);
                    JSONArray options = new JSONArray();
                    for (Item.ItemOption io : item.itemOptions) {
                        opt.add(io.optionTemplate.id);
                        opt.add(io.param);
                        options.add(opt.toJSONString());
                        opt.clear();
                    }
                    dataItem.add(options.toJSONString());
                } else {
                    dataItem.add(-1);
                    dataItem.add(0);
                    dataItem.add(opt.toJSONString());
                }
                dataItem.add(item.createTime);
                dataArray.add(dataItem.toJSONString());
                dataItem.clear();
            }
            String itemsBag = dataArray.toJSONString();
            dataArray.clear();

            //data box
            for (Item item : player.inventory.itemsBox) {
                JSONArray opt = new JSONArray();
                if (item.isNotNullItem()) {
                    dataItem.add(item.template.id);
                    dataItem.add(item.quantity);
                    JSONArray options = new JSONArray();
                    for (Item.ItemOption io : item.itemOptions) {
                        opt.add(io.optionTemplate.id);
                        opt.add(io.param);
                        options.add(opt.toJSONString());
                        opt.clear();
                    }
                    dataItem.add(options.toJSONString());
                } else {
                    dataItem.add(-1);
                    dataItem.add(0);
                    dataItem.add(opt.toJSONString());
                }
                dataItem.add(item.createTime);
                dataArray.add(dataItem.toJSONString());
                dataItem.clear();
            }
            String itemsBox = dataArray.toJSONString();
            dataArray.clear();

            //data box crack ball
            for (Item item : player.inventory.itemsBoxCrackBall) {
                JSONArray opt = new JSONArray();
                if (item.isNotNullItem()) {
                    dataItem.add(item.template.id);
                    dataItem.add(item.quantity);
                    JSONArray options = new JSONArray();
                    for (Item.ItemOption io : item.itemOptions) {
                        opt.add(io.optionTemplate.id);
                        opt.add(io.param);
                        options.add(opt.toJSONString());
                        opt.clear();
                    }
                    dataItem.add(options.toJSONString());
                } else {
                    dataItem.add(-1);
                    dataItem.add(0);
                    dataItem.add(opt.toJSONString());
                }
                dataItem.add(item.createTime);
                dataArray.add(dataItem.toJSONString());
                dataItem.clear();
            }
            String itemsBoxLuckyRound = dataArray.toJSONString();
            dataArray.clear();

            String dataCard = JSONValue.toJSONString(player.Cards);

            JSONObject achievementObject = new JSONObject();
            achievementObject.put("numPvpWin", player.achievement.numPvpWin);
            achievementObject.put("numSkillChuong", player.achievement.numSkillChuong);
            achievementObject.put("numFly", player.achievement.numFly);
            achievementObject.put("numKillMobFly", player.achievement.numKillMobFly);
            achievementObject.put("numKillNguoiRom", player.achievement.numKillNguoiRom);
            player.achievement.numHourOnline += System.currentTimeMillis() - player.lastTimeLogin;
            achievementObject.put("numHourOnline", player.achievement.numHourOnline);
            achievementObject.put("numGivePea", player.achievement.numGivePea);
            achievementObject.put("numSellItem", player.achievement.numSellItem);
            achievementObject.put("numPayMoney", player.achievement.numPayMoney);
            achievementObject.put("numKillSieuQuai", player.achievement.numKillSieuQuai);
            achievementObject.put("numHoiSinh", player.achievement.numHoiSinh);
            achievementObject.put("numSkillDacBiet", player.achievement.numSkillDacBiet);
            achievementObject.put("numPickGem", player.achievement.numPickGem);
            dataArray.addAll(player.achievement.listReceiveGem);
            achievementObject.put("listReceiveGem", dataArray);
            String achievement = achievementObject.toJSONString();
            dataArray.clear();

            //data bạn bè
            JSONArray dataFE = new JSONArray();
            for (Friend f : player.friends) {
                dataFE.add(f.id);
                dataFE.add(f.name);
                dataFE.add(f.head);
                dataFE.add(f.body);
                dataFE.add(f.leg);
                dataFE.add(f.bag);
                dataFE.add(f.power);
                dataArray.add(dataFE.toJSONString());
                dataFE.clear();
            }
            String friend = dataArray.toJSONString();
            dataArray.clear();

            //data kẻ thù
            for (Friend e : player.enemies) {
                dataFE.add(e.id);
                dataFE.add(e.name);
                dataFE.add(e.head);
                dataFE.add(e.body);
                dataFE.add(e.leg);
                dataFE.add(e.bag);
                dataFE.add(e.power);
                dataArray.add(dataFE.toJSONString());
                dataFE.clear();
            }
            String enemy = dataArray.toJSONString();
            dataArray.clear();

            //data nội tại
            dataArray.add(player.playerIntrinsic.intrinsic.id);
            dataArray.add(player.playerIntrinsic.intrinsic.param1);
            dataArray.add(player.playerIntrinsic.intrinsic.param2);
            dataArray.add(player.playerIntrinsic.countOpen);
            String intrinsic = dataArray.toJSONString();
            dataArray.clear();

            //data item time
            dataArray.add((player.itemTime.isUseBoHuyet ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoHuyet)) : 0));
            dataArray.add((player.itemTime.isUseBoKhi ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeBoKhi)) : 0));
            dataArray.add((player.itemTime.isUseGiapXen ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeGiapXen)) : 0));
            dataArray.add((player.itemTime.isUseCuongNo ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeCuongNo)) : 0));
            dataArray.add((player.itemTime.isUseAnDanh ? (ItemTime.TIME_ITEM - (System.currentTimeMillis() - player.itemTime.lastTimeAnDanh)) : 0));
            dataArray.add((player.itemTime.isOpenPower ? (ItemTime.TIME_OPEN_POWER - (System.currentTimeMillis() - player.itemTime.lastTimeOpenPower)) : 0));
            dataArray.add((player.itemTime.isUseMayDo ? (ItemTime.TIME_MAY_DO - (System.currentTimeMillis() - player.itemTime.lastTimeUseMayDo)) : 0));
            dataArray.add((player.itemTime.isUseMayDo2 ? (ItemTime.TIME_MAY_DO - (System.currentTimeMillis() - player.itemTime.lastTimeUseMayDo2)) : 0));

            dataArray.add((player.itemTime.isEatMeal ? (ItemTime.TIME_EAT_MEAL - (System.currentTimeMillis() - player.itemTime.lastTimeEatMeal)) : 0));
            dataArray.add(player.itemTime.iconMeal);
            dataArray.add((player.itemTime.isUseTDLT ? ((player.itemTime.timeTDLT - (System.currentTimeMillis() - player.itemTime.lastTimeUseTDLT)) / 60 / 1000) : 0));
            String itemTime = dataArray.toJSONString();
            dataArray.clear();

            //data nhiệm vụ
            dataArray.add(player.playerTask.taskMain.id);
            dataArray.add(player.playerTask.taskMain.index);
            dataArray.add(player.playerTask.taskMain.subTasks.get(player.playerTask.taskMain.index).count);
            String task = dataArray.toJSONString();
            dataArray.clear();

            //data nhiệm vụ hàng ngày
            dataArray.add(player.playerTask.sideTask.template != null ? player.playerTask.sideTask.template.id : -1);
            dataArray.add(player.playerTask.sideTask.receivedTime);
            dataArray.add(player.playerTask.sideTask.count);
            dataArray.add(player.playerTask.sideTask.maxCount);
            dataArray.add(player.playerTask.sideTask.leftTask);
            dataArray.add(player.playerTask.sideTask.level);
            String sideTask = dataArray.toJSONString();
            dataArray.clear();

            //data trứng bư
            if (player.mabuEgg != null) {
                dataArray.add(player.mabuEgg.lastTimeCreate);
                dataArray.add(player.mabuEgg.timeDone);
            }
            String mabuEgg = dataArray.toJSONString();
            dataArray.clear();

            //data linh thus
            if (player.egglinhthu != null) {
                dataArray.add(player.egglinhthu.lastTimeCreate);
                dataArray.add(player.egglinhthu.timeDone);
            }
            String EggLinhThu = dataArray.toJSONString();
            dataArray.clear();

            //data bùa
            dataArray.add(player.charms.tdTriTue);
            dataArray.add(player.charms.tdManhMe);
            dataArray.add(player.charms.tdDaTrau);
            dataArray.add(player.charms.tdOaiHung);
            dataArray.add(player.charms.tdBatTu);
            dataArray.add(player.charms.tdDeoDai);
            dataArray.add(player.charms.tdThuHut);
            dataArray.add(player.charms.tdDeTu);
            dataArray.add(player.charms.tdTriTue3);
            dataArray.add(player.charms.tdTriTue4);
            String charm = dataArray.toJSONString();
            dataArray.clear();

            //data skill
            JSONArray dataSkill = new JSONArray();
            for (Skill skill : player.playerSkill.skills) {
                dataSkill.add(skill.template.id);
                dataSkill.add(skill.point);
                dataSkill.add(skill.lastTimeUseThisSkill);
                dataSkill.add(skill.currLevel);
                dataArray.add(dataSkill.toJSONString());
                dataSkill.clear();
            }
            String skills = dataArray.toJSONString();
            dataArray.clear();
            dataArray.clear();

            //data skill shortcut
            for (int skillId : player.playerSkill.skillShortCut) {
                dataArray.add(skillId);
            }
            String skillShortcut = dataArray.toJSONString();
            dataArray.clear();

            String pet = dataArray.toJSONString();
            String petInfo = dataArray.toJSONString();
            String petPoint = dataArray.toJSONString();
            String petBody = dataArray.toJSONString();
            String petSkill = dataArray.toJSONString();

            //data pet
            if (player.pet != null) {
                dataArray.add(player.pet.typePet);
                dataArray.add(player.pet.gender);
                dataArray.add(player.pet.name);
                dataArray.add(player.fusion.typeFusion);
                int timeLeftFusion = (int) (Fusion.TIME_FUSION - (System.currentTimeMillis() - player.fusion.lastTimeFusion));
                dataArray.add(timeLeftFusion < 0 ? 0 : timeLeftFusion);
                dataArray.add(player.pet.status);
                dataArray.add(player.fusion.isBTC2);
                petInfo = dataArray.toJSONString();
                dataArray.clear();

                dataArray.add(player.pet.nPoint.limitPower);
                dataArray.add(player.pet.nPoint.power);
                dataArray.add(player.pet.nPoint.tiemNang);
                dataArray.add(player.pet.nPoint.stamina);
                dataArray.add(player.pet.nPoint.maxStamina);
                dataArray.add(player.pet.nPoint.hpg);
                dataArray.add(player.pet.nPoint.mpg);
                dataArray.add(player.pet.nPoint.dameg);
                dataArray.add(player.pet.nPoint.defg);
                dataArray.add(player.pet.nPoint.critg);
                dataArray.add(player.pet.nPoint.hp);
                dataArray.add(player.pet.nPoint.mp);
                petPoint = dataArray.toJSONString();
                dataArray.clear();

                JSONArray items = new JSONArray();
                JSONArray options = new JSONArray();
                JSONArray opt = new JSONArray();
                for (Item item : player.pet.inventory.itemsBody) {
                    if (item.isNotNullItem()) {
                        dataItem.add(item.template.id);
                        dataItem.add(item.quantity);
                        for (Item.ItemOption io : item.itemOptions) {
                            opt.add(io.optionTemplate.id);
                            opt.add(io.param);
                            options.add(opt.toJSONString());
                            opt.clear();
                        }
                        dataItem.add(options.toJSONString());
                    } else {
                        dataItem.add(-1);
                        dataItem.add(0);
                        dataItem.add(options.toJSONString());
                    }

                    dataItem.add(item.createTime);

                    items.add(dataItem.toJSONString());
                    dataItem.clear();
                    options.clear();
                }
                petBody = items.toJSONString();

                JSONArray petSkills = new JSONArray();
                for (Skill s : player.pet.playerSkill.skills) {
                    JSONArray pskill = new JSONArray();
                    if (s.skillId != -1) {
                        pskill.add(s.template.id);
                        pskill.add(s.point);
                    } else {
                        pskill.add(-1);
                        pskill.add(0);
                    }
                    petSkills.add(pskill.toJSONString());
                }
                petSkill = petSkills.toJSONString();

                dataArray.add(petInfo);
                dataArray.add(petPoint);
                dataArray.add(petBody);
                dataArray.add(petSkill);
                pet = dataArray.toJSONString();
            }
            dataArray.clear();

            //data thưởng ngọc rồng đen
            for (int i = 1; i <= 7; i++) {
                dataArray.add(player.rewardBlackBall.timeOutOfDateReward[i - 1]);
                dataArray.add(player.rewardBlackBall.lastTimeGetReward[i - 1]);
                dataArray.add(dataArray.toJSONString());
                dataArray.clear();
            }
            String dataBlackBall = dataArray.toJSONString();
            dataArray.clear();

            return PlayerEntity.builder()
                    .id(player.id)
                    .accountId(player.getSession().getUserId())
                    .name(player.name)
                    .head(player.head)
                    .gender(player.gender)
                    .haveTennisSpaceShip(player.haveTennisSpaceShip)
                    .clanIdServer1(player.clan != null ? player.clan.id : -1)
                    .clanIdServer2(-1)
                    .dataInventory(inventory)
                    .dataLocation(location)
                    .dataPoint(point)
                    .dataMagicTree(magicTree)
                    .itemsBody(itemsBody)
                    .itemsBag(itemsBag)
                    .itemsBox(itemsBox)
                    .itemsBoxLuckyRound(itemsBoxLuckyRound)
                    .achievement(achievement)
                    .friends(friend)
                    .enemies(enemy)
                    .dataIntrinsic(intrinsic)
                    .dataItemTime(itemTime)
                    .dataTask(task)
                    .dataMabuEgg(mabuEgg)
                    .dataEggLinhThu(EggLinhThu)
                    .pet(pet)
                    .dataBlackBall(dataBlackBall)
                    .datacard(dataCard)
                    .dataSideTask(sideTask)
                    .dataCharm(charm)
                    .skills(skills)
                    .skillsShortcut(skillShortcut)
                    .violate(0)
                    .pointPvp(player.pointPvp)
                    .createTime(System.currentTimeMillis())
                    .updateTime(System.currentTimeMillis())
                    .build();
        } else {
            return null;
        }
    }

    public static Player createPlayer(PlayerEntity entity) {
        int plHp = 200000000;
        int plMp = 200000000;

        JSONValue jv = new JSONValue();
        JSONArray dataArray = null;

        Player player = new Player();

        //base info
        player.id = entity.getId();
        player.name = entity.getName();
        player.head = entity.getHead();
        player.gender = entity.getGender();
        player.haveTennisSpaceShip = entity.isHaveTennisSpaceShip();

        player.violate = entity.getViolate();
        player.pointPvp = entity.getPointPvp();

        player.totalPlayerViolate = 0;
        int clanId = entity.getClanIdServer1();
        if (clanId != -1) {

            try {
                Clan clan = ClanService.gI().getClanById(clanId);
                for (ClanMember cm : clan.getMembers()) {
                    if (cm.id == player.id) {
                        clan.addMemberOnline(player);
                        player.clan = clan;
                        player.clanMember = cm;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //data kim lượng
        dataArray = (JSONArray) jv.parse(entity.getDataInventory());
        player.inventory.gold = Long.parseLong(String.valueOf(dataArray.get(0)));
        player.inventory.gem = Integer.parseInt(String.valueOf(dataArray.get(1)));
        player.inventory.ruby = Integer.parseInt(String.valueOf(dataArray.get(2)));
        if (dataArray.size() >= 4) {
            player.inventory.coupon = Integer.parseInt(String.valueOf(dataArray.get(3)));
        } else {
            player.inventory.coupon = 0;
        }
        if (dataArray.size() >= 5 && false) {
            player.inventory.event = Integer.parseInt(String.valueOf(dataArray.get(4)));
        } else {
            player.inventory.event = 0;
        }
        dataArray.clear();

        // data rada card
        dataArray = (JSONArray) jv.parse(entity.getDatacard());
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject obj = (JSONObject) dataArray.get(i);
            player.Cards.add(new Card(Short.parseShort(obj.get("id").toString()), Byte.parseByte(obj.get("amount").toString()), Byte.parseByte(obj.get("max").toString()), Byte.parseByte(obj.get("level").toString()), loadOptionCard((JSONArray) JSONValue.parse(obj.get("option").toString())), Byte.parseByte(obj.get("used").toString())));
        }
        dataArray.clear();

        //data tọa độ
        try {
            dataArray = (JSONArray) jv.parse(entity.getDataLocation());
            int mapId = Integer.parseInt(String.valueOf(dataArray.get(0)));
            player.location.x = Integer.parseInt(String.valueOf(dataArray.get(1)));
            player.location.y = Integer.parseInt(String.valueOf(dataArray.get(2)));
            player.location.lastTimeplayerMove = System.currentTimeMillis();
            if (MapService.gI().isMapDoanhTrai(mapId) || MapService.gI().isMapBlackBallWar(mapId)
                    || MapService.gI().isMapBanDoKhoBau(mapId) || MapService.gI().isMapMaBu(mapId)) {
                mapId = player.gender + 21;
                player.location.x = 300;
                player.location.y = 336;
            }
            player.zone = MapService.gI().getMapCanJoin(player, mapId, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataArray.clear();

        //data chỉ số
        dataArray = (JSONArray) jv.parse(entity.getDataPoint());
        player.nPoint.limitPower = Byte.parseByte(String.valueOf(dataArray.get(0)));
        player.nPoint.power = Long.parseLong(String.valueOf(dataArray.get(1)));
        player.nPoint.tiemNang = Long.parseLong(String.valueOf(dataArray.get(2)));
        player.nPoint.stamina = Short.parseShort(String.valueOf(dataArray.get(3)));
        player.nPoint.maxStamina = Short.parseShort(String.valueOf(dataArray.get(4)));
        player.nPoint.hpg = Integer.parseInt(String.valueOf(dataArray.get(5)));
        player.nPoint.mpg = Integer.parseInt(String.valueOf(dataArray.get(6)));
        player.nPoint.dameg = Integer.parseInt(String.valueOf(dataArray.get(7)));
        player.nPoint.defg = Integer.parseInt(String.valueOf(dataArray.get(8)));
        player.nPoint.critg = Byte.parseByte(String.valueOf(dataArray.get(9)));
        player.inventory.pointNangDong = Integer.parseInt(String.valueOf(dataArray.get(10))); //** Năng động
        plHp = Integer.parseInt(String.valueOf(dataArray.get(11)));
        plMp = Integer.parseInt(String.valueOf(dataArray.get(12)));
        player.numKillSieuHang = Long.parseLong(String.valueOf(dataArray.get(13)));
        player.rankSieuHang = Long.parseLong(String.valueOf(dataArray.get(14)));
        if (player.rankSieuHang == 0) {
            player.rankSieuHang = ServerManager.gI().getNumPlayer();
        }
        player.nPoint.hpMax = Integer.parseInt(String.valueOf(dataArray.get(15)));
        player.nPoint.mpMax = Integer.parseInt(String.valueOf(dataArray.get(16)));
        player.nPoint.dame = Integer.parseInt(String.valueOf(dataArray.get(17)));
        player.nPoint.def = Integer.parseInt(String.valueOf(dataArray.get(18)));
        dataArray.clear();

        //data đậu thần
        dataArray = (JSONArray) jv.parse(entity.getDataMagicTree());
        byte level = Byte.parseByte(String.valueOf(dataArray.get(0)));
        byte currPea = Byte.parseByte(String.valueOf(dataArray.get(1)));
        boolean isUpgrade = Byte.parseByte(String.valueOf(dataArray.get(2))) == 1;
        long lastTimeHarvest = Long.parseLong(String.valueOf(dataArray.get(3)));
        long lastTimeUpgrade = Long.parseLong(String.valueOf(dataArray.get(4)));
        player.magicTree = new MagicTree(player, level, currPea, lastTimeHarvest, isUpgrade, lastTimeUpgrade);
        dataArray.clear();

        //data phần thưởng sao đen
        dataArray = (JSONArray) jv.parse(entity.getDataBlackBall());
        JSONArray dataBlackBall = null;
        for (int i = 1; i <= 7; i++) {
            dataArray.add(player.rewardBlackBall.timeOutOfDateReward[i - 1]);
            dataArray.add(player.rewardBlackBall.lastTimeGetReward[i - 1]);
            dataArray.add(dataArray.toJSONString());
            dataArray.clear();
        }

        //data body
        dataArray = (JSONArray) jv.parse(entity.getItemsBody());
        for (int i = 0; i < dataArray.size(); i++) {
            Item item = null;
            JSONArray dataItem = (JSONArray) jv.parse(dataArray.get(i).toString());
            short tempId = Short.parseShort(String.valueOf(dataItem.get(0)));
            if (tempId != -1) {
                item = ItemService.gI().createNewItem(tempId, Integer.parseInt(String.valueOf(dataItem.get(1))));
                JSONArray options = (JSONArray) jv.parse(String.valueOf(dataItem.get(2)).replaceAll("\"", ""));
                for (int j = 0; j < options.size(); j++) {
                    JSONArray opt = (JSONArray) jv.parse(String.valueOf(options.get(j)));
                    item.itemOptions.add(new Item.ItemOption(Integer.parseInt(String.valueOf(opt.get(0))),
                            Integer.parseInt(String.valueOf(opt.get(1)))));
                }
                item.createTime = Long.parseLong(String.valueOf(dataItem.get(3)));
                if (ItemService.gI().isOutOfDateTime(item)) {
                    item = ItemService.gI().createItemNull();
                }
            } else {
                item = ItemService.gI().createItemNull();
            }
            player.inventory.itemsBody.add(item);
        }
        if (player.inventory.itemsBody.size() == 10) {
            player.inventory.itemsBody.add(ItemService.gI().createItemNull());
        }
        dataArray.clear();

        //data bag
        dataArray = (JSONArray) jv.parse(entity.getItemsBag());
        for (int i = 0; i < dataArray.size(); i++) {
            Item item = null;
            JSONArray dataItem = (JSONArray) jv.parse(dataArray.get(i).toString());
            short tempId = Short.parseShort(String.valueOf(dataItem.get(0)));
            if (tempId != -1) {
                item = ItemService.gI().createNewItem(tempId, Integer.parseInt(String.valueOf(dataItem.get(1))));
                JSONArray options = (JSONArray) jv.parse(String.valueOf(dataItem.get(2)).replaceAll("\"", ""));
                for (int j = 0; j < options.size(); j++) {
                    JSONArray opt = (JSONArray) jv.parse(String.valueOf(options.get(j)));
                    item.itemOptions.add(new Item.ItemOption(Integer.parseInt(String.valueOf(opt.get(0))),
                            Integer.parseInt(String.valueOf(opt.get(1)))));
                }
                item.createTime = Long.parseLong(String.valueOf(dataItem.get(3)));
                if (ItemService.gI().isOutOfDateTime(item)) {
                    item = ItemService.gI().createItemNull();
                }
            } else {
                item = ItemService.gI().createItemNull();
            }
            player.inventory.itemsBag.add(item);
        }
        dataArray.clear();

        //data box
        dataArray = (JSONArray) jv.parse(entity.getItemsBox());
        for (int i = 0; i < dataArray.size(); i++) {
            Item item = null;
            JSONArray dataItem = (JSONArray) jv.parse(dataArray.get(i).toString());
            short tempId = Short.parseShort(String.valueOf(dataItem.get(0)));
            if (tempId != -1) {
                item = ItemService.gI().createNewItem(tempId, Integer.parseInt(String.valueOf(dataItem.get(1))));
                JSONArray options = (JSONArray) jv.parse(String.valueOf(dataItem.get(2)).replaceAll("\"", ""));
                for (int j = 0; j < options.size(); j++) {
                    JSONArray opt = (JSONArray) jv.parse(String.valueOf(options.get(j)));
                    item.itemOptions.add(new Item.ItemOption(Integer.parseInt(String.valueOf(opt.get(0))),
                            Integer.parseInt(String.valueOf(opt.get(1)))));
                }
                item.createTime = Long.parseLong(String.valueOf(dataItem.get(3)));
                if (ItemService.gI().isOutOfDateTime(item)) {
                    item = ItemService.gI().createItemNull();
                }
            } else {
                item = ItemService.gI().createItemNull();
            }
            player.inventory.itemsBox.add(item);
        }
        dataArray.clear();

        //data box lucky round
        dataArray = (JSONArray) jv.parse(entity.getItemsBoxLuckyRound());
        for (int i = 0; i < dataArray.size(); i++) {
            Item item = null;
            JSONArray dataItem = (JSONArray) jv.parse(dataArray.get(i).toString());
            short tempId = Short.parseShort(String.valueOf(dataItem.get(0)));
            if (tempId != -1) {
                item = ItemService.gI().createNewItem(tempId, Integer.parseInt(String.valueOf(dataItem.get(1))));
                JSONArray options = (JSONArray) jv.parse(String.valueOf(dataItem.get(2)).replaceAll("\"", ""));
                for (int j = 0; j < options.size(); j++) {
                    JSONArray opt = (JSONArray) jv.parse(String.valueOf(options.get(j)));
                    item.itemOptions.add(new Item.ItemOption(Integer.parseInt(String.valueOf(opt.get(0))),
                            Integer.parseInt(String.valueOf(opt.get(1)))));
                }
                player.inventory.itemsBoxCrackBall.add(item);
            }
        }
        dataArray.clear();

        //data friends
        dataArray = (JSONArray) jv.parse(entity.getFriends());
        if (dataArray != null) {
            for (int i = 0; i < dataArray.size(); i++) {
                JSONArray dataFE = (JSONArray) jv.parse(String.valueOf(dataArray.get(i)));
                Friend friend = new Friend();
                friend.id = Integer.parseInt(String.valueOf(dataFE.get(0)));
                friend.name = String.valueOf(dataFE.get(1));
                friend.head = Short.parseShort(String.valueOf(dataFE.get(2)));
                friend.body = Short.parseShort(String.valueOf(dataFE.get(3)));
                friend.leg = Short.parseShort(String.valueOf(dataFE.get(4)));
                friend.bag = Byte.parseByte(String.valueOf(dataFE.get(5)));
                friend.power = Long.parseLong(String.valueOf(dataFE.get(6)));
                player.friends.add(friend);
                dataFE.clear();
            }
            dataArray.clear();
        }

        JSONObject achievementObject = (JSONObject) JSONValue.parse(entity.getAchievement());
        player.achievement.numPvpWin = Integer.parseInt(String.valueOf(achievementObject.get("numPvpWin")));
        player.achievement.numSkillChuong = Integer.parseInt(String.valueOf(achievementObject.get("numSkillChuong")));
        player.achievement.numFly = Integer.parseInt(String.valueOf(achievementObject.get("numFly")));
        player.achievement.numKillMobFly = Integer.parseInt(String.valueOf(achievementObject.get("numKillMobFly")));
        player.achievement.numKillNguoiRom = Integer.parseInt(String.valueOf(achievementObject.get("numKillNguoiRom")));
        player.achievement.numHourOnline = Long.parseLong(String.valueOf(achievementObject.get("numHourOnline")));
        player.achievement.numGivePea = Integer.parseInt(String.valueOf(achievementObject.get("numGivePea")));
        player.achievement.numSellItem = Integer.parseInt(String.valueOf(achievementObject.get("numSellItem")));
        player.achievement.numPayMoney = Integer.parseInt(String.valueOf(achievementObject.get("numPayMoney")));
        player.achievement.numKillSieuQuai = Integer.parseInt(String.valueOf(achievementObject.get("numKillSieuQuai")));
        player.achievement.numHoiSinh = Integer.parseInt(String.valueOf(achievementObject.get("numHoiSinh")));
        player.achievement.numSkillDacBiet = Integer.parseInt(String.valueOf(achievementObject.get("numSkillDacBiet")));
        player.achievement.numPickGem = Integer.parseInt(String.valueOf(achievementObject.get("numPickGem")));

        dataArray = (JSONArray) JSONValue.parse(String.valueOf(achievementObject.get("listReceiveGem")));
        for (Byte i = 0; i < dataArray.size(); i++) {
            player.achievement.listReceiveGem.add(Boolean.parseBoolean(String.valueOf(dataArray.get(i))));
        }
        dataArray.clear();

        //data enemies
        dataArray = (JSONArray) jv.parse(entity.getEnemies());
        if (dataArray != null) {
            for (int i = 0; i < dataArray.size(); i++) {
                JSONArray dataFE = (JSONArray) jv.parse(String.valueOf(dataArray.get(i)));
                Enemy enemy = new Enemy();
                enemy.id = Integer.parseInt(String.valueOf(dataFE.get(0)));
                enemy.name = String.valueOf(dataFE.get(1));
                enemy.head = Short.parseShort(String.valueOf(dataFE.get(2)));
                enemy.body = Short.parseShort(String.valueOf(dataFE.get(3)));
                enemy.leg = Short.parseShort(String.valueOf(dataFE.get(4)));
                enemy.bag = Byte.parseByte(String.valueOf(dataFE.get(5)));
                enemy.power = Long.parseLong(String.valueOf(dataFE.get(6)));
                player.enemies.add(enemy);
                dataFE.clear();
            }
            dataArray.clear();
        }

        //data nội tại
        dataArray = (JSONArray) jv.parse(entity.getDataIntrinsic());
        byte intrinsicId = Byte.parseByte(String.valueOf(dataArray.get(0)));
        player.playerIntrinsic.intrinsic = IntrinsicService.gI().getIntrinsicById(intrinsicId);
        player.playerIntrinsic.intrinsic.param1 = Short.parseShort(String.valueOf(dataArray.get(1)));
        player.playerIntrinsic.intrinsic.param2 = Short.parseShort(String.valueOf(dataArray.get(2)));
        player.playerIntrinsic.countOpen = Byte.parseByte(String.valueOf(dataArray.get(3)));
        dataArray.clear();

        //data item time
        dataArray = (JSONArray) jv.parse(entity.getDataItemTime());
        int timeBoHuyet = Integer.parseInt(String.valueOf(dataArray.get(0)));
        int timeBoKhi = Integer.parseInt(String.valueOf(dataArray.get(1)));
        int timeGiapXen = Integer.parseInt(String.valueOf(dataArray.get(2)));
        int timeCuongNo = Integer.parseInt(String.valueOf(dataArray.get(3)));
        int timeAnDanh = Integer.parseInt(String.valueOf(dataArray.get(4)));
        int timeOpenPower = Integer.parseInt(String.valueOf(dataArray.get(5)));
        int timeMayDo = Integer.parseInt(String.valueOf(dataArray.get(6)));

        int timeMeal = Integer.parseInt(String.valueOf(dataArray.get(7)));
        int iconMeal = Integer.parseInt(String.valueOf(dataArray.get(8)));

        int timeUseTDLT = 0;
        if (dataArray.size() == 10) {
            timeUseTDLT = Integer.parseInt(String.valueOf(dataArray.get(9)));
        }

        player.itemTime.lastTimeBoHuyet = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeBoHuyet);
        player.itemTime.lastTimeBoKhi = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeBoKhi);
        player.itemTime.lastTimeGiapXen = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeGiapXen);
        player.itemTime.lastTimeCuongNo = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeCuongNo);
        player.itemTime.lastTimeAnDanh = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeAnDanh);
        player.itemTime.lastTimeBoHuyet2 = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeBoHuyet);
        player.itemTime.lastTimeBoKhi2 = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeBoKhi);
        player.itemTime.lastTimeGiapXen2 = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeGiapXen);
        player.itemTime.lastTimeCuongNo2 = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeCuongNo);
        player.itemTime.lastTimeAnDanh2 = System.currentTimeMillis() - (ItemTime.TIME_ITEM - timeAnDanh);
        player.itemTime.lastTimeOpenPower = System.currentTimeMillis() - (ItemTime.TIME_OPEN_POWER - timeOpenPower);
        player.itemTime.lastTimeUseMayDo = System.currentTimeMillis() - (ItemTime.TIME_MAY_DO - timeMayDo);

        player.itemTime.lastTimeEatMeal = System.currentTimeMillis() - (ItemTime.TIME_EAT_MEAL - timeMeal);
        player.itemTime.timeTDLT = timeUseTDLT * 60 * 1000;
        player.itemTime.lastTimeUseTDLT = System.currentTimeMillis();

        player.itemTime.iconMeal = iconMeal;
        player.itemTime.isUseBoHuyet = timeBoHuyet != 0;
        player.itemTime.isUseBoKhi = timeBoKhi != 0;
        player.itemTime.isUseGiapXen = timeGiapXen != 0;
        player.itemTime.isUseCuongNo = timeCuongNo != 0;
        player.itemTime.isUseAnDanh = timeAnDanh != 0;
        player.itemTime.isUseBoHuyet2 = timeBoHuyet != 0;
        player.itemTime.isUseBoKhi2 = timeBoKhi != 0;
        player.itemTime.isUseGiapXen2 = timeGiapXen != 0;
        player.itemTime.isUseCuongNo2 = timeCuongNo != 0;
        player.itemTime.isUseAnDanh2 = timeAnDanh != 0;
        player.itemTime.isOpenPower = timeOpenPower != 0;
        player.itemTime.isUseMayDo = timeMayDo != 0;

        player.itemTime.isEatMeal = timeMeal != 0;
        player.itemTime.isUseTDLT = timeUseTDLT != 0;
        dataArray.clear();

        //data nhiệm vụ
        dataArray = (JSONArray) jv.parse(entity.getDataTask());
        TaskMain taskMain = TaskService.gI().getTaskMainById(player, Byte.parseByte(String.valueOf(dataArray.get(0))));
        taskMain.index = Byte.parseByte(String.valueOf(dataArray.get(1)));
        taskMain.subTasks.get(taskMain.index).count = Short.parseShort(String.valueOf(dataArray.get(2)));
        player.playerTask.taskMain = taskMain;
        dataArray.clear();

        //data nhiệm vụ hàng ngày
        dataArray = (JSONArray) jv.parse(entity.getDataSideTask());
        String format = "dd-MM-yyyy";
        long receivedTime = Long.parseLong(String.valueOf(dataArray.get(1)));
        Date date = new Date(receivedTime);
        if (TimeUtil.formatTime(date, format).equals(TimeUtil.formatTime(new Date(), format))) {
            player.playerTask.sideTask.template = TaskService.gI().getSideTaskTemplateById(Integer.parseInt(String.valueOf(dataArray.get(0))));
            player.playerTask.sideTask.count = Integer.parseInt(String.valueOf(dataArray.get(2)));
            player.playerTask.sideTask.maxCount = Integer.parseInt(String.valueOf(dataArray.get(3)));
            player.playerTask.sideTask.leftTask = Integer.parseInt(String.valueOf(dataArray.get(4)));
            player.playerTask.sideTask.level = Integer.parseInt(String.valueOf(dataArray.get(5)));
            player.playerTask.sideTask.receivedTime = receivedTime;
        }

        //data trứng bư
        dataArray = (JSONArray) jv.parse(entity.getDataMabuEgg());
        if (dataArray.size() != 0) {
            player.mabuEgg = new MabuEgg(player, Long.parseLong(String.valueOf(dataArray.get(0))),
                    Long.parseLong(String.valueOf(dataArray.get(1))));
        }
        dataArray.clear();

        //data trứng linh thu
        dataArray = (JSONArray) jv.parse(entity.getDataEggLinhThu());
        if (dataArray.size() != 0) {
            player.egglinhthu = new EggLinhThu(player, Long.parseLong(String.valueOf(dataArray.get(0))),
                    Long.parseLong(String.valueOf(dataArray.get(1))));
        }
        dataArray.clear();

        //data bùa
        dataArray = (JSONArray) jv.parse(entity.getDataCharm());
        player.charms.tdTriTue = Long.parseLong(String.valueOf(dataArray.get(0)));
        player.charms.tdManhMe = Long.parseLong(String.valueOf(dataArray.get(1)));
        player.charms.tdDaTrau = Long.parseLong(String.valueOf(dataArray.get(2)));
        player.charms.tdOaiHung = Long.parseLong(String.valueOf(dataArray.get(3)));
        player.charms.tdBatTu = Long.parseLong(String.valueOf(dataArray.get(4)));
        player.charms.tdDeoDai = Long.parseLong(String.valueOf(dataArray.get(5)));
        player.charms.tdThuHut = Long.parseLong(String.valueOf(dataArray.get(6)));
        player.charms.tdDeTu = Long.parseLong(String.valueOf(dataArray.get(7)));
        player.charms.tdTriTue3 = Long.parseLong(String.valueOf(dataArray.get(8)));
        player.charms.tdTriTue4 = Long.parseLong(String.valueOf(dataArray.get(9)));
        dataArray.clear();

        //data skill
        dataArray = (JSONArray) jv.parse(entity.getSkills());
        for (int i = 0; i < dataArray.size(); i++) {
            JSONArray dataSkill = (JSONArray) jv.parse(String.valueOf(dataArray.get(i)));
            int tempId = Integer.parseInt(String.valueOf(dataSkill.get(0)));
            byte point = Byte.parseByte(String.valueOf(dataSkill.get(1)));
            Skill skill = null;
            if (point != 0) {
                skill = SkillUtil.createSkill(tempId, point);
            } else {
                skill = SkillUtil.createSkillLevel0(tempId);
            }
            skill.lastTimeUseThisSkill = Long.parseLong(String.valueOf(dataSkill.get(2)));
            if (dataSkill.size() > 3) {
                skill.currLevel = Short.parseShort(String.valueOf(dataSkill.get(3)));
            }
            player.playerSkill.skills.add(skill);
        }
        dataArray.clear();

        //data skill shortcut
        dataArray = (JSONArray) jv.parse(entity.getSkillsShortcut());
        for (int i = 0; i < dataArray.size(); i++) {
            player.playerSkill.skillShortCut[i] = Byte.parseByte(String.valueOf(dataArray.get(i)));
        }
        for (int i : player.playerSkill.skillShortCut) {
            if (player.playerSkill.getSkillbyId(i) != null && player.playerSkill.getSkillbyId(i).damage > 0) {
                player.playerSkill.skillSelect = player.playerSkill.getSkillbyId(i);
                break;
            }
        }
        if (player.playerSkill.skillSelect == null) {
            player.playerSkill.skillSelect = player.playerSkill.getSkillbyId(player.gender == ConstPlayer.TRAI_DAT
                    ? Skill.DRAGON : (player.gender == ConstPlayer.NAMEC ? Skill.DEMON : Skill.GALICK));
        }
        dataArray.clear();

        //data pet
        JSONArray petData = (JSONArray) jv.parse(entity.getPet());
        if (!petData.isEmpty()) {
            dataArray = (JSONArray) jv.parse(String.valueOf(petData.get(0)));
            Pet pet = new Pet(player);
            pet.id = -player.id;
            pet.typePet = Byte.parseByte(String.valueOf(dataArray.get(0)));
            pet.gender = Byte.parseByte(String.valueOf(dataArray.get(1)));
            pet.name = String.valueOf(dataArray.get(2));
            player.fusion.typeFusion = Byte.parseByte(String.valueOf(dataArray.get(3)));
            player.fusion.lastTimeFusion = System.currentTimeMillis()
                    - (Fusion.TIME_FUSION - Integer.parseInt(String.valueOf(dataArray.get(4))));
            pet.status = Byte.parseByte(String.valueOf(dataArray.get(5)));
            try {
                player.fusion.isBTC2 = Boolean.getBoolean(String.valueOf(dataArray.get(6)));
            } catch (Exception e) {
                e.printStackTrace();
                //                    throw new RuntimeException(e);
            }
            //data chỉ số
            dataArray = (JSONArray) jv.parse(String.valueOf(petData.get(1)));
            pet.nPoint.limitPower = Byte.parseByte(String.valueOf(dataArray.get(0)));
            pet.nPoint.power = Long.parseLong(String.valueOf(dataArray.get(1)));
            pet.nPoint.tiemNang = Long.parseLong(String.valueOf(dataArray.get(2)));
            pet.nPoint.stamina = Short.parseShort(String.valueOf(dataArray.get(3)));
            pet.nPoint.maxStamina = Short.parseShort(String.valueOf(dataArray.get(4)));
            pet.nPoint.hpg = Integer.parseInt(String.valueOf(dataArray.get(5)));
            pet.nPoint.mpg = Integer.parseInt(String.valueOf(dataArray.get(6)));
            pet.nPoint.dameg = Integer.parseInt(String.valueOf(dataArray.get(7)));
            pet.nPoint.defg = Integer.parseInt(String.valueOf(dataArray.get(8)));
            pet.nPoint.critg = Integer.parseInt(String.valueOf(dataArray.get(9)));
            int hp = Integer.parseInt(String.valueOf(dataArray.get(10)));
            int mp = Integer.parseInt(String.valueOf(dataArray.get(11)));

            //data body
            dataArray = (JSONArray) jv.parse(String.valueOf(petData.get(2)));
            for (int i = 0; i < dataArray.size(); i++) {
                Item item = null;
                JSONArray dataItem = (JSONArray) jv.parse(String.valueOf(dataArray.get(i)));
                short tempId = Short.parseShort(String.valueOf(dataItem.get(0)));
                if (tempId != -1) {
                    item = ItemService.gI().createNewItem(tempId, Integer.parseInt(String.valueOf(dataItem.get(1))));
                    JSONArray options = (JSONArray) jv.parse(String.valueOf(dataItem.get(2)).replaceAll("\"", ""));
                    for (int j = 0; j < options.size(); j++) {
                        JSONArray opt = (JSONArray) jv.parse(String.valueOf(options.get(j)));
                        item.itemOptions.add(new Item.ItemOption(Integer.parseInt(String.valueOf(opt.get(0))),
                                Integer.parseInt(String.valueOf(opt.get(1)))));
                    }
                    item.createTime = Long.parseLong(String.valueOf(dataItem.get(3)));
                    if (ItemService.gI().isOutOfDateTime(item)) {
                        item = ItemService.gI().createItemNull();
                    }
                } else {
                    item = ItemService.gI().createItemNull();
                    ;
                }
                pet.inventory.itemsBody.add(item);
            }

            //data skills
            dataArray = (JSONArray) jv.parse(String.valueOf(petData.get(3)));
            for (int i = 0; i < dataArray.size(); i++) {
                JSONArray skillTemp = (JSONArray) jv.parse(String.valueOf(dataArray.get(i)));
                int tempId = Integer.parseInt(String.valueOf(skillTemp.get(0)));
                byte point = Byte.parseByte(String.valueOf(skillTemp.get(1)));
                Skill skill = null;
                if (point != 0) {
                    skill = SkillUtil.createSkill(tempId, point);
                } else {
                    skill = SkillUtil.createSkillLevel0(tempId);
                }
                switch (skill.template.id) {
                    case Skill.KAMEJOKO:
                    case Skill.MASENKO:
                    case Skill.ANTOMIC:
                        skill.coolDown = 1000;
                        break;
                }
                pet.playerSkill.skills.add(skill);
            }
            if (pet.playerSkill.skills.size() < 5) {
                pet.playerSkill.skills.add(4, SkillUtil.createSkillLevel0(-1));
            }
            pet.nPoint.hp = hp;
            pet.nPoint.mp = mp;
            player.pet = pet;
        }

        player.nPoint.hp = plHp;
        player.nPoint.mp = plMp;
        player.iDMark.setLoadedAllDataPlayer(true);

        return player;
    }
}
