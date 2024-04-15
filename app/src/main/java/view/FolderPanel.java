package view;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FolderPanel extends JPanel {

    NavigationPanel parent;

    List<SelectableNavigationLabel> files;
    int index;

    public FolderPanel(String filePath, int index, NavigationPanel parent) {
        super();
        this.index = index;
        this.parent = parent;
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        files = new LinkedList<>();
        File file = new File(filePath);
        for (String path : Arrays.stream(file.list()).toList()) {
            SelectableNavigationLabel label = new SelectableNavigationLabel(path, file.getPath(), index, parent, this);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            files.add(label);
        }
        for (JLabel label : files) {
            add(label);
        }
    }
}
