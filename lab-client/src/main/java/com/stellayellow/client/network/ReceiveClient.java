package com.stellayellow.client.network;

import com.stellayellow.common.swap.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.logging.Logger;

public class ReceiveClient {
    private final Logger logger;
    private static final int SIZE_PACKET = 512;

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

    public Response receiveResponse(DatagramChannel channel) throws IOException, ClassNotFoundException {
        boolean received = false;
        int numberOfPackets = 0;
        int indexOfChunk;
        byte[][] result = null;
        int marker = 0;
        byte[] data;

        while(!received) {
            data = receiveData(channel);

            if ((marker > 0) && (numberOfPackets != data[data.length - 1])) return new Response("", "", "Ошибка при получении ответа сервера.");

            numberOfPackets = data[data.length - 1];
            indexOfChunk = data[data.length - 5];
            logger.info("Получен кусок данных с индексом " + indexOfChunk + ", число пакетов: " + numberOfPackets);

            if (marker == 0) result = new byte[numberOfPackets][SIZE_PACKET - 8];
            marker++;
            result[indexOfChunk] =  Arrays.copyOf(data, data.length - 8);

            if (numberOfPackets == marker) {
                received = true;
                logger.info("Получение данных окончено");
            }
        }

        byte[] receiveData = new byte[SIZE_PACKET * numberOfPackets];
        ByteBuffer buffer = ByteBuffer.wrap(receiveData);

        for (int i = 0; i < numberOfPackets; i++) {
            buffer.put(result[i]);
        }
        receiveData = buffer.array();

        return (Response) deserialize(receiveData);
    }

    private byte[] receiveData(DatagramChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(SIZE_PACKET);
        SocketAddress address = null;
        while(address == null) {
            address = channel.receive(buffer);
        }
        return buffer.array();
    }


}

