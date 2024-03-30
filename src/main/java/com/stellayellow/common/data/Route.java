package com.stellayellow.common.data;

import java.time.LocalDateTime;

/**
 * Класс, описывающий маршрут. Объекты этого класса являются элементами коллекции
 */
public class Route implements Comparable<Route> {
    /**
     * Уникальный идентификатор маршрута.
     * Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     */
    private Long id;
    /**
     * Название маршрута.
     * Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Поле не может быть null
     */
    private Coordinates coordinates;
    /**
     * Дата создания маршрута.
     * Поле не может быть null, Значение этого поля должно генерироваться автоматически
     */
    private final LocalDateTime creationDate;
    /**
     * Точка начала маршрута.
     * Поле не может быть null
     */
    private Location from;
    /**
     * Точка окончания маршрута.
     * Поле не может быть null
     */
    private Location to;
    /**
     * Протяженность маршрута.
     * Значение поля должно быть больше 1
     */
    private float distance;

    public Route(String name, Coordinates coordinates, Location from, Location to, float distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Route o) {
        return Long.compare(this.getId(), o.getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Route{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", coordinates=").append(coordinates);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append(", distance=").append(distance);
        sb.append('}');
        return sb.toString();
    }
}

