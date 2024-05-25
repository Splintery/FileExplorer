package utils;

import view.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public abstract class SelectableLabel extends JLabel implements MouseListener, ActionListener {
    protected Explorer explorer;
    protected int index = 0;
    protected boolean isSelected = false;

    public SelectableLabel(String str, Explorer explorer) {
        super(str);
        setForeground(Explorer.UNSELECTED_TEXT_COLOR);
        setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TEXT_FONT_SIZE));
        this.explorer = explorer;
        addMouseListener(this);
        addActionListener(this);
    }

    public abstract String getFilePath();
    public abstract String getFolderPath();
    public abstract List<SelectableLabel> getContainingList();

    public void select() {
        isSelected = true;
        setForeground(Explorer.SELECTED_TEXT_COLOR);
        explorer.setNavLabel(getFilePath());
        explorer.addFolderPanel(getFilePath(), index + 1);
    }
    public void deselect() {
        if (isSelected) {
            explorer.removeFolderPanel(getFilePath(), index + 1);
            explorer.setNavLabel(getFolderPath());
            isSelected = false;
            setForeground(Explorer.UNSELECTED_TEXT_COLOR);
        }
    }
    public void performAction() {
        if (isSelected) {
            deselect();
        } else {
            for (SelectableLabel label : getContainingList()) {
                label.deselect();
            }
            select();
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {performAction();}
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {performAction();}
    @Override
    public void mousePressed(MouseEvent mouseEvent) {}
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}
    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
