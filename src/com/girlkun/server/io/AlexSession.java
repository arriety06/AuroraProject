package com.girlkun.server.io;

import java.net.Socket;

import com.girlkun.models.player.Player;
import com.girlkun.server.Controller;
import com.girlkun.data.DataGame;
import com.girlkun.jdbc.daos.GodGK;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.sieuhang.SieuHangManager;
import com.girlkun.network.session.Session;
import com.girlkun.server.Client;
import com.girlkun.server.Maintenance;
import com.girlkun.server.Manager;
import com.girlkun.server.model.AntiLogin;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlexSession extends Session {

    private static final Map<String, AntiLogin> ANTILOGIN = new HashMap<>();
    private Player player;

    private byte timeWait = 100;
    private Session session;
    private String ipAddress;
    private boolean isAdmin;
    private int userId;
    private String uu;
    private String pp;

    private int typeClient;
    private byte zoomLevel;

    private long lastTimeLogout;
    private boolean joinedGame;

    private boolean actived;

    private int goldBar;
    private int vnd;
    private int tongnap;
    private int pointshare;
    private List<Item> itemsReward;
    private String dataReward;
    private boolean is_gift_box;
    private double bdPlayer;

    private int version;

    public AlexSession(Socket socket) {
        super(socket);
        ipAddress = socket.getInetAddress().getHostAddress();
//        Util.WorhHistory("Beo.txt ", "Accept port: " + socket.getPort()
//                + " Accept ip: " + ipAddress
//                + " Tài Khoản: " + session.getUserName()
//                + " Mật khẩu: " + session.getPassword() + "\n");
    }

    @Override
    public void initItemsReward() {
        try {
            this.itemsReward = new ArrayList<>();
            String[] itemsReward = dataReward.split(";");
            for (String itemInfo : itemsReward) {
                if (itemInfo == null || itemInfo.equals("")) {
                    continue;
                }
                String[] subItemInfo = itemInfo.replaceAll("[{}\\[\\]]", "").split("\\|");
                String[] baseInfo = subItemInfo[0].split(":");
                int itemId = Integer.parseInt(baseInfo[0]);
                int quantity = Integer.parseInt(baseInfo[1]);
                Item item = ItemService.gI().createNewItem((short) itemId, quantity);
                if (subItemInfo.length == 2) {
                    String[] options = subItemInfo[1].split(",");
                    for (String opt : options) {
                        if (opt == null || opt.equals("")) {
                            continue;
                        }
                        String[] optInfo = opt.split(":");
                        int tempIdOption = Integer.parseInt(optInfo[0]);
                        int param = Integer.parseInt(optInfo[1]);
                        item.itemOptions.add(new Item.ItemOption(tempIdOption, param));
                    }
                }
                this.itemsReward.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public byte getZoomLevel() {
        return zoomLevel;
    }

    @Override
    public void setZoomLevel(byte zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    @Override
    public int getTypeClient() {
        return typeClient;
    }

    @Override
    public void setTypeClient(int typeClient) {
        this.typeClient = typeClient;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String getUserName() {
        return uu;
    }

    @Override
    public void setUserName(String username) {
        this.uu = username;
    }

    @Override
    public String getPassword() {
        return pp;
    }

    @Override
    public void setPassword(String password) {
        this.pp = password;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean isJoinedGame() {
        return joinedGame;
    }

    @Override
    public void setJoinedGame(boolean joinedGame) {
        this.joinedGame = joinedGame;
    }

    @Override
    public void setGoldBar(int goldBar) {
        this.goldBar = goldBar;
    }

    @Override
    public int getGoldBar() {
        return this.goldBar;
    }

     @Override
    public void setVnd(int vnd) {
        this.vnd = vnd;
    }

    @Override
    public int getVnd() {
        return this.vnd;
    }
    
    @Override
    public void setTongnap(int tongnap) {
        this.tongnap = tongnap;
    }

    @Override
    public int getTongnap() {
        return this.tongnap;
    }
    
    @Override
    public void setPointshare(int pointshare) {
        this.pointshare = pointshare;
    }

    @Override
    public int getPointshare() {
        return this.pointshare;
    }
    
    @Override
    public void setIsActive(boolean active) {
        this.actived = active;
    }

    @Override
    public boolean getIsActive() {
        return actived;
    }

    @Override
    public double getBdPlayer() {
        return bdPlayer;
    }

    @Override
    public void setBdPlayer(double player) {
        this.bdPlayer = player;
    }

    @Override
    public boolean isGiftBox() {
        return is_gift_box;
    }

    @Override
    public void setIsGiftBox(boolean giftBox) {
        this.is_gift_box = giftBox;
    }

    @Override
    public List<Item> getItemsReward() {
        return itemsReward;
    }

    @Override
    public void setLastTimeLogout(long value) {
        this.lastTimeLogout = value;
    }

    @Override
    public long getLastTimeLogout() {
        return lastTimeLogout;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public void setTimeWait(byte timeWait) {
        this.timeWait = timeWait;
    }

    @Override
    public byte getTimeWait() {
        return timeWait;
    }

    @Override
    public void sendKey() throws Exception {
        super.sendKey();
        this.startSend();
    }

    @Override

    public void login(String username, String password) {
        AntiLogin al = ANTILOGIN.get(this.ipAddress);
        if (al == null) {
            al = new AntiLogin();
            ANTILOGIN.put(this.ipAddress, al);
        }
        if (!al.canLogin()) {
            Service.getInstance().sendThongBaoOK(this, al.getNotifyCannotLogin());
            return;
        }
        if (Manager.LOCAL) {
            Service.getInstance().sendThongBaoOK(this, "Server này chỉ để lưu dữ liệu\nVui lòng qua server khác");
            return;
        }
        if (Maintenance.isRuning) {
            Service.getInstance().sendThongBaoOK(this, "Server đang trong thời gian bảo trì, vui lòng quay lại sau");
            return;
        }
        if (!this.isAdmin && Client.gI().getPlayers().size() >= Manager.MAX_PLAYER) {
            Service.getInstance().sendThongBaoOK(this, "Máy chủ hiện đang quá tải, "
                    + "cư dân vui lòng di chuyển sang máy chủ khác.");
            return;
        }
        if (this.player != null) {
            return;
        } else {
            Player player = null;
            try {
                long st = System.currentTimeMillis();
                setUserName(username);
                setPassword(password);
                player = GodGK.login(this, al);
                if (player != null) {
                    // -77 max small
                    DataGame.sendSmallVersion(this);
                    // -93 bgitem version
                    Service.getInstance().sendMessage(this, -93, "1630679752231_-93_r");

                    this.timeWait = 0;
                    this.joinedGame = true;
                    player.nPoint.calPoint();
                    player.nPoint.setHp(player.nPoint.hp);
                    player.nPoint.setMp(player.nPoint.mp);
                    player.zone.addPlayer(player);
                    if (player.pet != null) {
                        player.pet.nPoint.calPoint();
                        player.pet.nPoint.setHp(player.pet.nPoint.hp);
                        player.pet.nPoint.setMp(player.pet.nPoint.mp);
                    }

                    player.setSession(this);
                    Client.gI().put(player);
                    this.player = player;
                    //-28 -4 version data game
                    DataGame.sendVersionGame(this);
                    //-31 data item background
                    DataGame.sendDataItemBG(this);
                    Controller.getInstance().sendInfo(this);

//                    Logger.warning("Login thành công player " + this.player.name + ": " + (System.currentTimeMillis() - st) + " ms\n");
                    int rank = SieuHangManager.gI().rankistop((int) player.id);
                    if (rank != -1 && !SieuHangManager.gI().isreceivetop((int) player.id)) {
                        int HNreceive = SieuHangManager.gI().getrubyranksh(rank);
                        player.inventory.ruby += HNreceive;
                        Service.gI().sendMoney(player);
                        SieuHangManager.gI().update_data_rank_sieu_hang((int) player.id, rank, (byte) 2);
                        Service.getInstance().sendThongBao(player, "Chúc mừng bạn đã giành top: " + rank + "\nBạn nhận được " + HNreceive + "Hồng ngọc");
                    }
                    InventoryServiceNew.gI().sendItemBody(player);
//                    Service.getInstance().sendThongBaoOK(this, "Ngọc rồng sao đen sẽ mở lúc 21h hôm nay");
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (player != null) {
                    player.dispose();
                }
            }
        }
    }
}
