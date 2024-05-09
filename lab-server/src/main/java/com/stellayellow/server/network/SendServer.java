package com.stellayellow.server.network;

import com.stellayellow.common.swap.Response;
import com.stellayellow.server.utility.LoggerServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Logger;

public class SendServer {
    private final Logger logger;
    private final DatagramPacket inPacket;
    private static final int SIZE_PACKET = 512;
    private static final int SIZE_DATA = SIZE_PACKET - 8;
    private final DatagramSocket socket;

    public SendServer(DatagramPacket packet, DatagramSocket socket) {
        this.logger = LoggerServer.logger;
        this.inPacket = packet;
        this.socket = socket;
    }

    private byte[] serialize(Serializable mess) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(mess);
        return out.toByteArray();
    }

    public void sendResponse(Response response) throws IOException {
        byte[] data = serialize(response);

        byte[][] ret = splitOfArrayData(data);
        logger.info("Отправляется " + ret.length + " кусков response...");

        byte[] numberOfChunks = intToByteArray(ret.length);

        for(int i = 0; i < ret.length; i++) {
            byte[] sendChunk = new byte[SIZE_PACKET];
            byte[] chunk = ret[i];

            byte[] chunkNumber = intToByteArray(i);

            // Объединяем три массива данные, номер куска, число кусков в sendChunk,
            // запаковав результат объединения  отправляем пакет с куском данных клиенту
            ByteBuffer buffer = ByteBuffer.wrap(sendChunk);
            buffer.put(chunk);
            buffer.put(chunkNumber);
            buffer.put(numberOfChunks);
            sendChunk = buffer.array();

            DatagramPacket dp = new DatagramPacket(sendChunk, SIZE_PACKET, inPacket.getAddress(), inPacket.getPort());
            socket.send(dp);
            if (i < (ret.length - 1)) logger.info("Кусок размером " + chunk.length + " отправлен на сервер.");
            else logger.info("Последний кусок размером " + chunk.length + " отправлен на сервер.");
        }

        logger.info("Отправка данных завершена");
    }

    /**
     * Метод, делящий байтовый массив данных на куски.
     * Размер куска данных равен SIZE_DATA.
     * @param data - массив данных
     * @return - двумерный байтовый массив,размерность которого равна числу кусков, содержащих данные.
     * */
    private byte[][] splitOfArrayData(byte[] data) {
        byte[][] ret = new byte[data.length / SIZE_DATA + 1][SIZE_DATA];

        int start = 0;
        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(data, start, start + SIZE_DATA);
            start += SIZE_DATA;
        }

        return ret;
    }

    /**
     * Преобразует целое число в массив байтов.
     * @param i - число, которое надо представить в виде массива.
     * @return байтовый массив длиной 4 байта.
     */
    private byte[] intToByteArray(int i) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(i);
        buffer.rewind();
        return buffer.array();
    }

}

