package com.stellayellow.server.commands;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CollectionManager;

import java.util.stream.Collectors;

/**
 * Класс, реализующий команду вывести элементы, значение поля distance которых равно заданному
 */
public class CommandFilterByDistance extends Command {
    CollectionManager collectionManager;

    public CommandFilterByDistance(CollectionManager collectionManager) {
        super("filter_by_distance", "вывести элементы, значение поля distance которых равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Реализация команды вывести элементы, значение поля distance которых равно заданному
     * @param req - объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        if (collectionManager.getRouteCollection().isEmpty()) return new Response("", "", "Коллекция пуста.");

        float d;
        String  output;
        try {
            d = Float.parseFloat(req.getArgument());

            output = collectionManager.getRouteCollection().stream()
                    .filter(r  -> r.getDistance() == d)
                    .collect(Collectors.toList()).toString();
            if (output.equals("[]")) output = "Элиементы с значением distance равным " + d + " в коллекции не найдены.";
            else output = "элементы, значение поля distance которых равно " + d + " :\n" + output;
            return new Response("", "", output);

        } catch (NumberFormatException e) {
            output = "Ошибка ввода. Аргумент должен быть действительным числом.";
            return new Response("", "", output);
        }
    }
}
