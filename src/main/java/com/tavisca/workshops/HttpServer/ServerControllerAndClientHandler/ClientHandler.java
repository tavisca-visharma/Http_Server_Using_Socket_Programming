package com.tavisca.workshops.HttpServer.ServerControllerAndClientHandler;

import com.tavisca.workshops.HttpServer.HttpUtilities.HttpRequestParser;
import com.tavisca.workshops.HttpServer.HttpUtilities.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientHandler {

    BufferedInputStream inputStream;
    BufferedOutputStream outputStream;
    final int BUFFER_SIZE = 256;
    private static final String DIRECTORY_PATH = "src//main//resources//";          /*"C:\\Users\\vlsharma\\Desktop\\";*/
    File file = null;
    FileInputStream fileInputStream = null;

    void handleClient(Socket clientSocket) throws IOException {
        inputStream = new BufferedInputStream(clientSocket.getInputStream());
        outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
        byte[] dataBuffer = new byte[BUFFER_SIZE];      //We can use data.available() method as well
        inputStream.read(dataBuffer);

        String receivedRequestData = new String(dataBuffer);
        System.out.println("================================================================");
        System.out.println(receivedRequestData);
        System.out.println("================================================================");

        HttpRequestParser httpRequestParser = new HttpRequestParser();
        boolean isParsed = httpRequestParser.parse(receivedRequestData);

        /*StringTokenizer parser = new StringTokenizer(receivedRequestData);

        String requestType = parser.nextToken().trim();
        String resourceFileRequested = parser.nextToken().substring(1).trim();*/

        System.out.println("Request Type is : " + httpRequestParser.requestType);
        System.out.println("Resource File Requested is : " + httpRequestParser.requestResourceURI);

        HttpResponse httpResponse = getHttpResponse(httpRequestParser);

        if (isParsed == false) {
            httpResponse.statusCode = "400";
            httpResponse.reasonPhrase = "Bad Request";
        }

        outputStream.write((httpResponse.httpVersion + " " + httpResponse.statusCode + " " + httpResponse.reasonPhrase).getBytes());
        outputStream.write(("\nServer: Java HTTP Server : 1.0").getBytes());
        outputStream.write((("\nDate: ") + new Date()).getBytes());
        outputStream.write(("\nContent-type: " + httpResponse.httpResponseHeaderMap.get("Content-Type")).getBytes());
        outputStream.write(("\nContent-length: " + httpResponse.httpResponseHeaderMap.get("Content-Length")).getBytes());
        outputStream.write(("\n\n").getBytes());
        outputStream.write(httpResponse.responseBody, 0, httpResponse.responseBodyLength);
        outputStream.flush();

    }

    private HttpResponse getHttpResponse(HttpRequestParser httpRequestParser) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.httpVersion = httpRequestParser.httpVersion;
        httpResponse.reasonPhrase = "OK";
        httpResponse.statusCode = "200";

        //TODO : Handle different requests
        /*if (!httpRequestParser.requestType.toUpperCase().equals("GET")) {
            File file = new File(DIRECTORY_PATH + "onlyServeGetRequestsMessage.html");
        }*/

        if (httpRequestParser.requestType.toUpperCase().equals("GET")) {
            if (httpRequestParser.requestResourceURI.equals("")) {
                httpRequestParser.requestResourceURI = "index.html";
            }
            try {
                file = new File(DIRECTORY_PATH + httpRequestParser.requestResourceURI);
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                file = new File(DIRECTORY_PATH + "Error404.html");
                httpResponse.statusCode = "404";
                httpResponse.reasonPhrase = "Not Found";
            }
        }
        try {
            fileInputStream = new FileInputStream(file);
            int fileLength = (int) file.length();
            byte[] fileDataBuffer = new byte[fileLength];
            fileInputStream.read(fileDataBuffer);
            httpResponse.responseBody = fileDataBuffer;
            httpResponse.responseBodyLength = fileLength;

            if (httpRequestParser.requestResourceURI.contains(".") == true) {
                String extension = httpRequestParser.requestResourceURI.split("[.]")[1];
                System.out.println(HttpResponse.getMimeType(extension) + " =======------------");
                httpResponse.httpResponseHeaderMap.put("Content-Type", HttpResponse.getMimeType(extension));
            }


            httpResponse.httpResponseHeaderMap.put("Content-Length", String.valueOf(httpResponse.responseBody.length));


        } catch (IOException e) {
            httpResponse.statusCode = "400";
            httpResponse.reasonPhrase = "Bad Request";
        }

        return httpResponse;
    }
}

