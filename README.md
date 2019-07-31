# Http_Server_Using_Socket_Programming<br/>
Created a HTTP Web Server that runs on port 80 and serves the requests made by the clients.<br/>

Write a Java application to create a minimal HTTP web server that serves GET requests with requested html resources if available, else responds with error message.<br/>

(Putting the pieces together)<br/>
Directions to get started: <br/>
1.	Create a simple TCP Socket  based server side app (using port 80). <br/>
		(Print whatever you receive on inputstream)<br/>
2.	In your browser goto “http://localhost” (put this in address bar and press enter)<br/>
		(Observe the console for the print statements you made in step 1)<br/>
3.	Handle the requests based on the observations in step 2.<br/>
4.	Improve your app to support multiple concurrent requests using threads. (if not already  done in step 1).<br/>

Expectations:<br/>
1.	When an html document requested through browser, it should  serve the request with appropriate response.<br/>
2.	Else should respond with an error message.<br/>


Supporting Information:<br/>

HTTP response format:<br/>

PROTOCOL_WITH_VERSION RESPONSE_CODE STATUS   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      Example:=>    HTTP/1.1 200 OK<br/>
Brief Description about the server.            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          Example:=>    Server: My Java HTTP Server : 1.0<br/>
Date: RESPONSE_DATE_AND_TIME		  <br/>
Content-type: CONTENT_TYPE				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Example:=>    Content-type: text/html<br/>
Content-length: SIZE_OF_RESPONSE_FILE		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     Example:=>    Content-length: 256<br/>
NEW_LINE<br/>
ACTUAL_FILE_CONTENT_BYTES<br/>

References: <br/>
https://www.tutorialspoint.com/http/http_requests <br/>
https://www.tutorialspoint.com/http/http_responses <br/>
https://www.w3.org/Protocols/rfc2616/rfc2616.html <br/>
https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3

