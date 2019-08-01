package com.tavisca.workshops.HttpServer.ServerControllerAndClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerController {
    static Logger LOGGER = Logger.getLogger(ServerController.class.getName());
    static int port = 80;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        LOGGER.info("Server Started");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            LOGGER.info("\n\t\tNew Client Connected ... ");
            LOGGER.info("\t\t=========================");
            ClientHandler clientHandler = new ClientHandler();

            /*Creating a New Thread*/
            new Thread(() -> { clientHandler.handleClient(clientSocket);}).start();
        }
    }
}
