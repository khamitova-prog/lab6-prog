package com.stellayellow.client.io;

import com.stellayellow.common.exceptions.*;

/**
 * Это оболочка пользовательского ввода.
 * @param <T>
 */
public class Question<T> {
    private final AskRepeat<T> askRepeat;
    private T answer;

    public Question(String msg, AskRepeat<T> askRepeat) {
        this.askRepeat = askRepeat;
        while (true) {
            try {
                System.out.println(msg + "");
                answer =this.askRepeat.ask();
                break;
            }
            catch (InvalidDataException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * Возвращает тип ответа.
      * @return параметр T
     */
    public T getAnswer() {return answer;}
}

