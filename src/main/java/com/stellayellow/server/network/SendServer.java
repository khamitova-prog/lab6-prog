package com.stellayellow.server.network;

import com.stellayellow.server.utility.LoggerServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

public class SendServer {
    private final Logger logger;
    private final DatagramPacket inPacket;
    private final DatagramSocket socket;

    public SendServer(DatagramPacket packet, DatagramSocket socket) {
        this.logger = LoggerServer.logger;
        this.inPacket = packet;
        this.socket = socket;
    }

    public void sendResponse(Serializable mess) throws IOException, ClassNotFoundException, InterruptedException {
        int defaultSleepTime = 500;
        byte[] array = serialize(mess);
        DatagramPacket outPacket = new DatagramPacket(array, array.length, inPacket.getAddress(), inPacket.getPort());
        byte[] sizeArray = serialize(array.length);
        DatagramPacket sizeOutputPacket = new DatagramPacket(sizeArray, sizeArray.length, inPacket.getAddress(), inPacket.getPort());
        socket.send(sizeOutputPacket); //81
        socket.send(outPacket);
        Thread.sleep(defaultSleepTime);
    }

    public byte[] serialize(Serializable mess) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(mess);
        return out.toByteArray();
    }

}

