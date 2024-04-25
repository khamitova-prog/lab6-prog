package com.stellayellow.client.network;

import com.stellayellow.common.swap.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.logging.Logger;

public class ReceiveClient {
    private final Logger logger;
    private final int defaultBufferSize = 512;
    private final int defaultSleepTime = 500;

    public ReceiveClient() {
        this.logger = LoggerClient.logger;
    }
    public Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(input);
        Serializable mess = (Serializable) ois.readObject();
        input.close();
        ois.close();
        return mess;
    }

    public Response receiveManager(DatagramChannel channel) throws IOException, ClassNotFoundException, InterruptedException {
        byte[] sizeReceive = new byte[defaultBufferSize];
        ByteBuffer receiveBufferSize = ByteBuffer.wrap(sizeReceive);

        SocketAddress server = null;
        while (server == null) {
            server = channel.receive(receiveBufferSize);
        }

        Response response;
        if (Objects.nonNull(server)) {
            Serializable rm = deserialize(sizeReceive);
            if (rm.getClass().equals(Integer.class)) {
                int size = (int) rm;
                Thread.sleep(defaultSleepTime);
                byte[] array = new byte[size];
                ByteBuffer receiveBuffer = ByteBuffer.wrap(array);
                channel.receive(receiveBuffer);
                rm = deserialize(array);
                response = (Response) rm;
                logger.info("Получено сообщение:" + response.getStatus());
            } else {
                response = (Response) rm;
            }
            return response;
        } else {
            return null;
        }
    }

}

