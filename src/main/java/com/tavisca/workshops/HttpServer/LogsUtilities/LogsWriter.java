package com.tavisca.workshops.HttpServer.LogsUtilities;

import java.util.logging.Level;

public class LogsWriter {
//    Logger logger;

    public LogsWriter(String className) {
//        logger = Logger.getLogger(className);
//        FileHandler fileHandler = null;
//        try {
//            fileHandler = new FileHandler("src//main//LogsForHttpServer//" + className + ".log");
//        } catch (IOException e) {
//            System.out.println("Unable to open/write Log File...");
//        }
//        logger.addHandler(fileHandler);
//        SimpleFormatter formatter = new SimpleFormatter();
//        fileHandler.setFormatter(formatter);
    }

    public void writeLog(String message) {
        System.out.println(message);
//        logger.info(message);
    }

    public void writeLog(Level level, String message) {
//        logger.log(level, message);
        System.out.println(message);
    }

    public void writeLog(Level level, String message, Object object) {
//        logger.log(level, message, object);
        System.out.println(message);
    }
}