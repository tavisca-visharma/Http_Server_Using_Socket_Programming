package com.tavisca.workshops.HttpServer.HttpUtilities;

import com.tavisca.workshops.HttpServer.FileUtilities.FileReader;
import com.tavisca.workshops.HttpServer.LogsUtilities.LogsWriter;
import com.tavisca.workshops.HttpServer.model.ResponseHeader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public class HttpRequestHandler {
    static LogsWriter logsWriter;
    final int BUFFER_SIZE = 256;
    private static final String DIRECTORY_PATH = "src//main//resources//HttpServerResources//";

    static {
        logsWriter = new LogsWriter(HttpRequestHandler.class.getName());
    }

    public HttpResponse handleRequest(BufferedInputStream inputStream) throws IOException {
        byte[] dataBuffer = getReceivedRequestDataInBytes(inputStream);

        String receivedRequestData = logRequestHeadersAndBodyReceived(dataBuffer);

        HttpResponse httpResponse = parseHttpRequestAndReceiveResponse(receivedRequestData);
        return httpResponse;
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
            httpResponse.header = new ResponseHeader(httpResponse.body.length).badRequestError().getBytes();
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

    private HttpResponse getHttpResponse(HttpRequestParser httpRequestParser) {
        HttpResponse httpResponse = new HttpResponse();

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
            handleGetTypeResourceRequests(httpRequestParser, httpResponse, fileReader);
        }
        try {
            try {
                httpResponse.body = fileReader.readFromFile(new File(DIRECTORY_PATH + httpRequestParser.requestResourceURI));
                String extension = getExtension(httpRequestParser);
                httpResponse.header = new ResponseHeader(httpResponse.body.length).
                        setContentTypeForExtention(extension)
                        .getBytes();
            } catch (FileNotFoundException e) {
                httpRequestParser.requestResourceURI = "Error404.html";
                httpResponse.body = fileReader.readFromFile(new File(DIRECTORY_PATH + httpRequestParser.requestResourceURI));
                String extension = getExtension(httpRequestParser);
                httpResponse.header = new ResponseHeader(httpResponse.body.length)
                        .fileNotFoundError()
                        .setContentTypeForExtention(extension)
                        .getBytes();
                logsWriter.writeLog(Level.SEVERE, "File not found ...", e);
            }
        } catch (IOException e) {
            logsWriter.writeLog(Level.SEVERE, "Difficulty Reading File ...", e);
        }


    }

    private String getExtension(HttpRequestParser httpRequestParser) {
        String extension = "text/html";
        if (httpRequestParser.requestResourceURI.contains(".")) {
            extension = httpRequestParser.requestResourceURI.split("[.]")[1];
        }
        return extension;
    }
}


