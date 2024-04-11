package utils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigParser {
    private List<String> bookmarks;

    private Map<String, Actions> extensions;

    public ConfigParser() {}

    private InputStream getFileAsIOStream(final String fileName) {
        InputStream ioStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    public void parse(String fileName) {
        // TODO convert file from JSON to get bookmarks and extensions
        String bookmarkString = null;
        String extensionString = null;
        try {
            InputStream is = this.getFileAsIOStream(fileName);
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr);) {
                String line;
                int lineCounter = 0;
                while ((line = br.readLine()) != null) {
                    if (lineCounter == 0) {
                        bookmarkString = line;
                    } else {
                        extensionString = line;
                    }
                    lineCounter++;
                }
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (bookmarkString != null) {
            bookmarks = gson.fromJson(bookmarkString, List.class);
            System.out.println(bookmarks);
        }
        if (extensionString != null) {
            extensions = gson.fromJson(extensionString, Map.class);
            System.out.println(extensions);
        }
    }
}
