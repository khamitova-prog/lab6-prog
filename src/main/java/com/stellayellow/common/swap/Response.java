package com.stellayellow.common.swap;

import java.io.Serializable;

public class Response implements Serializable {
    private final String command;
    private final String argument;
    private final String status;

    public Response(String command, String argument, String status) {
        this.command = command;
        this.argument = argument;
        this.status = status;
    }

    public String getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

    public String getStatus() {
        return status;
    }
}
