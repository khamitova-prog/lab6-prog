package com.stellayellow.common.exceptions;

/**
 * Базовый класс для всех исключений, вызванных неправильным вводом
 */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
            super (message);
    }
}
