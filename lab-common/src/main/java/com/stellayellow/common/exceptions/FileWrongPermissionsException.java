package com.stellayellow.common.exceptions;
/**
 * Бросается, когда недостаточно прав для доступа к файлу
 */
public class FileWrongPermissionsException extends FileException{
    public FileWrongPermissionsException(String s){
        super(s);
    }
}