package view.navigation;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import view.Explorer;

import static java.util.Arrays.sort;

public class FolderPanel extends JPanel {

    Explorer explorer;
    String folderPath;

    List<SelectableNavigationLabel> files;
    int index;

    public FolderPanel(String filePath, int index, Explorer explorer) {
        super();
        this.index = index;
        this.explorer = explorer;
        setBackground(Explorer.BACKGROUND_NAV_COLOR_DARK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        files = new LinkedList<>();
        File file = new File(filePath);
        folderPath = file.getPath();
        String[] tmp = file.list();
        sort(tmp);
        for (String fileName : Arrays.stream(tmp).toList()) {
            SelectableNavigationLabel label = new SelectableNavigationLabel(fileName, file.getPath(), index, explorer, this);
//            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            files.add(label);
        }
        for (JLabel label : files) {
            add(label);
        }
    }

    public int getLongestSize() {
        int maxLength = 0;
        for (SelectableNavigationLabel label : files) {
            if (label.getText().length() > maxLength) {
                maxLength = label.getText().length();
            }
        }
        return maxLength;
    }
}
