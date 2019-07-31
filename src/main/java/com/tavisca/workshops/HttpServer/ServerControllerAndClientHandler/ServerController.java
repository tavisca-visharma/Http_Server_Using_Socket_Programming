package com.tavisca.workshops.HttpServer.ServerControllerAndClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    public static void main(String[] args) throws IOException {
        String IPAddress = "localhost";
        int port = 80;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server Started");
        while (true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("\n\t\tNew Client Connected ... ");
            System.out.println("\t\t=========================");
            Thread clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ClientHandler clientHandler = new ClientHandler();
                    try {
                        clientHandler.handleClient(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //TODO: Handle the Client in Thread
                }
            });
            clientThread.start();
        }
    }
}
