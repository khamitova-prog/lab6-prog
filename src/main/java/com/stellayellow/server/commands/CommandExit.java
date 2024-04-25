package com.stellayellow.server.commands;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;

/**
 * Класс, реализующий команду "exit". Выход без сохранения.
 */
public class CommandExit extends Command {

    public CommandExit() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * Реализует команду выхода без сохранения.
      * @param req - объект запроса клиента
     * @return объект ответа
     */
    @Override
    public Response execute(Request req) {
        String  output = "Клиент завершает работу.";

            return new Response("exit", "", output);
    }
}
