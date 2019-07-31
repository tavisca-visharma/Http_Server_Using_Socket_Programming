package com.tavisca.workshops.HttpServer.HttpUtilities;

import java.util.HashMap;

public class HttpRequestParser {
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

            String[] splittedRequestFirstLine = requestFirstLine.split(" ");
            this.requestType = splittedRequestFirstLine[0];
            if (splittedRequestFirstLine[1].equals("/"))
                this.requestResourceURI = "";
            else
                this.requestResourceURI = splittedRequestFirstLine[1].substring(1);
            this.httpVersion = splittedRequestFirstLine[2];

            int line;
            for (line = 1; line < requestContentHeaders.length; line++) {
                if (requestContentHeaders[line].equals(""))
                    break;

                String[] eachLineRequestHeader = requestContentHeaders[line].split(":");
                String requestHeaderName = eachLineRequestHeader[0];
                String requestHeaderValue = eachLineRequestHeader[1];

                requestHeadersMapper.put(requestHeaderName, requestHeaderValue);

            }

            //TODO: Request Body is to be implemented in case of Parsing Post,Put and other kind of requests.

        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
