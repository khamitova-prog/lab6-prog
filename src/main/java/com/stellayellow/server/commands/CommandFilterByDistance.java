package com.stellayellow.server.commands;

import com.stellayellow.common.data.Route;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CollectionManager;

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
        float d;
        String  output = "";
        boolean b = false;
        try {
            d = Float.parseFloat(req.getArgument());

            for (Route r : collectionManager.getRouteCollection()) {
                if (r.getDistance() == d) {
                    output += r.toString();
                    output += "/n";
                    b = true;
                }
            }

            if (!b) output = "Элементы с заданным distance в коллекции отсутствуют.";
            return new Response("", "", output);

        } catch (NumberFormatException e) {
            output = "Ошибка ввода. Аргумент должен быть действительным числом.";
            return new Response("", "", output);
        }
    }
}
