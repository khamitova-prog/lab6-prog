package com.stellayellow.server.commands;

import com.stellayellow.common.exceptions.RecursiveScriptExecuteException;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.utility.LoggerServer;

import java.nio.file.*;
import java.util.Stack;
import java.util.logging.Logger;


/**
 * "execute_script" command. Runs a file with commands.
 */
public class CommandExecuteScript extends Command {
    private final Stack<String> stack = new Stack<>();

    public CommandExecuteScript() {
        super("execute_script", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }

    /**
     * Запускает файл с командами.
     * @param req - объект запроса
     * @return объект ответа
     */
    @Override
public Response execute(Request req) {
        Logger logger = LoggerServer.logger;

            Path path = Paths.get (req.getArgument());
            if (!Files.exists(path)) return new Response("", "", "скрипт с именем " + req.getArgument() + " не существует");
        if (!Files.isReadable(path)) return new Response("", "", "Скрипт не удается прочитать.");

          String output = "Выполняется скрипт из файла " + req.getArgument() + ". \n";
        logger.info(output);

        try{
            if (stack.contains(req.getArgument())) throw new RecursiveScriptExecuteException();
            stack.push(req.getArgument());
        } catch (RecursiveScriptExecuteException e)  {
            stack.clear();
            output = "Выполнение скрипта execute_script " + req.getCommand() + " прервано.";
            logger.info(output + e.getMessage());
            return new Response("", "", output);
        }

            return  new Response(req.getCommand(), req.getArgument(), output);
    }

    public Stack<String> getStack() {
        return stack;
    }
}

