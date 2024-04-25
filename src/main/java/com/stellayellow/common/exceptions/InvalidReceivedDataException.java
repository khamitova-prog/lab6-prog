package com.stellayellow.common.exceptions;

/**
 * Выдается, когда полученные данные недействительны
 */
public class InvalidReceivedDataException extends InvalidDataException {
    public InvalidReceivedDataException(){
        super("received data is damaged");
    }
}
