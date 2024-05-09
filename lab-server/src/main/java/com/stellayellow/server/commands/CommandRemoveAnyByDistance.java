package com.stellayellow.server.commands;

import com.stellayellow.common.data.Route;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CollectionManager;


/**
 * Класс, реализующий команду удаления из коллекции один элемент, значение поля distance которого эквивалентно заданному
 */
public class CommandRemoveAnyByDistance extends Command {
    CollectionManager collectionManager;

    public CommandRemoveAnyByDistance(CollectionManager collectionManager) {
        super("remove_any_by_distance", "удалить из коллекции один элемент, значение поля distance которого эквивалентно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Реализация команды удаления из коллекции одного элемента, значение поля distance которого эквивалентно заданному
     */
    @Override
    public Response execute(Request req) {
        if (collectionManager.getRouteCollection().isEmpty()) return new Response("","", "Коллекция пуста.");

        float d;
        String output;
        try {
            d = Float.parseFloat(req.getArgument());

            Route route = collectionManager.getRouteCollection().stream()
                    .filter(route1 -> route1.getDistance() == d)
                    .findFirst()
                    .orElse(null);

            if (route != null) {
                collectionManager.getRouteCollection().remove(route);
                output = "Элемент коллекции, поле distance которого равно  " + d + " успешно удален.";
                return new Response("", "", output);
            }
            output = "Ошибка удаления. Элемент с  distance  равным " + d + " в коллекции отсутствует.";
            return new Response("", "", output);
        } catch (NumberFormatException e) {
           output = "Ошибка ввода. Аргумент должен быть действительным числом";
           return new Response("", "", output);
        }
    }
}
