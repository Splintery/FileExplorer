package view;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FolderPanel extends JPanel {

    List<JLabel> files;

    public FolderPanel(String filePath) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        files = new LinkedList<>();
        File file = new File(filePath);
        if (file.isDirectory()) {
            for (String path : Arrays.stream(file.list()).toList()) {
                files.add(new JLabel(path));
            }
        }
        for (JLabel label : files) {
            add(label);
        }
    }
}
