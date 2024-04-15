import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.Actions;
import view.Explorer;
import utils.ConfigParser;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class Main implements Runnable {
    public static void main(String[] args) {
        try {
		    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch(Exception e) {
		    e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Main());

        /*
        Map<String, Actions> testMap = new HashMap<>();
        testMap.put("Image", Actions.IMAGE);
        testMap.put("Text", Actions.TEXT);
        testMap.put("Video", Actions.VIDEO);
        testMap.put("Gif", Actions.GIF);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String mapJson = gson.toJson(testMap);
        System.out.println(mapJson);
        */
        /*
        List<String> test;
        String jsonString = "[\"bookmark1\",\"bookmark2\",\"bookmark3\",\"bookmark4\",\"bookmark5\"]";

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        test = gson.fromJson(jsonString, List.class);
        System.out.println(test.get(2));
        */
    }

    @Override
    public void run() {
        Explorer explorer = new Explorer();
    }
}