package com.girlkun.server;

import AururaFactory.FormRecall;
import AururaFactory.GiveItemForm;
import com.arriety.injector.NroInjector;
import com.Arriety.kygui.ShopKyGuiManager;
import com.Arriety.manager.AutoSaveManager;
import com.Arriety.message.MessageConsumerListener;
import com.Arriety.message.MessageConsumerManager;
import com.Arriety.networking.NetworkClient;
import com.Arriety.redis.RedisConnectionManager;
import com.Arriety.repositorys.AccountRepository;
import com.Arriety.repositorys.PlayerRepository;
import com.Arriety.repositorys.RedisRepository;
import com.Arriety.usecase.PlayerUseCase;
import com.girlkun.database.GirlkunDB;
import AururaFactory.ActiveCommandLine;
import com.Arriety.furry.FurryManager;
import java.net.ServerSocket;
import com.girlkun.jdbc.daos.HistoryTransactionDAO;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.dhvt23.MartialCongressManager;
import com.girlkun.models.map.sieuhang.SieuHangManager;
import com.girlkun.models.player.Player;
import com.girlkun.network.session.ISession;
import com.girlkun.network.example.MessageSendCollect;
import com.girlkun.network.server.GirlkunServer;
import com.girlkun.network.server.GirlkunSessionManager;
import com.girlkun.network.server.IServerClose;
import com.girlkun.network.server.ISessionAcceptHandler;
import com.girlkun.result.GirlkunResultSet;
import com.girlkun.server.io.AlexSession;
import com.girlkun.server.io.MyKeyHandler;
import com.girlkun.services.ClanService;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.models.DragonBallNamec.NgocRongNamecService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChonAiDay;
import com.girlkun.utils.Logger;
import com.girlkun.utils.TimeUtil;
import com.girlkun.utils.Util;

import java.util.*;
import java.util.logging.Level;

import org.json.JSONObject;
import org.json.JSONArray;
import redis.clients.jedis.JedisPool;

public class ServerManager {

    public static String timeStart;

    public static final Map CLIENTS = new HashMap();

    public static String NAME = "Arriety";
    public static int PORT = 14445;

    private static ServerManager instance;

    public static ServerSocket listenSocket;
    public static boolean isRunning;

