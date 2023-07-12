package com.Arriety.repositorys;

import com.Arriety.entity.PlayerEntity;
import com.Arriety.entity.PlayerEntityColumName;
import com.girlkun.database.GirlkunDB;
import com.girlkun.result.GirlkunResultSet;
import com.girlkun.server.Manager;

import java.awt.*;
import java.sql.Timestamp;

public class PlayerRepository {
    public PlayerRepository() {

    }

    public PlayerEntity findPlayerWithAccountId(int accountId) {
        try {
            GirlkunResultSet rs = GirlkunDB.executeQuery("select * from player where account_id = ? limit 1", accountId);
            if (!rs.first()) {
                return null;
            }

            int id = rs.getInt(PlayerEntityColumName.ID);
            String name = rs.getString(PlayerEntityColumName.NAME);
            short head = rs.getShort(PlayerEntityColumName.HEAD);
            byte gender = rs.getByte(PlayerEntityColumName.GENDER);
            boolean haveTennisSpaceShip = rs.getBoolean(PlayerEntityColumName.HAVE_TENNIS_SPACE_SHIP);
            int clanIdServer1 = rs.getInt(PlayerEntityColumName.CLAN_ID_SERVER_1);
            int clanIdServer2 = rs.getInt(PlayerEntityColumName.CLAN_ID_SERVER_2);
            String dataInventory = rs.getString(PlayerEntityColumName.DATA_INVENTORY);
            String dataLocation = rs.getString(PlayerEntityColumName.DATA_LOCATION);
            String dataPoint = rs.getString(PlayerEntityColumName.DATA_POINT);
            String dataMagicTree = rs.getString(PlayerEntityColumName.DATA_MAGIC_TREE);
            String itemsBody = rs.getString(PlayerEntityColumName.ITEMS_BODY);
            String itemsBag = rs.getString(PlayerEntityColumName.ITEMS_BAG);
            String itemsBox = rs.getString(PlayerEntityColumName.ITEMS_BOX);
            String itemsBoxLuckyRound = rs.getString(PlayerEntityColumName.ITEMS_BOX_LUCKY_ROUND);
            String friends = rs.getString(PlayerEntityColumName.FRIENDS);
            String enemies = rs.getString(PlayerEntityColumName.ENEMIES);
            String dataIntrinsic = rs.getString(PlayerEntityColumName.DATA_INTRINSIC);
            String dataItemTime = rs.getString(PlayerEntityColumName.DATA_ITEM_TIME);
            String dataTask = rs.getString(PlayerEntityColumName.DATA_TASK);
            String dataMabuEgg = rs.getString(PlayerEntityColumName.DATA_MANU_EGG);
            String dataEggLinhThu = rs.getString(PlayerEntityColumName.DATA_EGG_LINHTHU);
            String dataCharm = rs.getString(PlayerEntityColumName.DATA_CHARM);
            String skills = rs.getString(PlayerEntityColumName.SKILLS);
            String skillsShortcut = rs.getString(PlayerEntityColumName.SKILLS_SHORTCUT);
            String pet = rs.getString(PlayerEntityColumName.PET);
            String dataBlackBall = rs.getString(PlayerEntityColumName.DATA_BLACK_BALL);
            String dataSideTask = rs.getString(PlayerEntityColumName.DATA_SIDE_TASK);
            String createTime = rs.getString(PlayerEntityColumName.CREATE_TIME);
            int violate = rs.getInt(PlayerEntityColumName.VIOLATE);
            int pointPvp = rs.getInt(PlayerEntityColumName.POINT_PVP);
            long updateTime = rs.getTimestamp(PlayerEntityColumName.UPDATE_TIME).getTime();
            String itemTimeSieuCap = rs.getString(PlayerEntityColumName.DATA_ITEM_TIME_SIEU_CAP);
            String achievement = rs.getString(PlayerEntityColumName.INFO_ACHIEVEMENT);
            String dataCard = rs.getString(PlayerEntityColumName.DATA_CARD);
            return PlayerEntity.builder()
                    .id(id)
                    .accountId(accountId)
                    .name(name)
                    .head(head)
                    .gender(gender)
                    .haveTennisSpaceShip(haveTennisSpaceShip)
                    .clanIdServer1(clanIdServer1)
                    .clanIdServer2(clanIdServer2)
                    .dataInventory(dataInventory)
                    .dataLocation(dataLocation)
                    .dataPoint(dataPoint)
                    .dataMagicTree(dataMagicTree)
                    .itemsBody(itemsBody)
                    .itemsBag(itemsBag)
                    .itemsBox(itemsBox)
                    .itemsBoxLuckyRound(itemsBoxLuckyRound)
                    .friends(friends)
                    .enemies(enemies)
                    .dataIntrinsic(dataIntrinsic)
                    .dataItemTime(dataItemTime)
                    .dataTask(dataTask)
                    .dataMabuEgg(dataMabuEgg)
                    .dataEggLinhThu(dataEggLinhThu)
                    .pet(pet)
                    .dataBlackBall(dataBlackBall)
                    .dataSideTask(dataSideTask)
                    .dataCharm(dataCharm)
                    .skills(skills)
                    .skillsShortcut(skillsShortcut)
                    .violate(violate)
                    .pointPvp(pointPvp)
                    .updateTime(updateTime)
                    .dataItemTimeSieuCap(itemTimeSieuCap)
                    .achievement(achievement)
                    .datacard(dataCard)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updatePlayer(PlayerEntity player) {
        String query = " update player set data_card =?,info_achievement =?, data_item_time_sieu_cap = ?, head = ?, have_tennis_space_ship = ?,"
                + "clan_id_sv" + Manager.SERVER + " = ?, data_inventory = ?, data_location = ?, data_point = ?, data_magic_tree = ?,"
                + "items_body = ?, items_bag = ?, items_box = ?, items_box_lucky_round = ?, friends = ?,"
                + "enemies = ?, data_intrinsic = ?, data_item_time = ?, data_task = ?, data_mabu_egg = ?, data_egg_linhthu = ?, pet = ?,"
                + "data_black_ball = ?, data_side_task = ?, data_charm = ?, skills = ?, skills_shortcut = ?, pointPvp=?, update_time = ? where id = ?";
        try {
            GirlkunDB.executeUpdate(query,
                    player.datacard,
                    player.achievement,
                    player.dataItemTimeSieuCap,
                    player.head,
                    player.haveTennisSpaceShip,
                    player.clanIdServer1,
                    player.dataInventory,
                    player.dataLocation,
                    player.dataPoint,
                    player.dataMagicTree,
                    player.itemsBody,
                    player.itemsBag,
                    player.itemsBox,
                    player.itemsBoxLuckyRound,
                    player.friends,
                    player.enemies,
                    player.dataIntrinsic,
                    player.dataItemTime,
                    player.dataTask,
                    player.dataMabuEgg,
                    player.dataEggLinhThu,
                    player.pet,
                    player.dataBlackBall,
                    player.dataSideTask,
                    player.dataCharm,
                    player.skills,
                    player.skillsShortcut,
                    player.pointPvp,
                    new Timestamp(System.currentTimeMillis()),
                    player.id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
