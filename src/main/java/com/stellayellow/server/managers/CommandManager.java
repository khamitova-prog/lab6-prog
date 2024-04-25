package com.stellayellow.server.managers;

import com.stellayellow.client.io.InputManager;
import com.stellayellow.common.exceptions.EmptyStringException;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;
import com.stellayellow.server.utility.LoggerServer;
import com.stellayellow.server.commands.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * Управляет командами
 */
public class CommandManager {
    private final Logger logger = LoggerServer.logger;

    private final Map<String, Command> map;
    private InputManager inputManager;
    private final ArrayDeque<String> deque = new ArrayDeque<>();

    private boolean isScript;

    public CommandManager(CollectionManager collectionManager, FileManager fileManager) {
        logger.info("загружено управление командами.");

        map = new HashMap<>();
        addCommand("help", new CommandHelp(this));
        addCommand("info", new CommandInfo(collectionManager));
        addCommand("show", new CommandShow(collectionManager));
        addCommand("add", new CommandAdd(collectionManager));
        addCommand("update", new CommandUpdate(collectionManager));
        addCommand("remove_by_id", new CommandRemoveById(collectionManager));
        addCommand("clear", new CommandClear(collectionManager));
        addCommand("execute_script", new CommandExecuteScript());
        addCommand("exit", new CommandExit());
        addCommand("remove_first", new CommandRemoveFirst(collectionManager));
        addCommand("remove_head", new CommandRemoveHead(collectionManager));
        addCommand("history", new CommandHistory(deque));
        addCommand("remove_any_by_distance", new CommandRemoveAnyByDistance(collectionManager));
        addCommand("filter_by_distance", new CommandFilterByDistance(collectionManager));
        addCommand("print_field_descending_distance", new CommandPrintFieldDescendingDistance(collectionManager));
    }

    /**
     * Создает объект команды и добавляет имя команды как ключ, саму команду как значение в словарь map.
      * @param key строка с именем команды
     * @param command - объект команды
     */
    public void addCommand(String key, Command command) {
        map.put(key, command);
    }

    /**
     * Режим работы с командами в консоли
      */
    public Response consoleMode(Request request) {
        String command = request.getCommand();
        logger.info("получен на обработку запрос с камандой: " + request.getCommand() + " и аргументом: " + ((request.getArgument().isEmpty()) ? " аргуменнт отсутствует " : request.getArgument()));

        if (deque.size() == 5) deque.remove();
        deque.addLast(command);
        logger.info(" очередь содержит: " + deque.size());

        if (!map.containsKey(command)) {
            logger.info("Введенная команда отсутствует. Повторите ввод.");
            return new Response("", "", " Ошибка. Отсутствует команда " + command);
        }

        Command cmd = map.get(command);
        Response response = cmd.execute(request);

        if (request.getCommand().equals("add") && isScript) response = modeScript(response);

        if (response.getCommand().equals("execute_script")) {
            response = modeScript(response);
        }


        logger.info("результат обработки команды статус: " + response.getStatus());
        return response;
    }

    public Response modeScript(Response resp) {
        setScript(false);
        logger.info("start modeScript ");
        Response response;

        if (!resp.getArgument().isEmpty())inputManager = new FileInputManager(resp.getArgument());
        Scanner scanner = inputManager.getScanner();
        String command;
        String argument;
        String output  = resp.getStatus();
        while (scanner.hasNextLine()) {
            logger.info("start while modeScript ");
            try {
                String[] words = inputManager.readCommand();
                command = words[0];
                if (words.length == 2) argument = words[1];
                else argument = "";

                Request request = new Request(command, argument, null);

                output += "Выполнение команды " + command + " " + argument + " из файла: \n";

                if (request.getCommand().equals("add")) {
                    setScript(true);
                    return  new Response(command, argument, output);
                }
                response = consoleMode(request);
                output += response.getStatus() + "\n";
            } catch (EmptyStringException e) {
logger.warning("Ошибка. Пустая строка в скрипте.");
            }
        }

        CommandExecuteScript ces = (CommandExecuteScript) map.get("execute_script");
        Stack<String> stack = ces.getStack();
        int index = stack.size() - 1;
        if (index > -1)stack.remove(index);
        String s;
        if (index > 0) {
            s = stack.get(index - 1);
            response = new Response("execute_script", s, output);
            modeScript(response);
        }

        return new Response("","",output);
    }

    public Map<String, Command> getMap() {
        return map;
    }

    public void setScript(boolean script) {
        isScript = script;
    }
}

