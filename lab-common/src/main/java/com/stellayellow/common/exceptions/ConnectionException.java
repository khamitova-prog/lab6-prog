package com.stellayellow.common.exceptions;

/**
 * базовый класс для всех соединений
 * исключения, вызванные проблемами соединения
 */
public class ConnectionException extends Exception {
    public ConnectionException(String s){
        super(s);
    }
}
