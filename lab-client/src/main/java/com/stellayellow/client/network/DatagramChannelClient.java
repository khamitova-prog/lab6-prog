package com.stellayellow.client.network;

import com.stellayellow.client.inputCommand.UserInput;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
//import java.nio.CharBuffer;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
//import java.nio.charset.Charset;
import java.util.logging.Logger;

public class DatagramChannelClient {
    private final Logger logger;
    private Request request;
    private Response response;
    private final DatagramChannel channel;
    private  final SocketAddress server;
    private final UserInput ui;


    public DatagramChannelClient(InetAddress address, int port, UserInput userInput) throws IOException {
        this.logger = LoggerClient.logger;
        this.request = new Request("", "", null);
        this.response = new Response("", "", "");
        this.server = new InetSocketAddress(address, port);
        this.channel = DatagramChannel.open().bind(null).connect(server);
        this.channel.configureBlocking(false);
        this.ui = userInput;
    }

    public void sendAndReceive() {

        boolean stateWorking = true;
        while (stateWorking) {
        request = ui .getRequest(response);

            try {
                logger.info("start sendClient ");
                (new SendClient()).sendRequest(request, channel, server);
            } catch (IOException e) {
                logger.warning("Ошибка. Сообщение не отправлено из-за проблем с связью с сервером.");
        }
            try {
                response = (new ReceiveClient()).receiveResponse(channel);
            } catch (IOException e) {
                logger.warning("network error: " + (e.getMessage() != null ? e.getMessage() : ""));
//            } catch (InterruptedException e) {
//                logger.warning("Ошибка прерывания.");
            } catch (ClassNotFoundException e) {
                logger.warning("Ошибка приведения.");
            }

            System.out.println(response.getStatus());
            if (request.getCommand().equals("exit")) stateWorking = false;
        }
    }
}

