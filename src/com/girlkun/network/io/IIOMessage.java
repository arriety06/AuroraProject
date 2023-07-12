// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.network.io;

import java.io.IOException;

public interface IIOMessage
{
    int read() throws IOException;
    
    int read(final byte[] p0) throws IOException;
    
    int read(final byte[] p0, final int p1, final int p2) throws IOException;
    
    boolean readBoolean() throws IOException;
    
    byte readByte() throws IOException;
    
    short readShort() throws IOException;
    
    int readInt() throws IOException;
    
    long readLong() throws IOException;
    
    float readFloat() throws IOException;
    
    double readDouble() throws IOException;
    
    char readChar() throws IOException;
    
    String readUTF() throws IOException;
    
    void readFully(final byte[] p0) throws IOException;
    
    void readFully(final byte[] p0, final int p1, final int p2) throws IOException;
    
    int readUnsignedByte() throws IOException;
    
    int readUnsignedShort() throws IOException;
    
    void write(final byte[] p0) throws IOException;
    
    void write(final int p0) throws IOException;
    
    void write(final byte[] p0, final int p1, final int p2) throws IOException;
    
    void writeBoolean(final boolean p0) throws IOException;
    
    void writeByte(final int p0) throws IOException;
    
    void writeBytes(final String p0) throws IOException;
    
    void writeChar(final int p0) throws IOException;
    
    void writeChars(final String p0) throws IOException;
    
    void writeDouble(final double p0) throws IOException;
    
    void writeFloat(final float p0) throws IOException;
    
    void writeInt(final int p0) throws IOException;
    
    void writeLong(final long p0) throws IOException;
    
    void writeShort(final int p0) throws IOException;
    
    void writeUTF(final String p0) throws IOException;
}
