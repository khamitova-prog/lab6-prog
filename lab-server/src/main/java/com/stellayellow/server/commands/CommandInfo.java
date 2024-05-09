package com.stellayellow.server.commands;

import java.time.*;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CollectionManager;

/**
 * Класс, реализующий команду «info». Выводит информацию о коллекции.
 */
public class CommandInfo extends Command {
    private final CollectionManager collectionManager;

    public CommandInfo(CollectionManager collectionManager) {
        super("info", "Вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.).");
        this.collectionManager = collectionManager;
    }

    /**
     * Реализует команду "info".
     * @param req - объект запроса клиента
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        String lit = (lastInitTime == null) ? "В данной сессии инициализация еще не происходила.":
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lst = (lastSaveTime == null) ? "В данной сессии сохранение еще не происходило.":
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

        String output = "Сведения о коллекции: " + "\n" +
        "тип: " + collectionManager.getRouteCollection().getClass().getName() + "\n" +
        "количество элементов: " + collectionManager.getRouteCollection().size() + "\n" +
        "дата последнего сохранения: " + lst + "\n" +
        "дата последней инициализации: " + lit + "\n";

        return new Response("", "", output);
    }
}
