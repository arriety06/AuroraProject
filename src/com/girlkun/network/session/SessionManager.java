// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.session;

import java.util.ArrayList;
import java.util.List;

public class SessionManager
{
    private static SessionManager i;
    private List<Session> sessions;
    
    public static SessionManager gI() {
        if (SessionManager.i == null) {
            SessionManager.i = new SessionManager();
        }
        return SessionManager.i;
    }
    
    public SessionManager() {
        this.sessions = new ArrayList<Session>();
    }
}
