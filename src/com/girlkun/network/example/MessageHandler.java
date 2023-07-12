// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.example;

import com.girlkun.network.io.Message;
import com.girlkun.network.session.ISession;
import com.girlkun.network.handler.IMessageHandler;

public class MessageHandler implements IMessageHandler
{
    @Override
    public void onMessage(final ISession session, final Message msg) throws Exception {
        System.out.println(msg.reader().readUTF());
        msg.cleanup();
    }
}
