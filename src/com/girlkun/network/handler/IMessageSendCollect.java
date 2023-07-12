// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.handler;

import java.io.DataOutputStream;
import com.girlkun.network.io.Message;
import java.io.DataInputStream;
import com.girlkun.network.session.ISession;

public interface IMessageSendCollect
{
    Message readMessage(final ISession p0, final DataInputStream p1) throws Exception;
    
    byte readKey(final ISession p0, final byte p1);
    
    void doSendMessage(final ISession p0, final DataOutputStream p1, final Message p2) throws Exception;
    
    byte writeKey(final ISession p0, final byte p1);
}
