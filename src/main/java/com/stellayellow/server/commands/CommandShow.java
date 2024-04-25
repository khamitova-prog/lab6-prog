package com.stellayellow.server.commands;

import com.stellayellow.common.data.Route;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, реализующий команду «show». Показывает информацию обо всех элементах коллекции.
 */
public class CommandShow extends Command {
    CollectionManager collectionManager;

    public CommandShow(CollectionManager collectionManager) {
        super("show", "Вывести в стандартный поток вывода все элементы коллекции.");
        this.collectionManager = collectionManager;
    }

    /**
     * Реализует команду show.
     * @param req - объект запроса клиента
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        List<Route> sortCollection = new ArrayList<>(collectionManager.getRouteCollection()).stream()
                .sorted((Route r1, Route r2) -> r1.getName().compareTo(r2.getName()))
                .collect(Collectors.toList());

        StringBuilder str = new StringBuilder();
        sortCollection.forEach(route -> str.append(route.toString()));
        String output = "Элементы коллекции:\n" + str;

        return new Response("", "", output);
    }
}
