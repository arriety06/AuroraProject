// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.handler;

import com.girlkun.network.io.Message;
import com.girlkun.network.session.ISession;

public interface IKeySessionHandler
{
    void sendKey(final ISession p0);
    
    void setKey(final ISession p0, final Message p1) throws Exception;
}
