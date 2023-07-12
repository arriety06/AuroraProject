// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.server;

import java.util.Iterator;
import java.util.ArrayList;
import com.girlkun.network.session.ISession;
import java.util.List;

public class GirlkunSessionManager
{
    private static GirlkunSessionManager i;
    private List<ISession> sessions;
    
    public static GirlkunSessionManager gI() {
        if (GirlkunSessionManager.i == null) {
            GirlkunSessionManager.i = new GirlkunSessionManager();
        }
        return GirlkunSessionManager.i;
    }
    
    public GirlkunSessionManager() {
        this.sessions = new ArrayList<ISession>();
    }
    
    public void putSession(final ISession session) {
        this.sessions.add(session);
    }
    
    public void removeSession(final ISession session) {
        this.sessions.remove(session);
    }
    
    public List<ISession> getSessions() {
        return this.sessions;
    }
    
    public ISession findByID(final long id) throws Exception {
        if (this.sessions.isEmpty()) {
            throw new Exception("Session " + id + " kh\u00f4ng t\u1ed3n t\u1ea1i");
        }
        for (final ISession session : this.sessions) {
            if (session.getID() > id) {
                throw new Exception("Session " + id + " kh\u00f4ng t\u1ed3n t\u1ea1i");
            }
            if (session.getID() == id) {
                return session;
            }
        }
        throw new Exception("Session " + id + " kh\u00f4ng t\u1ed3n t\u1ea1i");
    }
    
    public int getNumSession() {
        return this.sessions.size();
    }
}
