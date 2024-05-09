package com.stellayellow.common.exceptions;

/**
 * Исключения при вводе числовых данных
 */
public class InvalidNumberException extends InvalidDataException {

    public InvalidNumberException () {
        super("Неверный формат числа");
    }

    public InvalidNumberException(String message) {
        super(message);
    }
}

