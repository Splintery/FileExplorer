package utils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.checkerframework.checker.index.qual.NonNegative;

public class ConfigParser {
    public List<String> bookmarks;

    private Map<String, FileAction> extensions;

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

    public String getFileContent(String filePath) {
        String content = "";
        try {
            InputStream is = new FileInputStream(filePath);
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr);) {
                String line;
                while ((line = br.readLine()) != null) {
                    content += line + "\n";
                }
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
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
            Map<String, String> tmp = gson.fromJson(extensionString, Map.class);
            extensions = new HashMap<>();
            for (String s : tmp.keySet()) {
                extensions.put(s, getFileAction(tmp.get(s)));
            }
            System.out.println(extensions);
        }
    }

    private FileAction getFileAction(String s) {
        return switch (s) {
            case "TEXT" -> FileAction.TEXT;
            case "IMAGE" -> FileAction.IMAGE;
            case "GIF" -> FileAction.GIF;
            default -> FileAction.NONE;
        };
    }

//    {".txt": "TEXT", ".java": "TEXT", ".jpg": "IMAGE", ".png": "IMAGE"}
    public FileAction getAction(String fileExtension) {
        return extensions.get(fileExtension) != null ? extensions.get(fileExtension) : FileAction.NONE;
    }
}
