package com.stellayellow.server.commands;

import com.stellayellow.common.data.Route;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CollectionManager;

/**
 * Команда удаления первого элемента коллекции
 */
public class CommandRemoveFirst extends Command {
    CollectionManager collectionManager;

    public CommandRemoveFirst(CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Реализует удаление первого элемента.
     * @param req - объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        Route route = collectionManager.getRouteCollection().pollFirst();
        String  output = (route != null) ? "Первый элемент коллекции удален успешно." : "Коллекция пуста. Удаление элемента коллекции не возможно.";
        return new Response("", "", output);
    }
}
