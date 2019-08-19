package com.tavisca.workshops.HttpServer.ServerControllerAndClientHandler;

import com.tavisca.workshops.HttpServer.LogsUtilities.LogsWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerController {
    static LogsWriter logsWriter;
    static int port = 80;

    public static void main(String[] args) throws IOException {
        logsWriter = new LogsWriter(ServerController.class.getName());
        ServerSocket serverSocket = new ServerSocket(port);
        logsWriter.writeLog("Server Started to listen at port " + port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            logsWriter.writeLog("\n\t\tNew Client Connected ... ");
            logsWriter.writeLog("\t\t=========================");
            ClientHandler clientHandler = new ClientHandler();

            /*Creating a New Thread*/
            new Thread(() -> { clientHandler.handleClient(clientSocket);}).start();
        }
    }
}
