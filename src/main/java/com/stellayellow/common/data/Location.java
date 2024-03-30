package com.stellayellow.common.data;

public class Location {
    /**
     * Поле не может быть null
     */
    private final Integer x;
    /**
     * Поле не может быть null
     */
    private final Double y;
    /**
     * Поле не может быть null
     */
    private final Float z;
    /**
     * Поле может быть null
     */
    private final String name;

    public Location(Integer x, Double y, Float z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Location(Integer x, Double y, Float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = "отсутствует";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", z=").append(z);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
