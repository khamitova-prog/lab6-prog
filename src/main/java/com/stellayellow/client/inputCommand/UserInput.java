package com.stellayellow.client.inputCommand;

import com.stellayellow.client.io.*;
import com.stellayellow.common.exceptions.*;
import com.stellayellow.common.data.Route;
import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;

import java.util.HashSet;

public class UserInput {
//    Request request;
    Response response;
    private  final  ConsoleInputManager cm;

    public UserInput(ConsoleInputManager consoleInputManager) {
        this.response = new Response("", "", "");
        this.cm = consoleInputManager;
    }

    public Request getRequest(Response response) {
        String command = "";
        String argument = "";
        Route route = null;
        boolean skipInput = false;
        HashSet<String> set = CommandEnum.HELP.getSet();
        int ord = -1;

        if (!response.getCommand().isEmpty()) {
            command = response.getCommand();
            argument = response.getArgument();
            ord = CommandEnum.valueOf(command.toUpperCase()).ordinal();
            if (ord == 13) route = cm.initRoute();


        } else {

            while (!skipInput) {
                try {
                    System.out.println("Введите команду.");
                    String[] words = cm.readCommand();
                    command = words[0];

                    if (words.length == 2) argument = words[1];

                    if (!set.contains(command)) {
                        System.out.println("Ошибка. Введенная команда не существует. Повторите ввод.");
                        continue;
                    }

                    ord = CommandEnum.valueOf(command.toUpperCase()).ordinal();

                    if ((ord < 9) | (ord == 14)) {
                        if (!argument.isEmpty()) {
                            System.out.println("Ошибка. У данной команды не должно быть аргумента. Повторите ввод.");
                            argument = "";
                            continue;
                        }
                    } else if (argument.isEmpty()) {
                        System.out.println("Ошибка. Не указан аргумент. Повторите вод. ");
                        continue;
                    }

                    if ((ord == 12) | (ord == 13)) {
                        try {
                            Integer.parseInt(argument);
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Ошибка. Аргумент должен быть целочисленным.");
                            argument = "";
                            continue;
                        }
                    }

                    skipInput = true;
                } catch (EmptyStringException e) {
                    System.out.println("Ошибка. Команда не введена." + "\n" + " Команда \"help\" выводит список доступных команд.");
                }
            }
        }

        if (ord == 14)  route = cm.initRoute();


        return new Request(command, argument, route);
    }
}

