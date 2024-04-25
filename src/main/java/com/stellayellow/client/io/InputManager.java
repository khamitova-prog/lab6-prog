package com.stellayellow.client.io;

import java.util.Scanner;

import com.stellayellow.common.exceptions.*;

/**
 * Абстрактный класс, приводит, полученную из консоли строку к заданному типу и реализует валидацию данных.
 */
public abstract class InputManager {
    private final Scanner scanner;

    public InputManager(Scanner scanner) {
        this.scanner = scanner;
        scanner.useDelimiter("\n");
    }

    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Проверяет пользовательский ввод name
     * @return имя маршрута
     * @throws EmptyStringException, если была введена пустая строка
     */
    protected String readName() throws EmptyStringException {
        String str = scanner.nextLine().trim();
        if (str.isEmpty()) {
            throw new EmptyStringException();
        }
        return str;
    }

    /**
     * Проверяет пользовательский ввод координаты x
      * @return координату x
     * @throws InvalidNumberException, если введенная строка не действительное число
     */
    protected Double readCoordinateX() throws InvalidNumberException {
double x;
try {
    x = Double.parseDouble(scanner.nextLine());
}
catch (NumberFormatException e) {
    throw new InvalidNumberException();
}
if (Double.isInfinite(x) || Double.isNaN(x)) throw new InvalidNumberException("Недопустимое значение с плавающей запятой");
if (x > 825.0) throw new InvalidNumberException("Значение поля не должно быть  больше 825.0");
return x;
    }

    /**
     * Проверяет пользовательский ввод координаты y
      * @return координату y
     * @throws InvalidNumberException, если введенная строка не целое число
     */
    protected Integer readCoordinateY() throws InvalidNumberException {
        int y;
        try {
            y = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        if (y < -602) throw new InvalidNumberException("Значение поля не должно быть меньше -602");
        return y;
    }

    /**
     * Преобразует строку из консоли в целое число.
     * @return целочисленную координату x
     * @throws InvalidNumberException если строка не содержит целое число
     */
    protected Integer readLocationX() throws InvalidNumberException {
        int x;
        try {
            x = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        return x;
    }

    protected Double readLocationY() throws InvalidNumberException {
        double y;
        try {
            y = Double.parseDouble(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        return y;
    }

    /**
     * Преобразует строку из консоли в действительное число (float)
     * @return действительное значение координаты z
     * @throws InvalidNumberException если полученная строка не приводится к действительному числу
     */
    protected Float readLocationZ() throws InvalidNumberException {
        float z;
        try {
            z = Float.parseFloat(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        return z;
    }

    protected   String readLocationName() {
        String str = scanner.nextLine().trim();
        if (str.isEmpty()) return null;
        return str;
    }

    /**
     * Преобразует строку ввода в действительное число (float)
     * @return действительное значение поля distance
     * @throws InvalidNumberException если строку нельзя преобразовать в действительное число
     */
    protected float readDistance() throws InvalidNumberException {
        String str = scanner.nextLine().trim();
        if (str.isEmpty()) throw new  InvalidNumberException("Не введено действительное число.");
        float distance;
        try {
            distance = Float.parseFloat(str);
        }
        catch (NumberFormatException e) {
throw new InvalidNumberException();
        }
        if (!(distance > 1)) throw new InvalidNumberException("Значение поля должно быть больше 1");
        return distance;
}

    /**
     * Преобразует строку из консоли в массив двух строк
     * первая строка - команда
     * вторая строка аргумент,
     * если аргумент отсутствует, то строка - ""
     * @return массив из двух строк
     * @throws EmptyStringException если в консоли была введена пустая строка
     */
    public String[] readCommand() throws EmptyStringException {
        String str = scanner.nextLine().trim();

        if (str.isEmpty()) throw new EmptyStringException();
        return str.split("\\s+", 2);
    }

    }


