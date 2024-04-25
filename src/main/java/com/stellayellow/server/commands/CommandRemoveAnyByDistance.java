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
        float d;
        String output;
        try {
            d = Float.parseFloat(req.getArgument());
            Route route = null;

            for (Route r : collectionManager.getRouteCollection()) {
                if (r.getDistance() == d) route = r;
            }

            if (route != null) {
                collectionManager.getRouteCollection().remove(route);
                output = "Элемент коллекции успешно удален.";
                return new Response("", "", output);
            }
            output = "Ошибка удаления. Элемент с заданным distance в коллекции отсутствует.";
            return new Response("", "", output);
        } catch (NumberFormatException e) {
           output = "Ошибка ввода. Аргумент должен быть действительным числом";
           return new Response("", "", output);
        }
    }
}
