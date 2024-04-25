package com.stellayellow.common.exceptions;
/**
 * Выбрасывается, когда скрипт зацикливается
 */
public class RecursiveScriptExecuteException extends CommandException{
    public RecursiveScriptExecuteException(){
        super("Попытка выполнения рекурсивного скрипта");
    }
}
