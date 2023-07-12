// 
// Decompiled by Procyon v0.5.36
// 
package com.girlkun.network.example;

import java.io.DataOutputStream;
import com.girlkun.network.io.Message;
import java.io.DataInputStream;
import com.girlkun.network.session.ISession;
import com.girlkun.network.handler.IMessageSendCollect;

public class DFSendCollect implements IMessageSendCollect {

    @Override
    public Message readMessage(final ISession session, final DataInputStream dis) throws Exception {
        final byte cmd = dis.readByte();
        final int size = dis.readInt();
        final byte[] data = new byte[size];
        for (int len = 0, byteRead = 0; len != -1 && byteRead < size; byteRead += len) {
            len = dis.read(data, byteRead, size - byteRead);
            if (len > 0) {
            }
        }
        return new Message(cmd, data);
    }

    @Override
    public byte readKey(final ISession session, final byte b) {
        return -1;
    }

    @Override
    public void doSendMessage(final ISession session, final DataOutputStream dos, final Message msg) throws Exception {
        try {
            final byte[] data = msg.getData();
            dos.writeByte(msg.command);
            if (data != null) {
                dos.writeInt(data.length);
                dos.write(data);
            } else {
                dos.writeInt(0);
            }
            dos.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public byte writeKey(final ISession session, final byte b) {
        return -1;
    }
}
