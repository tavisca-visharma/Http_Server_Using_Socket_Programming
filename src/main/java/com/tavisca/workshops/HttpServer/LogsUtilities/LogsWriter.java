package com.tavisca.workshops.HttpServer.LogsUtilities;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogsWriter {
    Logger logger;

    public LogsWriter(String className) {
        logger = Logger.getLogger(className);
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("src//main//LogsForHttpServer//" + className + ".log");
        } catch (IOException e) {
            System.out.println("Unable to open/write Log File...");
        }
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }

    public void writeLog(String message) {
        logger.info(message);
    }

    public void writeLog(Level level, String message) {
        logger.log(level, message);
    }

    public void writeLog(Level level, String message, Object object) {
        logger.log(level, message, object);
    }
}