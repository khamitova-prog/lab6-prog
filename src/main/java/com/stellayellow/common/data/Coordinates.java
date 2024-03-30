package com.stellayellow.common.data;

public class Coordinates {
    /**
     * Максимальное значение поля: 825,
     * Поле не может быть null
     */
    private Double x;
    /**
     * Значение поля должно быть больше -603,
     * Поле не может быть null
     */
    private Integer y;

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
