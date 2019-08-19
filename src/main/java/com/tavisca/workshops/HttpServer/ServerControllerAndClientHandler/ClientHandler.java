package com.tavisca.workshops.HttpServer.ServerControllerAndClientHandler;

import com.tavisca.workshops.HttpServer.HttpUtilities.HttpRequestHandler;
import com.tavisca.workshops.HttpServer.HttpUtilities.HttpResponse;
import com.tavisca.workshops.HttpServer.LogsUtilities.LogsWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ClientHandler {

    LogsWriter logsWriter;

    ClientHandler() {
        logsWriter = new LogsWriter(ClientHandler.class.getName());
    }

    void handleClient(Socket clientSocket) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(clientSocket.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(clientSocket.getOutputStream());

            HttpRequestHandler httpRequestHandler = new HttpRequestHandler();
            HttpResponse httpResponse = httpRequestHandler.handleRequest(inputStream);

            sendResponseOfClientRequest(outputStream, httpResponse);

            cleanUpStreamsAndClientSocket(clientSocket, inputStream, outputStream);

        } catch (IOException e) {
            logsWriter.writeLog(Level.SEVERE, "Exception Occurred", e);
        }
    }

    private void sendResponseOfClientRequest(BufferedOutputStream outputStream, HttpResponse httpResponse) throws IOException {

    /*    String responseHeaders = "";
        responseHeaders += httpResponse.httpVersion + " " + httpResponse.statusCode + " " + httpResponse.reasonPhrase;
        responseHeaders += "\nServer: Java HTTP Server : 1.0";
        responseHeaders += "\nDate: " + new Date();
        responseHeaders += "\nContent-type: " + httpResponse.httpResponseHeaderMap.get("Content-Type");
        responseHeaders += "\nContent-length: " + httpResponse.httpResponseHeaderMap.get("Content-Length");
        responseHeaders += "\n\n";
        outputStream.write(responseHeaders.getBytes());*/

        /*
        outputStream.write((httpResponse.httpVersion + " " + httpResponse.statusCode + " " + httpResponse.reasonPhrase).getBytes());
        outputStream.write(("\nServer: Java HTTP Server : 1.0").getBytes());
        outputStream.write((("\nDate: ") + new Date()).getBytes());
        outputStream.write(("\nContent-type: " + httpResponse.httpResponseHeaderMap.get("Content-Type")).getBytes());
        outputStream.write(("\nContent-length: " + httpResponse.httpResponseHeaderMap.get("Content-Length")).getBytes());
        outputStream.write(("\n\n").getBytes());*/

        outputStream.write(httpResponse.header);
        outputStream.write(httpResponse.body);
        outputStream.flush();
    }

    private void cleanUpStreamsAndClientSocket(Socket clientSocket, BufferedInputStream inputStream, BufferedOutputStream outputStream) throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

}

