// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.server;

public interface IGirlkunServer extends Runnable
{
    IGirlkunServer init();
    
    IGirlkunServer start(final int p0) throws Exception;
    
    IGirlkunServer setAcceptHandler(final ISessionAcceptHandler p0);
    
    IGirlkunServer close();
    
    IGirlkunServer dispose();
    
    IGirlkunServer randomKey(final boolean p0);
    
    IGirlkunServer setDoSomeThingWhenClose(final IServerClose p0);
    
    IGirlkunServer setTypeSessioClone(final Class p0) throws Exception;
    
    ISessionAcceptHandler getAcceptHandler() throws Exception;
    
    boolean isRandomKey();
    
    void stopConnect();
}
