package view.navigation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import view.Explorer;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class SelectableNavigationLabel extends JLabel implements ActionListener, MouseListener {

    private final NavigationPanel grandpa;
    private final FolderPanel parent;
    private final int index;
    public boolean isSelected = false;

    private String filePath;

    public SelectableNavigationLabel(String str, String filePath, int index, NavigationPanel grandpa, FolderPanel parent) {
        super(str);
        setForeground(Explorer.UNSELECTED_TEXT_COLOR);
        setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TEXT_FONT_SIZE));
        this.filePath = filePath;
        this.grandpa = grandpa;
        this.parent = parent;
        this.index = index;
        addMouseListener(this);
        addActionListener(this);
    }

    public void select() {
        isSelected = true;
        setForeground(Explorer.SELECTED_TEXT_COLOR);
        grandpa.setNavLabel(filePath + File.separator + getText());
        grandpa.addFolderPanel(filePath + File.separator + getText(), index + 1);
    }
    public void deselect() {
        if (isSelected) {
            grandpa.removeFolderPanel(filePath + File.separator + getText(), index + 1);
            grandpa.setNavLabel(parent.folderPath);
            isSelected = false;
            setForeground(Explorer.UNSELECTED_TEXT_COLOR);
        }
    }

    public void performAction() {
        if (isSelected) {
            deselect();
        } else {
            for (SelectableNavigationLabel label : parent.files) {
                label.deselect();
            }
            select();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        performAction();
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        performAction();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}
    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