    public void init() {
        Manager.gI();
        try {
            if (Manager.LOCAL) {
                return;
            }
            GirlkunDB.executeUpdate("update account set last_time_login = '2000-01-01', "
                    + "last_time_logout = '2001-01-01'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HistoryTransactionDAO.deleteHistory();
    }

    public static ServerManager gI() {
        if (instance == null) {
            instance = new ServerManager();
            instance.init();
        }
        return instance;
    }

    public static void main(String[] args) {
        timeStart = TimeUtil.getTimeNow("dd/MM/yyyy HH:mm:ss");
        Manager.gI().initGameData();
        ActiveCommandLine.gI().run();
        injectDependency();
        activeAutoSave();
//        comsumeMessageRabbitMq();
        ServerManager.gI().run();
    }

    private static void injectDependency() {
        JedisPool jedisPool = RedisConnectionManager.getInstance().getPool();
        RedisRepository redisRepository = new RedisRepository(jedisPool);
        PlayerRepository playerRepository = new PlayerRepository();
        PlayerUseCase playerUseCase = new PlayerUseCase(redisRepository, playerRepository);
        AccountRepository accountRepository = new AccountRepository();

        NroInjector.getInstance().injectRedisRepository(redisRepository);
        NroInjector.getInstance().injectPlayerRepository(playerRepository);
        NroInjector.getInstance().injectPlayerUseCase(playerUseCase);
        NroInjector.getInstance().injectAccountRepository(accountRepository);
    }

    private static void activeAutoSave() {
        AutoSaveManager.getInstance().autoSave();
    }

    private static void comsumeMessageRabbitMq() {
        try {
            MessageConsumerManager.getInstance().consumeWith(new MessageConsumerListener() {
                @Override
                public void onMessage(org.json.JSONObject jsonObject) {

                    System.out.println("Received message from queue: " + jsonObject.toString());
                    String type = (String) jsonObject.get("type");
                    switch (type) {
                        case "UPDATE_ITEMBAG": {
                            Integer userId = (Integer) jsonObject.get("userId");
                            Integer quantity = (Integer) jsonObject.get("quantity");
                            Integer itemId = (Integer) jsonObject.get("itemId");
                            Integer amount = (Integer) jsonObject.get("amount");
                            Player plInGame = Client.gI().getPlayerByUser(userId);
                            if (plInGame == null) {
                                //User chua login vao game
                                return;
                            }
                            if (plInGame.inventory != null && plInGame.inventory.itemsBag != null) {
                                List<Item> items = plInGame.inventory.itemsBag;
                                Item goldItem = null;
                                for (int i = 0; i < items.size(); i++) {
                                    Item bagItem = items.get(i);

                                    if (bagItem != null && bagItem.template != null && bagItem.template.id == 457) {
                                        goldItem = bagItem;
                                        System.out.println("Find bagItem 457");
                                    }
                                }

                                if (goldItem != null) {
                                    goldItem.quantity += quantity;
                                    String message = String.format("Bạn vừa được cộng %d thỏi vàng. Chúc bạn chơi game vui vẻ...", quantity);
                                    InventoryServiceNew.gI().sendItemBags(plInGame);
                                    Service.getInstance().sendThongBao(plInGame, message);
                                } else {
                                    Item newItem = ItemService.gI().createNewItem((short) 457, quantity);
                                    InventoryServiceNew.gI().addItemBag(plInGame, newItem);
                                    InventoryServiceNew.gI().sendItemBags(plInGame);
                                    String message = String.format("Bạn vừa được cộng %d thỏi vàng. Chúc bạn chơi game vui vẻ...", quantity);
                                    Service.getInstance().sendThongBao(plInGame, message);
                                }
                            }

                            //System.out.println(jsonObject);
                            break;
                        }
                        case "UPDATE_LUONG": {
                            int userId = jsonObject.getInt("userId");
                            int quantity = jsonObject.getInt("quantity");
                            Player plInGame = Client.gI().getPlayerByUser(userId);
                            if (plInGame != null && plInGame.inventory != null) {
                                plInGame.inventory.gem += quantity;
                                Service.getInstance().sendMoney(plInGame);
                                String message = String.format("Bạn vừa được cộng %d ngọc. Chúc bạn chơi game vui vẻ..", quantity);
                                Service.getInstance().sendThongBao(plInGame, message);
                            }
                            break;
                        }
                        case "UPDATE_NGOC_KHOA":
                            Integer userId = (Integer) jsonObject.get("userId");
                            Integer quantity = (Integer) jsonObject.get("quantity");
                            Player plInGame = Client.gI().getPlayerByUser(userId);
                            if (plInGame == null) {
                                //User chua login vao game
                                return;
                            }
                            Item vangKhoaItem = InventoryServiceNew.gI().getVangKhoaItem(plInGame);
                            if (vangKhoaItem != null && vangKhoaItem.isNotNullItem()) {
                                vangKhoaItem.quantity += quantity;
                                InventoryServiceNew.gI().sendItemBags(plInGame);
                                String message = String.format("Bạn vừa được cộng %d vàng khóa. Chúc bạn chơi game vui vẻ...", quantity);
                                Service.getInstance().sendThongBao(plInGame, message);
                            } else {
                                Item newItem = ItemService.gI().createNewItem((short) 1240, quantity);
                                newItem.itemOptions.clear();
                                newItem.itemOptions.add(new Item.ItemOption(30, 1));
                                InventoryServiceNew.gI().addItemBag(plInGame, newItem);
                                InventoryServiceNew.gI().sendItemBags(plInGame);
                                String message = String.format("Bạn vừa được cộng %d vàng khóa. Chúc bạn chơi game vui vẻ...", quantity);
                                Service.getInstance().sendThongBao(plInGame, message);
                            }
                            break;
                        case "BAOTRI":
                            Maintenance.gI().start(60);
                            JSONObject submitJson = new JSONObject();
                            submitJson.put("type", "BAOTRI");
                            submitJson.put("message", "Server đã nhận lệnh bảo trỉ trong vòng 60s");

                            NetworkClient.getInstance().setBaseUrl(jsonObject.getString("domain"));
                            NetworkClient.getInstance().confirm(submitJson);
                            break;
                        case "UPDATE_TOPFAN":
                            JSONArray jsonArray = (JSONArray) jsonObject.get("payload");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject topFanObject = (JSONObject) jsonArray.get(i);
                                int accountId = topFanObject.getInt("accountId");
                                String accountName = topFanObject.getString("name");

                                System.out.println(accountId);
                                System.out.println(accountName);
                            }

                            JSONObject updateTopFanJson = new JSONObject();
                            updateTopFanJson.put("type", "UPDATE_TOPFAN");
                            updateTopFanJson.put("message", "Server đã nhận lệnh cập nhật topfan");

                            NetworkClient.getInstance().setBaseUrl(jsonObject.getString("domain"));
                            NetworkClient.getInstance().confirm(updateTopFanJson);
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.print("Arrriety exception " + ex.toString());
        }
    }

    public void run() {
        long delay = 300;
        isRunning = true;
        activeCommandLine();
        activeGame();

        ChonAiDay.gI().lastTimeEnd = System.currentTimeMillis() + 300000;
        new Thread(ChonAiDay.gI(), "Thread CAD").start();
        NgocRongNamecService.gI().initNgocRongNamec((byte) 0);
        new Thread(NgocRongNamecService.gI(), "Thread NRNM").start();
        new Thread(() -> {
            while (isRunning) {
                try {
                    long start = System.currentTimeMillis();
                    MartialCongressManager.gI().update();
                    SieuHangManager.gI().update();
                    long timeUpdate = System.currentTimeMillis() - start;
//                    if (timeUpdate < delay) {
//                        Thread.sleep(delay - timeUpdate);
//                    }
                    Thread.sleep(delay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Update DHVT").start();
        try {
            Thread.sleep(300);
            BossManager.gI().loadBoss();
            Manager.MAPS.forEach(com.girlkun.models.map.Map::initBoss);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            java.util.logging.Logger.getLogger(BossManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.log(Logger.PURPLE, "\n      ▄█████ ]▄▄▄▄▄▄▃\n ▂▄▅███████▅▄▃▂\nI████[ARRIETY]████]\n ◥⊙▲⊙▲⊙▲⊙▲⊙▲⊙▲⊙◤\n");
        Thread thread = new Thread();
        Logger.log(Logger.PURPLE, "Start server......... Current thread: " + Thread.activeCount() + "\n");
        activeServerSocket();
    }

    private void act() throws Exception {
        GirlkunServer.gI().init().setAcceptHandler(new ISessionAcceptHandler() {
            @Override
            public void sessionInit(ISession is) {
//                antiddos girlkun
//                if (!canConnectWithIp(is.getIP())) {
//                    is.disconnect();
//                    return;
//                }

                is = is.setMessageHandler(Controller.getInstance())
                        .setSendCollect(new MessageSendCollect())
                        .setKeyHandler(new MyKeyHandler())
                        .startCollect();
            }

            @Override
            public void sessionDisconnect(ISession session) {
                Client.gI().kickSession(session);
            }
        }).setTypeSessioClone(AlexSession.class)
                .setDoSomeThingWhenClose(new IServerClose() {
                    @Override
                    public void serverClose() {
                        System.out.println("server close");
                        System.exit(0);
                    }
                })
                .start(PORT);

    }

    private void activeServerSocket() {
        if (true) {
            try {
                this.act();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    }

//    private boolean canConnectWithIp(String ipAddress) {
//        Object o = CLIENTS.get(ipAddress);
//        if (o == null) {
//            CLIENTS.put(ipAddress, 1);
//            return true;
//        } else {
//            int n = Integer.parseInt(String.valueOf(o));
//            if (n < Manager.MAX_PER_IP) {
//                n++;
//                CLIENTS.put(ipAddress, n);
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
    public void disconnect(ISession session) {
        Object o = CLIENTS.get(session.getIP());
        if (o != null) {
            int n = Integer.parseInt(String.valueOf(o));
            n--;
            if (n < 0) {
                n = 0;
            }
            CLIENTS.put(session.getIP(), n);
        }
    }

    private void activeCommandLine() {
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                String line = sc.nextLine();
                if (line.equals("baotri")) {
                    Maintenance.gI().start(20);
                } else if (line.equals("athread")) {
                    ServerNotify.gI().notify("Nro ALEXD debug server: " + Thread.activeCount());
                } else if (line.equals("nplayer")) {
                    Logger.error("Player in game: " + Client.gI().getPlayers().size() + "\n");
                } else if (line.equals("admin")) {
                    new Thread(() -> {
                        Client.gI().close();
                    }).start();
                } else if (line.startsWith("bang")) {
                    new Thread(() -> {
                        try {
                            ClanService.gI().close();
                            Logger.error("Save " + Manager.CLANS.size() + " bang");
                        } catch (Exception e) {
                            Logger.error("Lỗi save clan!...................................\n");
                        }
                    }).start();
                } else if (line.startsWith("a")) {
                    String a = line.replace("a ", "");
                    Service.getInstance().sendThongBaoAllPlayer(a);
                } else if (line.startsWith("qua")) {
//                    =1-1-1-1=1-1-1-1=
//                     =playerId-quantily-itemId-sql=optioneId-pagram=

                    try {
                        List<Item.ItemOption> ios = new ArrayList<>();
                        String[] pagram1 = line.split("=")[1].split("-");
                        String[] pagram2 = line.split("=")[2].split("-");
                        if (pagram1.length == 4 && pagram2.length % 2 == 0) {
                            Player p = Client.gI().getPlayer(Integer.parseInt(pagram1[0]));
                            if (p != null) {
                                for (int i = 0; i < pagram2.length; i += 2) {
                                    ios.add(new Item.ItemOption(Integer.parseInt(pagram2[i]), Integer.parseInt(pagram2[i + 1])));
                                }
                                Item i = Util.sendDo(Integer.parseInt(pagram1[2]), Integer.parseInt(pagram1[3]), ios);
                                i.quantity = Integer.parseInt(pagram1[1]);
                                InventoryServiceNew.gI().addItemBag(p, i);
                                InventoryServiceNew.gI().sendItemBags(p);
                                Service.getInstance().sendThongBao(p, "Admin trả đồ. anh em thông cảm nhé...");
                            } else {
                                System.out.println("Người chơi không online");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Lỗi quà");
                    }
                } else if (line.startsWith("client")) {
                    String txt = "";
                    txt += "sessions: " + GirlkunSessionManager.gI().getSessions().size() + "\n";
                    System.out.println(txt);
                } else if (line.startsWith("give")) {
                    GiveItemForm.gI().run();
                } else if (line.startsWith("recall")) {
                    FormRecall.gI().run();
                } else if (line.startsWith("test")) {
                    ActiveCommandLine.gI().run();
                } else if (line.startsWith("gc")) {
                    System.gc();
                    System.out.println("Clear Thành Công:");
                }
            }
        }, "Active line").start();
    }

    private void activeGame() {
    }

    public void close(long delay) {
        GirlkunServer.gI().stopConnect();

        isRunning = false;
        try {
            ClanService.gI().close();
        } catch (Exception e) {
            Logger.error("Lỗi save clan!...................................\n");
        }
        Client.gI().close();
        ShopKyGuiManager.gI().save();
        FurryManager.gI().save();
        Logger.success("SUCCESSFULLY MAINTENANCE!...................................\n");
        System.exit(0);
    }

    public long getNumPlayer() {
        long num = 0;
        try {
            GirlkunResultSet rs = GirlkunDB.executeQuery("SELECT COUNT(*) FROM `player`");
            rs.first();
            num = rs.getLong(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }
}
