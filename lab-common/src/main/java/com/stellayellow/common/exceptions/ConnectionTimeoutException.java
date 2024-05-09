package com.stellayellow.common.exceptions;

/**
 * Выдается при превышении времени ожидания
 */
public class ConnectionTimeoutException extends ConnectionException{
    public ConnectionTimeoutException(){
        super("response timed out");
    }
}
