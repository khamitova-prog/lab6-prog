package com.stellayellow.common.swap;

import com.stellayellow.common.data.Route;

import java.io.Serializable;

public class Request implements Serializable {
    private final String command;
    private final String argument;
    private final Route route;

    public Request(String command, String argument, Route route) {
        this.command = command;
        this.argument = argument;
        this.route = route;
    }

    public String getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

    public Route getRoute() {
        return route;
    }

}

