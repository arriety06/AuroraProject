// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.server;

import com.girlkun.network.session.ISession;

public interface ISessionAcceptHandler
{
    void sessionInit(final ISession p0);
    
    void sessionDisconnect(final ISession p0);
}
