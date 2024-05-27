package view.find;

import utils.SelectableLabel;
import view.Explorer;
import utils.ExportTransferHandler;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SelectableFindLabel extends SelectableLabel implements MouseMotionListener {
    FindResultPanel parent;

    public SelectableFindLabel(String str, Explorer explorer, FindResultPanel findResultPanel) {
        super(str, explorer);
        this.parent = findResultPanel;
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
        SelectableFindLabel snl = (SelectableFindLabel) mouseEvent.getSource();
        TransferHandler th = snl.getTransferHandler();
        th.exportAsDrag(snl, mouseEvent, TransferHandler.COPY);
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {}

    @Override
    public String getFilePath() {
        return getText();
    }

    @Override
    public String getFolderPath() {
        return ".";
    }

    @Override
    public List<SelectableLabel> getContainingList() {
        return new LinkedList<>(parent.getLabels());
    }
}
