// 
// Decompiled by Procyon v0.5.36
// 
package com.girlkun.network.io;

import java.net.Socket;
import com.girlkun.network.handler.IMessageSendCollect;
import java.io.DataOutputStream;
import java.util.ArrayList;
import com.girlkun.network.session.ISession;

public class Sender implements Runnable {

    private ISession session;
    private ArrayList<Message> messages;
    private DataOutputStream dos;
    private IMessageSendCollect sendCollect;

    public Sender(final ISession session, final Socket socket) {
        try {
            this.session = session;
            this.messages = new ArrayList<Message>();
            this.setSocket(socket);
        } catch (Exception ex) {
        }
    }

    public Sender setSocket(final Socket socket) {
        try {
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
        }
        return this;
    }

    @Override
    public void run() {
        while (this.session != null && this.session.isConnected()) {
            try {
                while (this.messages != null && this.messages.size() > 0) {
                    Message message = this.messages.remove(0);
                    if (message != null) {
                        this.doSendMessage(message);
                    }
                    message = null;
                }
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void doSendMessage(final Message message) throws Exception {
        this.sendCollect.doSendMessage(this.session, this.dos, message);
    }

    public synchronized void sendMessage(final Message msg) {
        if (this.session != null && msg != null && this.session.isConnected()) {
            this.messages.add(msg);
        }
    }

    public void setSend(final IMessageSendCollect sendCollect) {
        this.sendCollect = sendCollect;
    }

    public int getNumMessages() {
        if (this.messages != null) {
            return this.messages.size();
        }
        return -1;
    }

    public void close() {
        if (this.messages != null) {
            this.messages.clear();
        }
        if (this.dos != null) {
            try {
                this.dos.close();
            } catch (Exception ex) {
            }
        }
    }

    public void dispose() {
        this.session = null;
        this.messages = null;
        this.sendCollect = null;
        this.dos = null;
    }
}
