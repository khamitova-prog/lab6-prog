package com.stellayellow.common.exceptions;
/**
 * Бросается, когда файл не существует
 */
public class FileNotExistsException extends FileException{
    public FileNotExistsException(){
        super("Ошибка. Файл отсутствует.");
    }
}
