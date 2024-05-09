package com.stellayellow.server;

import com.stellayellow.server.managers.CollectionManager;
import com.stellayellow.server.managers.CommandManager;
import com.stellayellow.server.managers.FileManager;
import com.stellayellow.server.network.DatagramSocketServer;
import com.stellayellow.server.utility.LoggerServer;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

public final class StartServer {
    public static final int PORT = 50001;
    public static final String FILE_NAME = "resources/collection.xml";
    private static final Logger logger = LoggerServer.logger;

    private StartServer() {
    }

    public static void main(String[] args) {
        int port = 0;
        String fileName = "";


        if (args.length > 1) {
            fileName = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                logger.warning("Invalid port number: " + args[0]);
                port = PORT;
            }
        }
        if (args.length == 1) {
            port = PORT;
            fileName = args[0];
        }
        if (args.length < 1) {
            port = PORT;
            fileName = FILE_NAME;
        }

        FileManager fm = new FileManager(fileName);
        CollectionManager cm = new CollectionManager(fm);
        CommandManager commandManager = new CommandManager(cm);

        try {
            DatagramSocketServer server = new DatagramSocketServer(port, commandManager);
            server.run();
            cm.saveCollection();
            logger.info("Коллекция сохранена в файл.");
        } catch (SocketException e) {logger.warning("Не удалось запустить сервер, проблемы сокета: " + e.getMessage());
        } catch (IOException e) {
            logger.warning("Не удалось запустить сервер, проблемы с соединением: " + e.getMessage());
        }

    }
}

