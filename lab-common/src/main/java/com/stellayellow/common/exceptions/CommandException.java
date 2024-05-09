package com.stellayellow.common.exceptions;

/**
 * Базовый класс для всех исключений, вызванных выполнением команд.
 */
public class CommandException extends RuntimeException {

    public CommandException(String message) {
        super(message);
    }
}
