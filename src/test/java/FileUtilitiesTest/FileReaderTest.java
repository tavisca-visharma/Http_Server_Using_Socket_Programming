package FileUtilitiesTest;

import com.tavisca.workshops.HttpServer.FileUtilities.FileReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReaderTest {

    @Test
    void canReadTheGivenFileInBytes(){
        File file = new File("src\\main\\resources\\HttpServerResources\\test.html");
        try {
            String testFile = "<!DOCTYPE html>\r\n" +
                    "<html>\r\n" +
                    "<head>\r\n" +
                    "    <style>\r\n" +
                    "body {\r\n" +
                    "  background-color: lightblue;\r\n" +
                    "}\r\n" +
                    "\r\n" +
                    "h1 {\r\n" +
                    "  color: white;\r\n" +
                    "  text-align: center;\r\n" +
                    "}\r\n" +
                    "\r\n" +
                    "p {\r\n" +
                    "  font-family: verdana;\r\n" +
                    "  font-size: 20px;\r\n" +
                    "}\r\n" +
                    "\r\n" +
                    "    </style>\r\n" +
                    "</head>\r\n" +
                    "<body>\r\n" +
                    "\r\n" +
                    "<h1>My First CSS Example</h1>\r\n" +
                    "<p>This is a paragraph.</p>\r\n" +
                    "\r\n" +
                    "</body>\r\n" +
                    "</html>\r\n" ;
            FileReader fileReader = new FileReader();
            byte[] fileInBytes = fileReader.readFromFile(file);
            assertEquals(testFile,new String(fileInBytes));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void canReturnTheGivenFileLength(){
        File file = new File("src\\main\\resources\\HttpServerResources\\test.html");
        try {

            String testFile = "<!DOCTYPE html>\r\n" +
                    "<html>\r\n" +
                    "<head>\r\n" +
                    "    <style>\r\n" +
                    "body {\r\n" +
                    "  background-color: lightblue;\r\n" +
                    "}\r\n" +
                    "\r\n" +
                    "h1 {\r\n" +
                    "  color: white;\r\n" +
                    "  text-align: center;\r\n" +
                    "}\r\n" +
                    "\r\n" +
                    "p {\r\n" +
                    "  font-family: verdana;\r\n" +
                    "  font-size: 20px;\r\n" +
                    "}\r\n" +
                    "\r\n" +
                    "    </style>\r\n" +
                    "</head>\r\n" +
                    "<body>\r\n" +
                    "\r\n" +
                    "<h1>My First CSS Example</h1>\r\n" +
                    "<p>This is a paragraph.</p>\r\n" +
                    "\r\n" +
                    "</body>\r\n" +
                    "</html>\r\n" ;

            FileReader fileReader = new FileReader();
            byte[] fileInBytes = fileReader.readFromFile(file);
            int fileLength = fileReader.getFileLengthOfReadFile();
            assertEquals(testFile.getBytes().length,fileLength);
        } catch (IOException e) {
            fail();
        }
    }

}
