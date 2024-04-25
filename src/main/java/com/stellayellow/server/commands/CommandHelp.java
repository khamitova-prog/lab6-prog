package com.stellayellow.server.commands;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.managers.CommandManager;

/**
 * Класс, реализующий команду "help". Выводит список доступных команд.
 */
public class CommandHelp extends Command {
    private final CommandManager commandManager;

    public CommandHelp(CommandManager commandManager) {
        super("help", "Вывести справку по доступным командам.");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder output = new StringBuilder("доступны команды:\n");

        commandManager.getMap().values().forEach(command -> {
            output.append(String.format(" %-35s%-1s%n", command.getName(), command.getDescription()));
        });

        return new Response("", "", output.toString());
    }
}

