package com.stellayellow.server.commands;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.*;
import com.stellayellow.common.data.*;

import java.util.ArrayDeque;
import java.util.Objects;

/**
 * Класс, реализующий команду "update id". Обновляет значения элемента коллекции по id.
 */
public class CommandUpdate extends Command {
    private final CollectionManager collectionManager;
    private final ArrayDeque<Route> collection;

    public CommandUpdate(CollectionManager collectionManager) {
        super("update", "Обновить значение элемента коллекции, id которого равен заданному.");
        this.collectionManager = collectionManager;
        this.collection = collectionManager.getRouteCollection();
    }

    /**
     * Реализация обновления элемента по id
     * @param req - объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        long id;
        Route route;
        String output;

        if (collection.isEmpty()) {
            output = "Коллекция пуста.";
            return new Response("", "", output);
        }

        try {
            id = Long.parseLong(req.getArgument());
        } catch (NumberFormatException e) {
            output = "Ошибка ввода. Id должно быть целым числом.";
            return new Response("", "", output);
        }

        if (req.getRoute() == null) {
            route = collectionManager.getById(id);
            output = (Objects.isNull(route)) ? "Элемент с введенным id в коллекции отсутствует." : "";
            return new Response(((output.isEmpty()) ? "update" : ""), req.getArgument(), output);
        } else {
            route = collectionManager.getById(id);
            collection.remove(route);
            route = req.getRoute();
            route.setId(id);
            collection.add(route);
            output = "Элемент коллекции успешно обновлен. " +route;
            return new Response("", "", output);
        }
    }
}

