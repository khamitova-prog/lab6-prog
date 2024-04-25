package com.stellayellow.server.commands;

import com.stellayellow.common.data.Route;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CollectionManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс, реализующий команду вывода значения поля distance всех элементов в порядке убывания
 */
public class CommandPrintFieldDescendingDistance extends Command {
    CollectionManager collectionManager;

    public CommandPrintFieldDescendingDistance(CollectionManager collectionManager) {
        super("print_field_descending_distance", "вывести значения поля distance всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Реализация вывода значения поля distance всех элементов коллекции в порядке убывания
     * @param req - объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        ArrayList<Float> list = new ArrayList<>();
        for (Route r : collectionManager.getRouteCollection()) {
            list.add(r.getDistance());
        }

        Collections.reverse(list);
        String output = list.toString();
        return new Response("", "", output);
    }
}
