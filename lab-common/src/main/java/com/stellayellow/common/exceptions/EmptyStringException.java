package com.stellayellow.common.exceptions;

/**
 * Ошибка при вводе пустой строки
 */
public class EmptyStringException extends InvalidDataException {
    public EmptyStringException() {
        super("Ошибка ввода. Строка не может быть пустой.");
    }
}
