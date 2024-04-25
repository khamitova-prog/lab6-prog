package com.stellayellow.client.network;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerClient {
    public static final Logger logger = Logger.getLogger(LoggerClient.class.getName());
    static {
        try {
            FileHandler fileHandler = new FileHandler("logs/logClient.txt",true);
            fileHandler.setEncoding("UTF-8");
            fileHandler.setFormatter(new SimpleFormatter());
            LoggerClient.logger.addHandler(fileHandler);
        }catch (IOException e){
            LoggerClient.logger.warning("Не удалось открыть файл журнала: " + e.getMessage());
        } catch (SecurityException e) {
            LoggerClient.logger.warning("Отказано в доступе при открытии файла журнала: " + e.getMessage());
        } catch (Exception e){
            LoggerClient.logger.warning("Произошла ошибка при открытии обработчика файла журнала: " + e.getMessage());
        }
    }


}
