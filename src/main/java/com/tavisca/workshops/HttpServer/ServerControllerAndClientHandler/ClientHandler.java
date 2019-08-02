package com.tavisca.workshops.HttpServer.ServerControllerAndClientHandler;

import com.tavisca.workshops.HttpServer.FileUtilities.FileReader;
import com.tavisca.workshops.HttpServer.HttpUtilities.HttpRequestParser;
import com.tavisca.workshops.HttpServer.HttpUtilities.HttpResponse;
import com.tavisca.workshops.HttpServer.LogsUtilities.LogsWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;

public class ClientHandler {

    final int BUFFER_SIZE = 256;
    private static final String DIRECTORY_PATH = "src//main//resources//HttpServerResources//";          /*"C:\\Users\\vlsharma\\Desktop\\";*/
    File file = null;
    LogsWriter logsWriter;

    ClientHandler() {
        logsWriter = new LogsWriter(ClientHandler.class.getName());
    }


    void handleClient(Socket clientSocket) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(clientSocket.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(clientSocket.getOutputStream());

            byte[] dataBuffer = getReceivedRequestDataInBytes(inputStream);

            String receivedRequestData = logRequestHeadersAndBodyReceived(dataBuffer);

            HttpResponse httpResponse = parseHttpRequestAndReceiveResponse(receivedRequestData);

            sendResponseOfClientRequest(outputStream, httpResponse);

            cleanUpStreamsAndClientSocket(clientSocket, inputStream, outputStream);

        } catch (IOException e) {
            logsWriter.writeLog(Level.SEVERE, "Exception Occurred", e);
        }
    }

    private byte[] getReceivedRequestDataInBytes(BufferedInputStream inputStream) throws IOException {
        byte[] dataBuffer = new byte[BUFFER_SIZE];      //We can use data.available() method as well
        inputStream.read(dataBuffer);
        return dataBuffer;
    }

    private HttpResponse parseHttpRequestAndReceiveResponse(String receivedRequestData) {
        HttpRequestParser httpRequestParser = new HttpRequestParser();
        boolean isParsed = httpRequestParser.parse(receivedRequestData);

        /*StringTokenizer parser = new StringTokenizer(receivedRequestData);

        String requestType = parser.nextToken().trim();
        String resourceFileRequested = parser.nextToken().substring(1).trim();*/
        logsWriter.writeLog("Request Type is : " + httpRequestParser.requestType);
        logsWriter.writeLog("Resource File Requested is : " + httpRequestParser.requestResourceURI);

        HttpResponse httpResponse = null;
        if (isParsed == true) {
            httpResponse = getHttpResponse(httpRequestParser);
        } else {
            httpResponse = new HttpResponse();
            httpResponse.statusCode = "400";
            httpResponse.reasonPhrase = "Bad Request";
        }
        return httpResponse;
    }

    private String logRequestHeadersAndBodyReceived(byte[] dataBuffer) {
        String receivedRequestData = new String(dataBuffer);
        logsWriter.writeLog("================================================================");
        logsWriter.writeLog(receivedRequestData);
        logsWriter.writeLog("================================================================");
        return receivedRequestData;
    }

    private void sendResponseOfClientRequest(BufferedOutputStream outputStream, HttpResponse httpResponse) throws IOException {
        outputStream.write((httpResponse.httpVersion + " " + httpResponse.statusCode + " " + httpResponse.reasonPhrase).getBytes());
        outputStream.write(("\nServer: Java HTTP Server : 1.0").getBytes());
        outputStream.write((("\nDate: ") + new Date()).getBytes());
        outputStream.write(("\nContent-type: " + httpResponse.httpResponseHeaderMap.get("Content-Type")).getBytes());
        outputStream.write(("\nContent-length: " + httpResponse.httpResponseHeaderMap.get("Content-Length")).getBytes());
        outputStream.write(("\n\n").getBytes());
        outputStream.write(httpResponse.responseBody, 0, httpResponse.responseBodyLength);
        outputStream.flush();
    }

    private void cleanUpStreamsAndClientSocket(Socket clientSocket, BufferedInputStream inputStream, BufferedOutputStream outputStream) throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    private HttpResponse getHttpResponse(HttpRequestParser httpRequestParser) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.httpVersion = httpRequestParser.httpVersion;

        //TODO : Handle different requests
        /*if (!httpRequestParser.requestType.toUpperCase().equals("GET")) {
            File file = new File(DIRECTORY_PATH + "onlyServeGetRequestsMessage.html");
        }*/

        FileReader fileReader = new FileReader();
        if (httpRequestParser.requestType.toUpperCase().equals("GET")) {
            handleGetTypeResourceRequests(httpRequestParser, httpResponse, fileReader);
        }
        return httpResponse;
    }

    private void handleGetTypeResourceRequests(HttpRequestParser httpRequestParser, HttpResponse httpResponse, FileReader fileReader) {
        if (httpRequestParser.requestResourceURI.equals("")) {
            httpRequestParser.requestResourceURI = "index.html";
        }
        file = new File(DIRECTORY_PATH + httpRequestParser.requestResourceURI);
        if(!file.exists()) {
            httpRequestParser.requestResourceURI = "Error404.html";
            file = new File(DIRECTORY_PATH + httpRequestParser.requestResourceURI);
            httpResponse.statusCode = "404";
            httpResponse.reasonPhrase = "NOT_FOUND";
        }
        try {
            httpResponse.responseBody = fileReader.readFromFile(file);
        } catch (IOException e) {
            logsWriter.writeLog(Level.SEVERE, "Exception Occurred While Reading File ...", e);
        }

        httpResponse.responseBodyLength = fileReader.getFileLengthOfReadFile();

        setContentTypeAndContentLengthOfResponse(httpRequestParser, httpResponse);
    }

    private void setContentTypeAndContentLengthOfResponse(HttpRequestParser httpRequestParser, HttpResponse httpResponse) {
        if (httpRequestParser.requestResourceURI.contains(".")) {
            String extension = httpRequestParser.requestResourceURI.split("[.]")[1];
            httpResponse.httpResponseHeaderMap.put("Content-Type", HttpResponse.getMimeType(extension));
        }
        httpResponse.httpResponseHeaderMap.put("Content-Length", String.valueOf(httpResponse.responseBody.length));
    }
}

