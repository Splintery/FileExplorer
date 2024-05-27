package utils;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static utils.FileAction.NONE;

public class ConfigParser {
    public List<String> bookmarks;

    private Map<String, FileAction> extensions;

    private String configFile;

    public ConfigParser(String userHome) {
        configFile = userHome + File.separator + ".explorer.conf";
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
        File rubbishBin = new File(userHome + File.separator + ".poubelle");
        if (!rubbishBin.exists()) {
            if (!rubbishBin.mkdir()) {
                System.out.println("failed to create rubbish bin");
                System.exit(0);
            }
        }
        // Parse /home/"username"/.explorer.conf
        parse();
    }

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
        String bookmarkString = "";
        String extensionString = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            boolean switchString = false;

            while ((line = reader.readLine()) != null) {
                if (!switchString) {
                    bookmarkString += line;
                } else {
                    extensionString += line;
                }
                if (line.contains("]")) {switchString = true;}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (!bookmarkString.isEmpty()) {
            bookmarks = gson.fromJson(bookmarkString, List.class);
        }
        if (!extensionString.isEmpty()) {
            Map<String, String> tmp = gson.fromJson(extensionString, Map.class);
            extensions = new HashMap<>();
            for (String s : tmp.keySet()) {
                extensions.put(s, getFileActionFromString(tmp.get(s)));
            }
        }
    }

    public FileAction getFileActionFromString(String s) {
        return switch (s) {
            case "TEXT" -> FileAction.TEXT;
            case "IMAGE" -> FileAction.IMAGE;
            case "GIF" -> FileAction.GIF;
            default -> NONE;
        };
    }
    public String getStringFromFileAction(FileAction fa) {
        return switch (fa) {
            case TEXT -> "TEXT";
            case IMAGE -> "IMAGE";
            case GIF -> "GIF";
            default -> "NONE";
        };

    }

//    {".txt": "TEXT", ".java": "TEXT", ".jpg": "IMAGE", ".png": "IMAGE"}
    public FileAction getAction(String fileExtension) {
        return extensions.get(fileExtension) != null ? extensions.get(fileExtension) : NONE;
    }

    /*
[".", "..", "/home/yann", "/home/yann/EtudeSup"]
{".txt": "TEXT", ".java": "TEXT", ".jpg": "IMAGE", ".png": "IMAGE"}
    */

    public void setBookmarks(List<String> newBookmarks) {
        this.bookmarks = newBookmarks;
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
            writer.write(format);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(extensions));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Map<String, FileAction> getExtensions() {return extensions;};

    public void setExtensions(Map<String, FileAction> extensions) {
        this.extensions = extensions;
        List<String> ext = new LinkedList<>(extensions.keySet());
        List<String> visual = new LinkedList<>();
        for (String s : extensions.keySet()) {
            visual.add(getStringFromFileAction(extensions.get(s)));
        }
        try(
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
        ) {
            String format = "\n{";
            for (int i = 0; i < ext.size(); i++) {
                format += "\"" + ext.get(i) + "\": \"" + visual.get(i) + "\"";
                if (i < ext.size() - 1) {
                    format += ", ";
                }
            }

            format += "}\n";
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(bookmarks));
            writer.write(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
