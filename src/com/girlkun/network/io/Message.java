// 
// Decompiled by Procyon v0.5.36
// 
package com.girlkun.network.io;

import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

public class Message implements IAdvanceIOMessage {

    public byte command;
    private ByteArrayOutputStream os;
    private DataOutputStream dos;
    private ByteArrayInputStream is;
    private DataInputStream dis;

    public Message(final int command) {
        this((byte) command);
    }

    public Message(final byte command) {
        this.command = command;
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.os);
    }

    public Message(final byte command, final byte[] data) {
        this.command = command;
        this.is = new ByteArrayInputStream(data);
        this.dis = new DataInputStream(this.is);
    }

    @Override
    public DataOutputStream writer() {
        return this.dos;
    }

    @Override
    public DataInputStream reader() {
        return this.dis;
    }

    @Override
    public byte[] getData() {
        return this.os.toByteArray();
    }

    @Override
    public void cleanup() {
        try {
            if (this.is != null) {
                this.is.close();
            }
            if (this.os != null) {
                this.os.close();
            }
            if (this.dis != null) {
                this.dis.close();
            }
            if (this.dos != null) {
                this.dos.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        this.cleanup();
        this.dis = null;
        this.is = null;
        this.dos = null;
        this.os = null;
    }

    @Override
    public int read() throws IOException {
        return this.reader().read();
    }

    @Override
    public int read(final byte[] b) throws IOException {
        return this.reader().read(b);
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        return this.reader().read(b, off, len);
    }

    @Override
    public boolean readBoolean() throws IOException {
        return this.reader().readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return this.reader().readByte();
    }

    @Override
    public short readShort() throws IOException {
        return this.reader().readShort();
    }

    @Override
    public int readInt() throws IOException {
        return this.reader().readInt();
    }

    @Override
    public long readLong() throws IOException {
        return this.reader().readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return this.reader().readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return this.reader().readDouble();
    }

    @Override
    public char readChar() throws IOException {
        return this.reader().readChar();
    }

    @Override
    public String readUTF() throws IOException {
        return this.reader().readUTF();
    }

    @Override
    public void readFully(final byte[] b) throws IOException {
        this.reader().readFully(b);
    }

    @Override
    public void readFully(final byte[] b, final int off, final int len) throws IOException {
        this.reader().readFully(b, off, len);
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return this.reader().readUnsignedByte();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return this.reader().readUnsignedShort();
    }

    @Override
    public void write(final byte[] b) throws IOException {
        this.writer().write(b);
    }

    @Override
    public void write(final int b) throws IOException {
        this.writer().write(b);
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.writer().write(b, off, len);
    }

    @Override
    public void writeBoolean(final boolean v) throws IOException {
        this.writer().writeBoolean(v);
    }

    @Override
    public void writeByte(final int v) throws IOException {
        this.writer().writeByte(v);
    }

    @Override
    public void writeBytes(final String s) throws IOException {
        this.writer().writeBytes(s);
    }

    @Override
    public void writeChar(final int v) throws IOException {
        this.writer().writeChar(v);
    }

    @Override
    public void writeChars(final String s) throws IOException {
        this.writer().writeChars(s);
    }

    @Override
    public void writeDouble(final double v) throws IOException {
        this.writer().writeDouble(v);
    }

    @Override
    public void writeFloat(final float v) throws IOException {
        this.writer().writeFloat(v);
    }

    @Override
    public void writeInt(final int v) throws IOException {
        this.writer().writeInt(v);
    }

    @Override
    public void writeLong(final long v) throws IOException {
        this.writer().writeLong(v);
    }

    @Override
    public void writeShort(final int v) throws IOException {
        this.writer().writeShort(v);
    }

    @Override
    public void writeUTF(final String str) throws IOException {
        this.writer().writeUTF(str);
    }

    @Override
    public BufferedImage readImage() throws IOException {
        final int size = this.readInt();
        final byte[] dataImage = new byte[size];
        this.read(dataImage);
        final BufferedImage image = ImageIO.read(new ByteArrayInputStream(dataImage));
        return image;
    }

    @Override
    public void writeImage(final BufferedImage image, final String format) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        final byte[] dataImage = baos.toByteArray();
        this.writeInt(dataImage.length);
        this.write(dataImage);
    }
}
