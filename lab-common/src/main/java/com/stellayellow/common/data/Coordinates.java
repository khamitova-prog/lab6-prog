package com.stellayellow.common.data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    /**
     * Максимальное значение поля: 825,
     * Поле не может быть null
     */
    private final Double x;
    /**
     * Значение поля должно быть больше -603,
     * Поле не может быть null
     */
    private final Integer y;

    public Coordinates(Double x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
