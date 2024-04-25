package com.stellayellow.server.commands;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;

import java.util.ArrayDeque;

/**
 * Класс, реализующий команду "script". Выводит последние 5 команд.
 */
public class CommandHistory extends Command {
    ArrayDeque<String> dq;

    public CommandHistory(ArrayDeque<String> deque) {
        super("history", "Вывести последние 5 команд (без их аргументов).");
        dq = deque;
    }

    /**
     * Реализует команду "script" и выводит список последних 5 выполненных команд.
      * @param req - объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        String output = dq.toString();

        return new Response("", "", output);
    }
}
