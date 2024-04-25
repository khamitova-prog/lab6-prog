package com.stellayellow.server.managers;

import java.time.*;

import java.util.*;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.stellayellow.common.data.*;

/**
 * Класс, который управляет коллекцией.
 */
public class CollectionManager {

    private ArrayDeque<Route> routeCollection = new ArrayDeque<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.loadCollection();
    }

    /**
     * @return Коллекция объектов класса Route.
     */
    public ArrayDeque<Route> getRouteCollection() {
        return routeCollection;
    }

    /**
     * @return Время последней инициализации или null если не было инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Время последнего сохранения коллекции или значение NULL, если сохранения не было.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public void setLastSaveTime(LocalDateTime lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }

    /**
     * Загружает коллекцию из файла.
     */
    private void loadCollection() {
        lastInitTime = LocalDateTime.now();
        routeCollection = fileManager.createCollection(fileManager.readFile());
    }

    /**
     * Генератор уникального id.
      * @return  Long -значение идентификатора нового элемента маршрута.
     */
    public Long createId() {
        return (routeCollection.isEmpty()) ? 1L :  Collections.max(routeCollection).getId() + 1;
    }

    /**
     * Возвращает Элемент маршрута с заданным полем id.
      * @param id - значение поля идентификатора
     * @return Элемент Route с указанным полем id или значение NULL, если такого элемента нет.
     */
    public Route getById(Long id) {
        for (Route route : routeCollection) {
            if (route.getId().equals(id)) return route;
        }
        return null;
    }

    /**
     * Отображает информацию о коллекции.
      * @return строка с информацией о коллекции
     */
    @Override
    public String toString() {
        if (routeCollection.isEmpty()) return "The collection is empty.";

        StringBuilder info = new StringBuilder();
        for (Route route : routeCollection) {
            info.append(route.toString());
            info.append("\n");
        }
        return info.toString();
    }


}

