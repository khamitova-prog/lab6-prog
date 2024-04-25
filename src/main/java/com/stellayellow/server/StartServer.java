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
    public static int PORT = 50001;
    private static final Logger logger = LoggerServer.logger;

    private StartServer() {
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException  {
        int port;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.warning("Invalid port number: " + args[0]);
                port = 50001;
            }
        } else port = PORT;

        //получаем файл
        String fileName;
        if (args.length <2)  fileName = "resources/collection.xml";
        else fileName = args[1];
        FileManager fm = new FileManager(fileName);

        // создаем объект управления коллекцией
        CollectionManager cm = new CollectionManager(fm);

        // создаем объект управления командами
        CommandManager commandManager = new CommandManager(cm, fm);

        try {
            DatagramSocketServer server = new DatagramSocketServer(port, commandManager);
            server.run();
        } catch (SocketException e) {logger.warning("Could not start server: " + e.getMessage());
        } catch (IOException e) {
            logger.warning("Could not start server: " + e.getMessage());
        }

    }
}

