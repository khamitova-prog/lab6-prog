package com.stellayellow.server.commands;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.*;
import com.stellayellow.server.utility.LoggerServer;


/**
 * Класс, реализующий команду очистить коллекцию.
 */
public class CommandClear extends Command {
    CollectionManager collectionManager;

    public CommandClear(CollectionManager collectionManager) {
        super("clear", "Очистить коллекцию.");
        this.collectionManager = collectionManager;
    }

    /**
     * Реализует очищение коллекции.
      * @param req - объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        String output;
        try {
            collectionManager.getRouteCollection().clear();
            output = "Коллекция очищена.";
        } catch (Exception e) {
            LoggerServer.logger.info("Ошибка при выполнении команды clear " + e.getMessage());
return  new Response("", "", "Ошибка при выполнении команды clear.");
        }
        return new Response("", "", output);
    }
}
