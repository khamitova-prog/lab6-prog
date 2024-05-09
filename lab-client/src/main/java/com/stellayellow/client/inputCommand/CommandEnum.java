package com.stellayellow.client.inputCommand;

import java.util.HashSet;

public enum CommandEnum {
    HELP("help"),
    INFO("info"),
    SHOW("show"),
    CLEAR("clear"),
    EXIT("exit"),
    HISTORY("history"),
    REMOVE_FIRST("remove_first"),
    REMOVE_HEAD("remove_head"),
    PRINT_FIELD_DESCENDING_DISTANCE("print_field_descending_distance"),
    REMOVE_ANY_BY_DISTANCE("remove_any_by_distance"),
    EXECUTE_SCRIPT("execute_script"),
    FILTER_BY_DISTANCE("filter_by_distance"),
    REMOVE_BY_ID("remove_by_id"),
    UPDATE("update"),
    ADD("add");

    private final String command;
    private final HashSet< String> set = new HashSet<>();

    CommandEnum(String _command) {
        command = _command;
    }

    public String getCommand() {
        return command;
    }

    public HashSet<String> getSet() {
        for (CommandEnum com : values()) {
            set.add(com.getCommand());
        }
        return set;
    }
}


