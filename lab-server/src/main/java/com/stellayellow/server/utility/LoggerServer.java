package com.stellayellow.server.utility;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerServer {
    public static final Logger logger = Logger.getLogger(LoggerServer.class.getName());
    static {
        try {
            FileHandler fileHandler = new FileHandler("logs/logServer.txt",true);
            fileHandler.setEncoding("UTF-8");
            fileHandler.setFormatter(new SimpleFormatter());
            LoggerServer.logger.addHandler(fileHandler);
        }catch (IOException e){LoggerServer.logger.warning("Не удалось открыть файл журнала: " + e.getMessage());
        } catch (SecurityException e) {LoggerServer.logger.warning("Отказано в доступе при открытии файла журнала: " + e.getMessage());
        } catch (Exception e){LoggerServer.logger.warning("Произошла ошибка при открытии обработчика файла журнала: " + e.getMessage());
        }
    }


}
