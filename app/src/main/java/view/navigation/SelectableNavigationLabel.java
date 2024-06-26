package view.navigation;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import utils.ExportTransferHandler;
import utils.SelectableLabel;
import view.Explorer;

public class SelectableNavigationLabel extends SelectableLabel implements MouseMotionListener {

    private final FolderPanel parent;
    private String filePath;

    public SelectableNavigationLabel(String str, String filePath, int index, Explorer explorer, FolderPanel parent) {
        super(str, explorer);
        this.parent = parent;
        this.filePath = filePath;
        this.index = index;
        File f = new File(getFilePath());
        if (f.isDirectory()) {
            unselectedColor = Explorer.UNSELECTED_DIR_COLOR;
        } else {
            unselectedColor = Explorer.UNSELECTED_TEXT_COLOR;
        }
        setForeground(unselectedColor);
        addMouseMotionListener(this);
        setTransferHandler(new ExportTransferHandler(getFilePath()));
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        SelectableNavigationLabel snl = (SelectableNavigationLabel)mouseEvent.getSource();
        TransferHandler th = snl.getTransferHandler();
        th.exportAsDrag(snl, mouseEvent, TransferHandler.COPY);
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {}

    @Override
    public String getFilePath() {
        return filePath + File.separator + getText();
    }

    @Override
    public String getFolderPath() {
        return parent.folderPath;
    }

    @Override
    public List<SelectableLabel> getContainingList() {
        return new LinkedList<>(parent.files);
    }
}