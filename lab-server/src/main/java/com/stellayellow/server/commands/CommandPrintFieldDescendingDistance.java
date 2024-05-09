package com.stellayellow.server.commands;

import com.stellayellow.common.data.Route;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CollectionManager;

import java.util.Comparator;
import java.util.stream.Collectors;

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
        if (collectionManager.getRouteCollection().isEmpty()) return new Response("", "", "Коллекция пуста.");

        String  output = "значения поля distance всех элементов в порядке убывания:\n";
        output += collectionManager.getRouteCollection().stream()
                .map(Route :: getDistance)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList()).toString();

        return new Response("", "", output);
    }
}
