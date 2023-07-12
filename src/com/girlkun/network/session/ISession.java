// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.session;

import com.girlkun.models.item.Item;
import com.girlkun.models.player.Player;
import com.girlkun.network.io.Message;
import com.girlkun.network.handler.IKeySessionHandler;
import com.girlkun.network.handler.IMessageHandler;
import com.girlkun.network.handler.IMessageSendCollect;

import java.util.List;

public interface ISession extends IKey
{

    TypeSession getTypeSession();
    
    ISession setSendCollect(final IMessageSendCollect p0);
    
    ISession setMessageHandler(final IMessageHandler p0);
    
    ISession setKeyHandler(final IKeySessionHandler p0);
    
    ISession startSend();
    
    ISession startCollect();
    
    ISession start();
    
    ISession setReconnect(final boolean p0);
    
    void initThreadSession();
    
    void reconnect();
    
    String getIP();
    
    boolean isConnected();
    
    long getID();
    
    void sendMessage(final Message p0);
    
    void doSendMessage(final Message p0) throws Exception;
    
    void disconnect();
    
    void dispose();
    
    int getNumMessages();


    //Additional
    void onComingMessage(Message message);

    String getId();

    byte getZoomLevel();
    void setZoomLevel(byte zoomLevel);

    int getTypeClient();
    void setTypeClient(int typeClient);

    Player getPlayer();
    void setPlayer(Player player);

    String getUserName();
    void setUserName(String username);

    String getPassword();
    void setPassword(String password);

    int getUserId();
    void setUserId(int userId);

    int getVersion();
    void setVersion(int version);

    boolean isAdmin();
    void setIsAdmin(boolean isAdmin);
    
    boolean isBan();
    void setIsBan(boolean isBan);

    boolean isJoinedGame();
    void setJoinedGame(boolean joinedGame);

    void setGoldBar(int goldBar);
    int getGoldBar();

    void setVnd(int vnd);
    int getVnd();
    
    void setTongnap(int tongnap);
    int getTongnap();
    
    void setPointshare(int pointshare);
    int getPointshare();
    
    void setIsActive(boolean active);
    boolean getIsActive();

    double getBdPlayer();
    void setBdPlayer(double player);

    void initItemsReward();

    boolean isGiftBox();
    void setIsGiftBox(boolean giftBox);

    List<Item> getItemsReward();

    void setLastTimeLogout(long value);
    long getLastTimeLogout();

    String getIpAddress();

    void setTimeWait(byte timeWait);
    byte getTimeWait();

    void login(String username, String password);
}
