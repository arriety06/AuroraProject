// 
// Decompiled by Procyon v0.5.36
// 
package com.girlkun.network.io;

import com.girlkun.network.server.GirlkunServer;
import com.girlkun.network.session.TypeSession;
import com.girlkun.network.CommandMessage;
import java.net.Socket;
import com.girlkun.network.handler.IMessageHandler;
import com.girlkun.network.handler.IMessageSendCollect;
import java.io.DataInputStream;
import com.girlkun.network.session.ISession;

public class Collector implements Runnable {

    private ISession session;
    private DataInputStream dis;
    private IMessageSendCollect collect;
    private IMessageHandler messageHandler;

    public Collector(final ISession session, final Socket socket) {
        this.session = session;
        this.setSocket(socket);
    }

    public Collector setSocket(final Socket socket) {
        try {
            this.dis = new DataInputStream(socket.getInputStream());
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return this;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (this.session.isConnected()) {
                    final Message msg = this.collect.readMessage(this.session, this.dis);
                    if (msg.command == CommandMessage.REQUEST_KEY) {
                        if (this.session.getTypeSession() == TypeSession.SERVER) {
                            this.session.sendKey();
                        } else {
                            this.session.setKey(msg);
                        }
                    } else {
                        this.messageHandler.onMessage(this.session, msg);
                    }
                    msg.cleanup();
                }
                Thread.sleep(300);
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
            try {
                GirlkunServer.gI().getAcceptHandler().sessionDisconnect(this.session);
            } catch (Exception ex2) {
//                ex2.printStackTrace();
            }
            if (this.session != null) {
                System.out.println("M\u1ea5t k\u1ebft n\u1ed1i v\u1edbi session " + this.session.getIP() + "...");
                this.session.disconnect();
            }
        }
    }

    public void setCollect(final IMessageSendCollect collect) {
        this.collect = collect;
    }

    public void setMessageHandler(final IMessageHandler handler) {
        this.messageHandler = handler;
    }

    public void close() {
        if (this.dis != null) {
            try {
                this.dis.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dispose() {
        this.session = null;
        this.dis = null;
        this.collect = null;
    }
}
