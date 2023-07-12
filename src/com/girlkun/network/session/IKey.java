// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.session;

import com.girlkun.network.io.Message;

public interface IKey
{
    void sendKey() throws Exception;
    
    void setKey(final Message p0) throws Exception;
    
    void setKey(final byte[] p0);
    
    byte[] getKey();
    
    boolean sentKey();
    
    void setSentKey(final boolean p0);
}
