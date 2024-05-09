package com.stellayellow.common.exceptions;

/**
 * Выдается, когда порт недействителен
 */
public class InvalidPortException extends ConnectionException {
    public InvalidPortException(){
        super("invalid port");
    }
}
