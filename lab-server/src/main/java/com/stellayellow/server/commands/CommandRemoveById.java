package com.stellayellow.server.commands;

import com.stellayellow.common.data.Route;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.*;

/**
 * Команда "remove_by_id". Удаляет элемент коллекции по его id.
 */
public class CommandRemoveById extends Command {
    CollectionManager collectionManager;

    public CommandRemoveById(CollectionManager collectionManager) {
        super("remove_by_id", "Удалить элемент из коллекции по его id.");
        this.collectionManager = collectionManager;
    }

    /**
     * Удаляет элемент коллекции по id
      * @param req - объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        String output;
        long id;

        if(collectionManager.getRouteCollection().isEmpty()) return new Response("", "", "Коллекция пуста.");

        try {
            id = Long.parseLong(req.getArgument());
        } catch (NumberFormatException  e) {
            output = "Ошибка ввода. Id должно быть целым числом.";
            return new Response("", "", output);
        }

        Route route = collectionManager.getRouteCollection().stream()
                .filter(route1 -> route1.getId() == id)
                .findFirst()
                .orElse(null);

        if (route != null) {
            collectionManager.getRouteCollection().remove(route);
            output = "Элемент с id=" + id + " удален из коллекции.";
        } else output = "Элемент с id " + req.getArgument() + " в коллекции не найден";
        return new Response("", "", output);
    }
}

