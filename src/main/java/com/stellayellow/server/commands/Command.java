package com.stellayellow.server.commands;

import com.stellayellow.common.swap.Request;
import com.stellayellow.common.swap.Response;

/**
 * Абстрактный класс, его расширяют классы с командами
 */
public abstract class Command {
    private  final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public abstract Response execute(Request request);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}


