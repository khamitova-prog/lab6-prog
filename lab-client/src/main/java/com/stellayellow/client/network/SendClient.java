package com.stellayellow.client.network;

import com.stellayellow.common.swap.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class SendClient {
    private final Logger logger;

    public SendClient() {
        this.logger = LoggerClient.logger;
    }

    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        byte[] outMess = out.toByteArray();
        out.close();
        os.close();
        return outMess;
    }

    public void sendRequest(Request request, DatagramChannel channel, SocketAddress server) throws IOException {
        byte[] array = serialize(request);
        byte[] sizeArray = serialize(array.length);

        int sendSize = sizeArray.length;
        ByteBuffer bufferSendSize = ByteBuffer.wrap(sizeArray);

        int limit = 100;
        while (channel.send(bufferSendSize, server) < sendSize) {
            limit -= 1;
            if (limit == 0) {
                logger.info("Client couldn't send message");
                return;
            }
        }
        bufferSendSize.clear();

        ByteBuffer bufferSendRequest = ByteBuffer.wrap(array);
        sendSize = array.length;
        limit = 100;
        while (channel.send(bufferSendRequest, server) < sendSize) {
            limit -= 1;
            logger.info("could not sent a package, re-trying");
            if (limit == 0) {
                logger.info("Client couldn't send message");
                return;
            }
        }
        bufferSendRequest.clear();
        logger.info("Send result of request to server: " + request.getCommand());
    }



}


