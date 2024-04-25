package com.stellayellow.server.commands;

import com.stellayellow.common.data.*;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.*;

/**
 * Класс, реализующий добавление нового элемента в коллекцию.
 */
public class CommandAdd extends Command {
    private final CollectionManager collectionManager;

    public CommandAdd(CollectionManager collectionManager) {
        super("add {element} ", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Реализует команду «add».
     * @param req - объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        if (req.getRoute() == null)  return  new Response("add", "", "script");

            Route route = req.getRoute();
            String output;
            route .setId(collectionManager.createId());
            output = (collectionManager.getRouteCollection().add(route)) ? route.getName() + " успешно  добавлен в коллекцию.\n" : "ошибка при добавлении в коллекцию.\n";
            return new Response("", "", output);
    }
}

