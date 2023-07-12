// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.io;

import java.io.IOException;
import java.awt.image.BufferedImage;

public interface IAdvanceIOMessage extends IMessage
{
    BufferedImage readImage() throws IOException;
    
    void writeImage(final BufferedImage p0, final String p1) throws IOException;
}
