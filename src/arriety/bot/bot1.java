//package arriety.bot;
//
//import com.girlkun.models.map.Map;
//import com.girlkun.models.map.Zone;
//import com.girlkun.models.player.Player;
//import com.girlkun.server.Manager;
//import com.girlkun.services.MapService;
//import com.girlkun.services.PlayerService;
//import com.girlkun.services.Service;
//import com.girlkun.utils.Util;
//
//import java.util.List;
//
///**
// * @author Duy Béo
// */
//public class bot1 extends Player {
//
//    private long lastTimeChat;
//    private long lastTimeTargetPlayer;
//    private long timeTargetPlayer = 5000;
//
//    public void initbot1() {
//        init();
//    }
//
//    @Override
//    public short getHead() {
//        return 95;
//    }
//
//    @Override
//    public short getBody() {
//        return 96;
//    }
//
//    @Override
//    public short getLeg() {
//        return 97;
//    }
//
//    public void joinMap(Zone z, Player player) {
//        MapService.gI().goToMap(player, z);
//        z.load_Me_To_Another(player);
//    }
//    protected long lastTimeAttack;
//
//    private long lastTimemove;
//
//    @Override
//    public void update() {
//        if (Util.canDoWithTime(lastTimeChat, 5000)) {
//            Service.getInstance().chat(this, "|2|Em ơi chua ngọt đã từng\n|7|Em ơi chua ngọt đã từng.");
//            Service.getInstance().chat(this, "Khi đói cùng chung một dạ\n Khi rét cùng chung một dòng");
//            lastTimeChat = System.currentTimeMillis();
//        }
//        if (Util.canDoWithTime(this.lastTimeAttack, 100)) {
//            this.lastTimeAttack = System.currentTimeMillis();
//            Player pl = getPlayerAttack();
//            if (pl == null || pl.isDie() || pl.isNewPet) {
//                return;
//            }
//            if (Util.canDoWithTime(lastTimemove, 5000)) {
//                lastTimemove = System.currentTimeMillis();
//                this.moveTo(pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(10, 40)),
//                        pl.location.y);
//            }
//
//        }
//    }
//
//    public void moveToPlayer(Player player) {
//        this.moveTo(player.location.x, player.location.y);
//    }
//
//    public void moveTo(int x, int y) {
//        byte dir = (byte) (this.location.x - x < 0 ? 1 : -1);
//        byte move = (byte) Util.nextInt(40, 60);
//        PlayerService.gI().playerMove(this, this.location.x + (dir == 1 ? move : -move), y + (Util.isTrue(3, 10) ? -50 : 0));
//    }
//
//    protected Player playerTarger;
//
//    public Player getPlayerAttack() {
//        if (this.playerTarger != null && (this.playerTarger.isDie() || !this.zone.equals(this.playerTarger.zone))) {
//            this.playerTarger = null;
//        }
//        if (this.playerTarger == null || Util.canDoWithTime(this.lastTimeTargetPlayer, this.timeTargetPlayer)) {
//            this.playerTarger = this.zone.getRandomPlayerInMap();
//            this.lastTimeTargetPlayer = System.currentTimeMillis();
//            this.timeTargetPlayer = Util.nextInt(5000, 7000);
//        }
//        return this.playerTarger;
//    }
//
//    private void init() {
//        int id = 2_100_000_000;
//        for (Map m : Manager.MAPS) {
//            if (m.mapId == 0) {
//                for (Zone z : m.zones) {
//                    if (z.zoneId == 4) {
//                        bot1 pl = new bot1();
//                        pl.name = "thantai";
//                        pl.gender = 0;
//                        pl.id = id--;
//                        pl.nPoint.hpMax = 69;
//                        pl.nPoint.hpg = 69;
//                        pl.nPoint.hp = 69;
//                        pl.nPoint.setFullHpMp();
//                        pl.location.x = 387;
//                        pl.location.y = 336;
//                        joinMap(z, pl);
//                        z.setReferee(pl);
//                    }
//
//                }
//            }
//            if (m.mapId == 0) {
//                for (Zone z : m.zones) {
//                    if (z.zoneId == 6) {
//                        bot1 pl = new bot1();
//                        pl.name = "beonghichngu";
//                        pl.gender = 0;
//                        pl.id = id--;
//                        pl.nPoint.hpMax = 69;
//                        pl.nPoint.hpg = 69;
//                        pl.nPoint.hp = 69;
//                        pl.nPoint.setFullHpMp();
//                        pl.location.x = 385;
//                        pl.location.y = 264;
//                        joinMap(z, pl);
//                        z.setReferee(pl);
//                    }
//                }
//            }
//            if (m.mapId == 0) {
//                for (Zone z : m.zones) {
//                    if (z.zoneId == 11) {
//                        bot1 pl = new bot1();
//                        pl.name = "anhnhoem";
//                        pl.gender = 0;
//                        pl.id = id--;
//                        pl.nPoint.hpMax = 69;
//                        pl.nPoint.hpg = 69;
//                        pl.nPoint.hp = 69;
//                        pl.nPoint.setFullHpMp();
//                        pl.location.x = 1201;
//                        pl.location.y = 432;
//                        joinMap(z, pl);
//                        z.setReferee(pl);
//                    }
//                }
//            }
//            if (m.mapId == 0) {
//                for (Zone z : m.zones) {
//                    if (z.zoneId == 11) {
//                        bot1 pl = new bot1();
//                        pl.name = "nhinmanung";
//                        pl.gender = 0;
//                        pl.id = id--;
//                        pl.nPoint.hpMax = 69;
//                        pl.nPoint.hpg = 69;
//                        pl.nPoint.hp = 69;
//                        pl.nPoint.setFullHpMp();
//                        pl.location.x = 1201;
//                        pl.location.y = 432;
//                        joinMap(z, pl);
//                        z.setReferee(pl);
//                    }
//                }
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//            if (MapService.gI().isMapNha(m.mapId)) {
//                continue;
//            }
//            for (Zone z : m.zones) {
//                bot1 pl = new bot1();
//                pl.name = "peodeozai";
//                pl.gender = 0;
//                pl.id = id--;
//                pl.nPoint.hpMax = 69;
//                pl.nPoint.hpg = 69;
//                pl.nPoint.hp = 69;
//                pl.nPoint.setFullHpMp();
//                pl.location.x = 1201;
//                pl.location.y = 432;
//                joinMap(m.zones.get(Util.nextInt(0, m.zones.size() - 1)), pl);
//                z.setReferee(pl);
//            }
//        }
//    }
//}
