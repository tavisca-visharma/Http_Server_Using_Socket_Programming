package com.tavisca.workshops.HttpServer.HttpUtilitiesTest;

import com.tavisca.workshops.HttpServer.HttpUtilities.HttpRequestParser;
import com.tavisca.workshops.HttpServer.HttpUtilities.HttpResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestParserTest {

    @Test
    void canParseGivenHttpRequest(){
        String requestString = "GET /rose-blue-flower-rose-blooms-67636.jpeg HTTP/1.1\n" +
                "Host: localhost\n" +
                "Connection: keep-alive\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36\n" +
                "Accept: image/webp,image/apng,i";
        HttpRequestParser httpRequestParser = new HttpRequestParser();
        boolean isParsed = httpRequestParser.parse(requestString);

        assertEquals(true,isParsed);

        assertEquals(httpRequestParser.httpVersion , "HTTP/1.1");
        assertEquals(httpRequestParser.requestResourceURI , "rose-blue-flower-rose-blooms-67636.jpeg");
        assertEquals(httpRequestParser.requestType , "GET");

    }

    @Test
    void canReturnMimeTypeBasedOnExtension(){
        assertEquals("image/jpeg",HttpResponse.getMimeType("jpeg"));
    }
}
