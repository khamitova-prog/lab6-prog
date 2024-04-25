package com.stellayellow.server.network;

import com.stellayellow.common.swap.Request;
import com.stellayellow.server.utility.LoggerServer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

public class ReceiveServer {
    DatagramSocket socketServer;
    DatagramPacket inPacket;
    Logger logger;


    public ReceiveServer(DatagramSocket socketServer) {
        this.socketServer = socketServer;
        this.logger = LoggerServer.logger;
    }

    public Request receiveRequest() throws IOException, ClassNotFoundException {
        int defaultSizeBuffer = 256;
    try {
        byte[] sizeReceive = new byte[defaultSizeBuffer];
        inPacket = new DatagramPacket(sizeReceive, defaultSizeBuffer);
        socketServer.receive(inPacket);
        int size = (int) deserialize(sizeReceive);
        byte[] requestReceive = new byte[size];
        inPacket = new DatagramPacket(requestReceive, size);
        socketServer.receive(inPacket);
        return (Request) deserialize(inPacket.getData());
    } catch (SocketTimeoutException e) {
        return null;
    }
}

    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(input);
        Object obj = ois.readObject();

        ois.close();
        input.close();
        return obj;
    }

    public DatagramPacket getInPacket() {
        return inPacket;
    }
}

