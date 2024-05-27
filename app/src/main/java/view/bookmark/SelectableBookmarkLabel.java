package view.bookmark;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import utils.SelectableLabel;
import view.Explorer;


public class SelectableBookmarkLabel extends SelectableLabel {
    private BookmarkPanel parent;
    private String filePath;

    public SelectableBookmarkLabel(String str, Explorer explorer, BookmarkPanel parent) {
        super(str, explorer);
        setText(getLastFolder(str));
        setToolTipText(str);
        this.parent = parent;
        filePath = str;
        File f = new File(getFilePath());
        if (f.isDirectory()) {
            unselectedColor = Explorer.UNSELECTED_DIR_COLOR;
        } else {
            unselectedColor = Explorer.UNSELECTED_TEXT_COLOR;
        }
        setForeground(unselectedColor);
    }

    private String getLastFolder(String str) {
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i).contains(File.separator)) {
                index = i;
            }
        }
        return str.substring(index);
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public String getFolderPath() {
        return ".";
    }

    @Override
    public List<SelectableLabel> getContainingList() {
        return new LinkedList<>(parent.getBookmarks());
    }
}