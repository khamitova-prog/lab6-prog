package com.stellayellow.server.network;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CommandManager;
import com.stellayellow.server.utility.LoggerServer;

import java.util.logging.Logger;

public class HandlerServer {
    private final Logger logger;
    private Response response;
    private final Request request;

    public HandlerServer(Request request) {
        this.request = request;
        this.logger = LoggerServer.logger;
    }

    public Response handlerCommand(Request request, CommandManager cm) {
        logger.info("Старт обработчика команды.");
        request = request;
        // выполнение программы
        response = cm.consoleMode(request);
        return response;
    }
}
