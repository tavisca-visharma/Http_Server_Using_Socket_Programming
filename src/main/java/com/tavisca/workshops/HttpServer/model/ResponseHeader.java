package com.tavisca.workshops.HttpServer.model;

import com.tavisca.workshops.HttpServer.HttpUtilities.HttpResponse;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    Map<Integer, String> statusCodeToMessageMap = Collections.unmodifiableMap(new HashMap<Integer, String>() {{
        put(200, "OK");
        put(400, "Bad Request");
        put(404, "Not Found");
        put(500, "Internal Server Error");
    }});

    String protocol = "HTTP/1.1";
    int statusCode = 200;
    Date date = new Date();
    String contentType = "text/html";
    int contentLength;

    public ResponseHeader(int contentLength) {
        this.contentLength = contentLength;
    }

    public ResponseHeader(int statusCode, String contentType, int contentLength) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.contentLength = contentLength;
    }

    @Override
    public String toString() {
        return "" + protocol + " " + statusCode + " " + statusCodeToMessageMap.get(statusCode)
                + "\n" + "Server: Java HTTP Server : 1.0"
                + "\n" + "Date:" + date
                + "\n" + "Content-type:" + contentType
                + "\n" + "Content-length:" + contentLength
                + "\n\n";
    }

    public ResponseHeader setContentTypeForExtention(String extension) {
        this.contentType = HttpResponse.getMimeType(extension);
        return this;
    }

    public ResponseHeader badRequestError() {
        statusCode = 400;
        return this;
    }

    public ResponseHeader fileNotFoundError() {
        statusCode = 404;
        return this;
    }

    public ResponseHeader serverError() {
        statusCode = 500;
        return this;
    }

    public byte[] getBytes() {
        return toString().getBytes();
    }
}
