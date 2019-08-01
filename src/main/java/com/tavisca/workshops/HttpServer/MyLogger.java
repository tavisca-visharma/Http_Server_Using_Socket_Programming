package com.tavisca.workshops.HttpServer;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    static Logger LOGGER;
    public void initializeLogger() {

        LOGGER = Logger.getLogger("MyLog");
        FileHandler fileHandler;

        try {
            // This block configure the logger with handler and formatter
            fileHandler = new FileHandler("C:/temp/test/MyLogFile.log");
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

        } catch (IOException e) {
            System.out.println("Error in Logging ...");
        }
    }
}