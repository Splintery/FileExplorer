package utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigParser {
    public List<String> bookmarks;

    private Map<String, FileAction> extensions;

    private String configFile;

    public ConfigParser(String configFile) {
        this.configFile = configFile;
        File file = new File(configFile);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println("Couldn't create file: "+ configFile);
                    throw new RuntimeException();
                }
                file.setReadable(true);
                file.setWritable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Create /home/"username"/.explorer.conf
            // set it readable and writable
            // Initialize /home/"username"/.explorer.conf with init_config.conf
        }
        if (file.length() == 0) {
            try (FileWriter writer = new FileWriter(file);) {
                writer.write(getFileContent("init_config.conf"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Parse /home/"username"/.explorer.conf
        parse();
    }

//    private InputStream getFileAsInputStream(final String fileName) {
//        InputStream ioStream = this.getClass()
//            .getClassLoader()
//            .getResourceAsStream(fileName);
//        if (ioStream == null) {
//            throw new IllegalArgumentException(fileName + " is not found");
//        }
//        return ioStream;
//    }

    public String getFileContent(String fileName) {
        String content = "";
        try {
            FileInputStream is = new FileInputStream(fileName);
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
    public void parse() {
        String bookmarkString = null;
        String extensionString = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            int lineCounter = 0;

            while ((line = reader.readLine()) != null) {
                if (lineCounter == 0) {
                    bookmarkString = line;
                } else {
                    extensionString += line;
                }
                lineCounter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (bookmarkString != null) {
            bookmarks = gson.fromJson(bookmarkString, List.class);
            System.out.println(bookmarks);
        }
        if (!extensionString.isEmpty()) {
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

    /*
[".", "..", "/home/yann", "/home/yann/EtudeSup"]
{".txt": "TEXT", ".java": "TEXT", ".jpg": "IMAGE", ".png": "IMAGE"}
    */

    public void setBookmarks(List<String> newBookmarks) {
        try(
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
        ) {
            String format = "[";
            for (int i = 0; i < newBookmarks.size(); i++) {
                format += "\"" + newBookmarks.get(i) + "\"";
                if (i < newBookmarks.size() - 1) {
                    format += ", ";
                }
            }
            format += "]\n";
            System.out.println("WRITING: " + format);
            writer.write(format);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(extensions));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
