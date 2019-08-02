package com.tavisca.workshops.HttpServer.FileUtilities;

import com.tavisca.workshops.HttpServer.HttpUtilities.HttpResponse;

import java.io.*;

public class FileReader {
    private int fileLength = 0;

    public byte[] readFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        fileLength = (int) file.length();
        byte[] fileDataBuffer = new byte[fileLength];
        fileInputStream.read(fileDataBuffer);

        fileInputStream.close();
        return fileDataBuffer;
    }

    public int getFileLengthOfReadFile(){
        return this.fileLength;
    }

}
