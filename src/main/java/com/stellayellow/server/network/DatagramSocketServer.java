package com.stellayellow.server.network;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CommandManager;
import com.stellayellow.server.utility.LoggerServer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;

public class DatagramSocketServer {
    private  final int serverPort ;
    CommandManager cm;
    Logger logger = LoggerServer.logger;
    ReceiveServer rs;
    HandlerServer hs;
    SendServer ss;

    public DatagramSocketServer(int port, CommandManager commandManager) throws IOException {
        this.serverPort = port;
        this.cm = commandManager;
    }

    public void run()  throws IOException, SocketException, InterruptedException, ClassNotFoundException {
        logger.info("Сервер запущен по адресу: " +(new InetSocketAddress("localhost", serverPort)));

        DatagramSocket server = new DatagramSocket(serverPort);

        boolean stateWorking = true;
        while (stateWorking) {
            rs = new ReceiveServer(server);
            Request request = rs.receiveRequest();

            if (request == null) {
                logger.info("Ошибка при получении запроса. receiveRequest is null");
                return;
            }

            logger.info("Получен запрос, содержащий команду: " + request.getCommand());

            hs = new HandlerServer(request);
            Response response = hs.handlerCommand(request, cm);
            logger.info("Результат обработки команды: " + response.getStatus());

            ss = new SendServer(rs.getInPacket(), server);
            ss.sendResponse(response);
            if (request.getCommand().equals("exit")) stateWorking = false;
        }

        server.close();
    }

}

