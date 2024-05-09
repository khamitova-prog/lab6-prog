package com.stellayellow.server.network;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CommandManager;
import com.stellayellow.server.utility.LoggerServer;

import java.util.Objects;
import java.util.logging.Logger;

public class HandlerServer {
    private final Logger logger;

    public HandlerServer() {
        this.logger = LoggerServer.logger;
    }

    public Response handlerCommand(Request request, CommandManager cm) {
        logger.info("Старт обработчика команды.");
        if (Objects.isNull(request)) return new Response("", "", "Ошибка при получении запроса.");

        return cm.consoleMode(request);
    }
}
