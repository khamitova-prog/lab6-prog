package com.stellayellow.client;

import com.stellayellow.client.inputCommand.UserInput;
import com.stellayellow.client.io.ConsoleInputManager;
import com.stellayellow.client.network.DatagramChannelClient;
import com.stellayellow.client.network.LoggerClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class StartClient {
    public static final int PORT = 50001;
    public static final String HOST = "localhost";
    private StartClient() {
    }

    public static void main(String[] args) {
        String host = "";
        int port = 0;

            if (args.length == 2) {
                host = args[0];
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    port = PORT;
                }
            }
            if (args.length == 1) {
                host =args[0];
                port = PORT;
            }
            if (args.length < 1) {
                host = HOST;
                port = PORT;
            }

        ConsoleInputManager cm = new ConsoleInputManager();
        UserInput ui = new UserInput(cm);
        try {
            DatagramChannelClient c = new DatagramChannelClient(InetAddress.getByName(host), port, ui);

            c.sendAndReceive();
        } catch (UnknownHostException e) {
            LoggerClient.logger.info("Error host ");
        } catch (IOException e) {
            LoggerClient.logger.info("Network error ");
        }
    }
}

