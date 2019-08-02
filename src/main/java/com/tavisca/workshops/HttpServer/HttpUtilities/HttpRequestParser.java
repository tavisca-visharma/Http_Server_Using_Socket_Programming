package com.tavisca.workshops.HttpServer.HttpUtilities;

import com.tavisca.workshops.HttpServer.LogsUtilities.LogsWriter;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpRequestParser {
    LogsWriter logsWriter;
    public String requestType = "";
    public String requestResourceURI = "";
    public String httpVersion = "";
/*
    String requestBody = "";
*/

    HashMap<String, String> requestHeadersMapper = new HashMap<String, String>();

    public boolean parse(String requestContent) {
        try {
            String[] requestContentHeaders = requestContent.split("\n");
            String requestFirstLine = requestContentHeaders[0];
            parseFirstLineOfHttpRequest(requestFirstLine);
            parseAndStoreRequestHeaders(requestContentHeaders);
            //TODO: Request Body is to be implemented in case of Parsing Post,Put and other kind of requests.
        } catch (Exception e) {
            logsWriter.writeLog(Level.SEVERE, "Exception Occurred During Parsing Http Request", e);
            return false;
        }
        return true;
    }

    private void parseFirstLineOfHttpRequest(String requestFirstLine) {
        String[] splittedRequestFirstLine = requestFirstLine.split(" ");
        this.requestType = splittedRequestFirstLine[0];
        if (splittedRequestFirstLine[1].equals("/"))
            this.requestResourceURI = "";
        else
            this.requestResourceURI = splittedRequestFirstLine[1].substring(1);
        this.httpVersion = splittedRequestFirstLine[2];
    }

    private void parseAndStoreRequestHeaders(String[] requestContentHeaders) {
        for (int line = 1; line < requestContentHeaders.length; line++) {
            if (requestContentHeaders[line].equals(""))
                break;

            if (!requestContentHeaders[line].endsWith(":") && requestContentHeaders[line].contains(":")) {
                String[] eachLineRequestHeader = requestContentHeaders[line].split(":");
                String requestHeaderName = eachLineRequestHeader[0];
                String requestHeaderValue = eachLineRequestHeader[1];

                requestHeadersMapper.put(requestHeaderName, requestHeaderValue);
            }
        }
    }

}
