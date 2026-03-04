package org.example.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager{

    public static List<String> readFile(String fileName) throws IOException{
        InputStream inputStream=FileManager.class
                .getClassLoader()
                .getResourceAsStream(fileName);

        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines=new ArrayList<>();

        String line;
        while ((line=reader.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

}
