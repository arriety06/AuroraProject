// 
// Decompiled by Procyon v0.5.36
// 
package com.girlkun.network.example;

import com.girlkun.network.io.Message;
import com.girlkun.network.CommandMessage;
import com.girlkun.network.session.ISession;
import com.girlkun.network.handler.IKeySessionHandler;
import com.girlkun.network.io.Sender;

public class KeyHandler implements IKeySessionHandler {

    private Sender sender;

    @Override
    public void sendKey(final ISession session) {
        final Message msg = new Message(CommandMessage.REQUEST_KEY);
        try {
            final byte[] KEYS = session.getKey();
            msg.writer().writeByte(KEYS.length);
            msg.writer().writeByte(KEYS[0]);
            for (int i = 1; i < KEYS.length; ++i) {
                msg.writer().writeByte(KEYS[i] ^ KEYS[i - 1]);
            }
            session.doSendMessage(msg);
            msg.cleanup();
            session.setSentKey(true);
//            ThreadPool.sender().execute(sender);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setKey(final ISession session, final Message message) throws Exception {
        try {
            final byte b = message.reader().readByte();
            final byte[] KEYS = new byte[b];
            for (int i = 0; i < b; ++i) {
                KEYS[i] = message.reader().readByte();
            }
            for (int j = 0; j < KEYS.length - 1; ++j) {
                KEYS[j + 1] ^= KEYS[j];
            }
            session.setKey(KEYS);
            session.setSentKey(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
