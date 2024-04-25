package com.stellayellow.server.commands;

import com.stellayellow.common.data.Route;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CollectionManager;

/**
 * Класс, реализующий команду вывести первый элемент коллекции и удалить его
 */
public class CommandRemoveHead extends Command {
    CollectionManager collectionManager;

    public CommandRemoveHead(CollectionManager collectionManager) {
        super("remove_head", "вывести первый элемент коллекции и удалить его");
        this.collectionManager = collectionManager;
    }

    /**
     * Реализация команды вывести первый элемент коллекции и удалить его
     * @param req - объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        Route route = collectionManager.getRouteCollection().pollFirst();

        String output = (route != null) ? route.toString() : "Коллекция пуста.";
        output += "/n";
        output += (route != null) ? "Элемент удален из коллекции." : "Удаление элемента не возможно.";
        return new Response("", "", output);
    }
}
