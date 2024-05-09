    package com.stellayellow.server.managers;

import java.io.FileOutputStream;
import com.stellayellow.common.data.*;
import com.stellayellow.common.exceptions.FileNotExistsException;
import com.stellayellow.common.exceptions.FileWrongPermissionsException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import java.io.*;
import java.util.*;


/**
 * Класс FileManager, который отвечает за запись коллекции в файл и чтение из файла.
 */
public class FileManager {


    private final String filename;

    /**
     * Объект XStream для преобразования коллекции в XML и обратно
     */
    private final XStream xstream;

    /**
     * Создает новый экземпляр класса FileManager с указанным именем файла и настройками XStream.
     * Задает псевдонимы для классов, устанавливает настройки без ссылок и безопасности и
     * разрешает использование типа List и String.
     * @param filename имя файла
     */
    public FileManager(String filename) {
        this.filename = filename;
        xstream = new XStream();

        xstream.alias("location", Location.class); // используется для установки псевдонимов для классов, которые будут использоваться при преобразовании объекта в поток байтов для сохранения в файл XML
        xstream.alias("coordinates", Coordinates.class);
        xstream.alias("route", Route.class);
        xstream.alias("routes", CollectionManager.class);
        xstream.addImplicitCollection(CollectionManager.class, "routeCollection"); // используется для определения, какая коллекция будет использоваться для хранения списка организаций в классе CollectionManager


        xstream.addPermission(NoTypePermission.NONE); // запрещает сериализацию или десериализацию объектов, не имеющих типа
        xstream.addPermission(NullPermission.NULL); // позволяет сериализовать и десериализовать null значения
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES); // разрешены только примитивные типы данных и списки
        xstream.allowTypeHierarchy(List.class); // разрешает использование иерархии типов для объектов, которые реализуют интерфейс List
        xstream.allowTypeHierarchy(String.class); // разрешает использование иерархии типов для объектов типа String
        xstream.ignoreUnknownElements(); // используется для игнорирования неизвестных элементов при десериализации
    }


    /**
     * Записывает коллекцию в файл в формате XML.
     *
     * @param collection - коллекция для записи в файл
     */
    public boolean writeCollection(ArrayDeque<Route> collection) {
        if (!filename.isEmpty()) {
            String xml = xstream.toXML(new ArrayList<>(collection)); // сериализуется список элементов коллекции в формат XML и сохраняется в строковую переменную xml
            try {
                File file = new File(filename);
                if (!file.exists()) {
                    if (file.createNewFile())System.out.println("Файл для записи не найден. Файл создан успешно.");
                    else throw new FileNotExistsException();
                }
                if (file.isDirectory()) throw new FileWrongPermissionsException("Ошибка записи. По заданному пути находится директория.");

                if (!file.canWrite()) {
                    if (file.setWritable(true)) System.out.println("Нет прав на запись в файл. Права на записсь установлены успешно.");
                    else throw new FileWrongPermissionsException("не могу записать файл");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            try (FileOutputStream stream = new FileOutputStream(filename)) {
                stream.write(xml.getBytes());
                System.out.println("Файл сохранен.");
                return true;
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл.");
return false;
            }
        } else System.out.println("Файл поврежден или ошибка в названии.");
        return false;
    }


    /**
     * Метод для чтения коллекции маршрутов из файла.
     * @return возвращает коллекцию маршрутов, прочитанную из файла.
     */
    public String readFile() {
        StringBuilder xml = new StringBuilder();
        char c;

        if (!filename.isEmpty()) {
            try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename))) {
                while (bis.available() != 0) {
                    c = (char) bis.read();
                    xml.append(c);
                }
            } catch (StreamException exception){
                System.out.println("EOF error.\nФайл обработан.\n");
            } catch (FileNotFoundException exception) {
                System.out.println("Файл не найден или доступ запрещен.\nПрограмма остановлена.");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                System.out.println("Файл пуст.\nПрограмма остановлена.");
                System.exit(0);
            } catch (NullPointerException exception) {
                System.out.println("Неверный формат коллекции в файле.\nПрограмма остановлена.");
                System.exit(0);
            } catch (IllegalStateException exception) {
                System.out.println("Непредвиденная ошибка.\nПрограмма остановлена.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Неверный формат данных в файле.\nФайл очищен.\n");
            }
        } else {System.out.println("Файл поврежден или ошибка в названии.\nПрограмма остановлена.");
            System.exit(0);}
        return xml.toString();
    }

    /**
     * Создает коллекцию из строки
     * @param xml - строка в формате xmll
     * @return коллекцию
     */
    public ArrayDeque<Route> createCollection(String xml) {
        ArrayDeque<Route> collectionRoute = new ArrayDeque<>();
        if (!xml.isEmpty()) {
            try {

            xstream.setMode(XStream.NO_REFERENCES); // не будут использоваться ссылки на другие объекты при сериализации
            xstream.addPermission(NoTypePermission.NONE); // запрещает сериализацию объектов, не имеющих указанных типов
            xstream.addPermission(NullPermission.NULL); // разрешает сериализацию null-значений
            xstream.addPermission(PrimitiveTypePermission.PRIMITIVES); // разрешает сериализацию примитивных типов данных и строк
            xstream.allowTypeHierarchy(List.class); // разрешает сериализацию всех типов, производных от List
            xstream.allowTypeHierarchy(String.class); // разрешает сериализацию всех типов, производных от String
            xstream.ignoreUnknownElements(); // игнорирует элементы, которые не удалось десериализовать
            xstream.allowTypes(new Class[] {ArrayDeque.class, Route.class}); // разрешает сериализацию объектов типа ArrayList и Organization

            List<Route> list = (List<Route>) xstream.fromXML(xml);
                collectionRoute.addAll(list);
                return collectionRoute;
            } catch (NullPointerException exception) {
                System.out.println("Неверный формат коллекции в файле.\nПрограмма остановлена.");
                System.exit(0);
            } catch (IllegalStateException exception) {
                System.out.println("Непредвиденная ошибка.\nПрограмма остановлена.");
                System.exit(0);
            } catch (Exception e) {
                System.out.println("Неверный формат данных в файле.\nФайл очищен.\n");
            }
        } else {
            System.out.println("Файл пуст. создана пустая коллекция.");
            return new ArrayDeque<>();
        }
        return collectionRoute;
    }

    /**
     * Возвращает строку с описанием класса FileManager
     * @return описание класса
     */
    @Override
    public String toString() {
        return "FileManager (класс для работы с файлами)";
    }
}