package com.stellayellow.client.io;

import com.stellayellow.common.data.Coordinates;
import com.stellayellow.common.data.Location;
import com.stellayellow.common.data.Route;

import java.util.Scanner;

/**
 * Класс, использующий пользовательский ввод для создания объекта Route
 */
public class ConsoleInputManager extends InputManager {
    String place;

    public ConsoleInputManager () {
        super(new Scanner(System.in));
        getScanner().useDelimiter("\n");
        this.place = " начало ";
    }

    /**
     *Формирует запрос на получение значения поля name и обрабатывает пользовательский ввод
      * @return значение поля name
     */
    @Override
    protected String readName() {
        return new Question<>("Введите название маршрута:", super::readName).getAnswer();
    }

/**
 * Формирует запрос на получение координаты x в классе Coordinates
 * и обрабатывает пользовательский ввод
 * @return действительное значение поля x
  */
@Override
    protected Double readCoordinateX() {
        String str = "Введите координату x маршрута," + "\n" +
                "действительное число не более 825:";
        return new Question<>(str, super::readCoordinateX).getAnswer();
    }

    /**
     * Формирует запрос на получение координаты y в классе Coordinates
     * и обрабатывает пользовательский ввод
      * @return целочисленное значение поля y
     */
    @Override
    protected Integer readCoordinateY() {
        String str = "Введите координату y маршрута," + "\n" +
                " целое число больше -603:";
        return new Question<>(str, super::readCoordinateY).getAnswer();
    }

    /**
     * Формирует запрос на получение значения поля x класса Location
     * и обрабатывает пользовательский ввод
      * @return целочисленное значение поля x
     */
    @Override
    protected Integer readLocationX() {
        String  str = "Введите значение координаты x " + place + "маршрута" + "\n" +
                "любое целое число. (может быть пустым)";

        return new Question<>(str, super::readLocationX).getAnswer();
    }

    /**
     * Формирует запрос для получения значения поля y класса Location
     *      @return действительное значение поля y
     */
    @Override
    protected Double readLocationY() {
        String  str = "Введите значение координаты y " + place + "маршрута" + "\n" +
                "любое действительное число. (может быть пустым)";

        return new Question<>(str, super::readLocationY).getAnswer();
    }

    /**
     * Формирует запрос на получение координаты z
     * и обрабатывает пользовательский ввод
     * @return действительное значение поля z
     */
    @Override
    protected Float readLocationZ() {
        String  str = "Введите значение координаты z " + place + "маршрута" + "\n" +
                "любое действительное число типа Float. (может быть пустым)";

        return new Question<>(str, super::readLocationZ).getAnswer();
    }

    /**
     * Формирует запрос на получение значения поля name класса Location
     * и обрабатывает пользовательский ввод
     * @return название старта/финиша маршрута или NULL
     */
    @Override
    protected String readLocationName() {
        return new Question<>("Введите имя " + place + " маршрута:", super::readLocationName).getAnswer();
    }

    /**
     * Формирует запрос на получение значения поля distance
      * @return действительное значение поля distance
     */
    @Override
    protected float readDistance() {
        return new Question<>("Введите длину маршрута,целое число больше 1:", super::readDistance).getAnswer();
    }

    /**
     * Создает объект Location
     * в зависимости от инициализации поля name
     * запускается конструктор с тремя или четырьмя параметрами
     * @return локацию начала/окончания маршрута
     */
    private Location initLocation() {
        Integer x = readLocationX();
        Double y = readLocationY();
        Float z = readLocationZ();
        String nameL = readLocationName();
        return (nameL == null) ? (new Location(x, y, z)) : (new Location(x, y, z, nameL));
    }

    /**
     * Создает объект Route, используя полученные данные, введенные из консоли
      * @return Route
     */
    public Route initRoute() {
        String nameRoute = readName();
        Coordinates coordinates = new Coordinates(readCoordinateX(), readCoordinateY());

        place = " начала ";
        Location from = initLocation();
        place = " окончания ";
        Location to = initLocation();
        return new Route(nameRoute, coordinates, from, to, readDistance());
    }

    public Route updateRoute(Route route) {
        route.setName(readName());
        route.setCoordinates(new Coordinates(readCoordinateX(), readCoordinateY()));
        place = " начала ";
        route.setFrom(initLocation());
        place = "окончания";
        route.setTo(initLocation());
        route.setDistance(readDistance());
        return route;
    }
}

