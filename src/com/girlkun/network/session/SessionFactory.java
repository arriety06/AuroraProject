// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.session;

import java.net.Socket;

public class SessionFactory
{
    private static SessionFactory I;
    
    public static SessionFactory gI() {
        if (SessionFactory.I == null) {
            SessionFactory.I = new SessionFactory();
        }
        return SessionFactory.I;
    }
    
    public ISession cloneSession(final Class clazz, final Socket socket) throws Exception {
        return (ISession)clazz.getConstructor(Socket.class).newInstance(socket);
    }
}
