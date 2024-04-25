package com.stellayellow.client.io;

import com.stellayellow.common.exceptions.*;

/**
 * Повтор ввода пользователя.
 * @param <T>
 */
public interface AskRepeat<T> {T ask() throws InvalidDataException;
}
