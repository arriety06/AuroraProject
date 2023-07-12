package com.girlkun.server;

import com.Arriety.kygui.ShopKyGuiManager;
import com.girlkun.database.GirlkunDB;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.network.server.GirlkunSessionManager;
import com.girlkun.network.session.ISession;
import com.girlkun.services.ItemTimeService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.services.func.SummonDragon;
import com.girlkun.services.func.TransactionService;
import com.girlkun.models.DragonBallNamec.NgocRongNamecService;
import com.girlkun.services.func.ChonAiDay;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client implements Runnable {

    private static Client i;

    private final Map<Long, Player> players_id = new HashMap<Long, Player>();
    private final Map<Integer, Player> players_userId = new HashMap<Integer, Player>();
    private final Map<String, Player> players_name = new HashMap<String, Player>();
    private final List<Player> players = new ArrayList<>();

    private boolean running = true;

    private Client() {
        new Thread(this).start();
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public static Client gI() {
        if (i == null) {
            i = new Client();
        }
        return i;
    }

    public void put(Player player) {
        if (!players_id.containsKey(player.id)) {
            this.players_id.put(player.id, player);
        }
        if (!players_name.containsValue(player)) {
            this.players_name.put(player.name, player);
        }
        if (!players_userId.containsValue(player)) {
            this.players_userId.put(player.getSession().getUserId(), player);
        }
        if (!players.contains(player)) {
            this.players.add(player);
        }
    }

    private void remove(ISession session) {
        if (session.getPlayer() != null) {
            this.remove(session.getPlayer());
            session.getPlayer().dispose();
        }
        if (session.isJoinedGame()) {
            session.setJoinedGame(false);
            try {
                GirlkunDB.executeUpdate("update account set last_time_logout = ? where id = ?", new Timestamp(System.currentTimeMillis()), session.getUserId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ServerManager.gI().disconnect(session);
    }

    private void remove(Player player) {
        this.players_id.remove(player.id);
        this.players_name.remove(player.name);
        this.players_userId.remove(player.getSession().getUserId());
        this.players.remove(player);
        if (!player.beforeDispose) {
            player.beforeDispose = true;
            player.mapIdBeforeLogout = player.zone.map.mapId;
            if (player.idNRNM != -1) {
                ItemMap itemMap = new ItemMap(player.zone, player.idNRNM, 1, player.location.x, player.location.y, -1);
                Service.getInstance().dropItemMap(player.zone, itemMap);
                NgocRongNamecService.gI().pNrNamec[player.idNRNM - 353] = "";
                NgocRongNamecService.gI().idpNrNamec[player.idNRNM - 353] = -1;
                NgocRongNamecService.gI().mapNrNamec[player.idNRNM - 353] = player.zone.map.mapId;
                NgocRongNamecService.gI().zoneNrNamec[player.idNRNM - 353] = (byte) player.zone.zoneId;
                player.idNRNM = -1;
            }
            ChangeMapService.gI().exitMap(player);
            TransactionService.gI().cancelTrade(player);
            if (player.clan != null) {
                player.clan.removeMemberOnline(null, player);
            }
//            if (player.itemTime != null && player.itemTime.isUseTDLT) {
//                Item tdlt = null;
//                try {
//                    tdlt = InventoryServiceNew.gI().findItemBag(player, 521);
//                } catch (Exception ex) {
//                }
//                if (tdlt != null) {
//                    ItemTimeService.gI().turnOffTDLT(player, tdlt);
//                }
//            }
            if (SummonDragon.gI().playerSummonShenron != null
                    && SummonDragon.gI().playerSummonShenron.id == player.id) {
                SummonDragon.gI().isPlayerDisconnect = true;
            }
            if (player.mobMe != null) {
                player.mobMe.mobMeDie();
            }
            if (player.pet != null) {
                if (player.pet.mobMe != null) {
                    player.pet.mobMe.mobMeDie();
                }
                ChangeMapService.gI().exitMap(player.pet);
            }
            if (player.newpet != null) {
                ChangeMapService.gI().exitMap(player.newpet);
                player.newpet.dispose();
                player.newpet = null;
            }
            if (player.goldNormar != 0){
                ChonAiDay.gI().removePlayerNormar(player);
            }
            if (player.goldVIP != 0){
                ChonAiDay.gI().removePlayerVIP(player);
            }
        }
        PlayerDAO.updatePlayer(player);
    }

    public void kickSession(ISession session) {
        if (session != null) {
            this.remove(session);
            session.disconnect();
        }
    }

    public Player getPlayer(long playerId) {
        return this.players_id.get(playerId);
    }

    public Player getPlayerByUser(int userId) {
        return this.players_userId.get(userId);
    }

    public Player getPlayer(String name) {
        return this.players_name.get(name);
    }

    public void close() {
        Logger.error("BEGIN KICK OUT SESSION.............................." + players.size() + "\n");
//        while(!GirlkunSessionManager.gI().getSessions().isEmpty()){
//            Logger.error("LEFT PLAYER: " + this.players.size() + ".........................\n");
//            this.kickSession(GirlkunSessionManager.gI().getSessions().remove(0));
//        }
        while (!players.isEmpty()) {
            this.kickSession(players.remove(0).getSession());
        }
        Logger.error("...........................................SUCCESSFUL\n");
    }

    public void cloneMySessionNotConnect() {
        Logger.error("BEGIN KICK OUT Session Not Connect...............................\n");
        Logger.error("COUNT: " + GirlkunSessionManager.gI().getSessions().size());
        if (!GirlkunSessionManager.gI().getSessions().isEmpty()) {
            for (int j = 0; j < GirlkunSessionManager.gI().getSessions().size(); j++) {
                ISession m = GirlkunSessionManager.gI().getSessions().get(j);
                if (m.getPlayer() == null) {
                    this.kickSession(GirlkunSessionManager.gI().getSessions().remove(j));
                }
            }
        }
        Logger.error("..........................................................SUCCESSFUL\n");
    }

    private void update() {
        try {
            List<ISession> sessionList = new ArrayList<>(GirlkunSessionManager.gI().getSessions());
            if (!sessionList.isEmpty()) {
                for (int i = 0; i < sessionList.size(); i++) {
                    ISession session = sessionList.get(i);
                    if (session != null) {
                        if (session.getTimeWait() > 0) {
                            session.setTimeWait((byte) (session.getTimeWait() - 1));
                            if (session.getTimeWait() == 0) {
                                kickSession(session);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi ở class Client!");
        }
    }

    @Override
    public void run() {
        while (ServerManager.isRunning) {
            try {
                long st = System.currentTimeMillis();
                update();
                if (Util.canDoWithTime(ShopKyGuiManager.gI().lastTimeUpdate, 1800000)) {
                    ShopKyGuiManager.gI().lastTimeUpdate = System.currentTimeMillis();
                    ShopKyGuiManager.gI().save();
                }
//                Thread.sleep(Math.abs(800 - (System.currentTimeMillis() - st)));
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void show(Player player) {
        String txt = "";
        txt += "Sessions: " + GirlkunSessionManager.gI().getSessions().size() + "\n";
        txt += "Id Player: " + players_id.size() + "\n";
        txt += "Use ID: " + players_userId.size() + "\n";
        txt += "Players Name: " + players_name.size() + "\n";
        txt += "Player Online: " + players.size() + "\n";
        txt += "Thread: " + Thread.activeCount();
        Service.getInstance().sendThongBaoFromAdmin(player, txt);
    }
}
