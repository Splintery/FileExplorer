package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class SelectableBookmarkLabel extends JLabel implements ActionListener, MouseListener {
    private BookmarkPanel parent;
    public boolean isSelected = false;

    public SelectableBookmarkLabel(String str, BookmarkPanel parent) {
        super(str);
        setForeground(Explorer.UNSELECTED_TEXT_COLOR);
        setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TEXT_FONT_SIZE));
        this.parent = parent;
        addMouseListener(this);
        addActionListener(this);
    }

    public void select() {
        isSelected = true;
        setForeground(Explorer.SELECTED_TEXT_COLOR);
        parent.addFolderPanel(getText(), 0);
    }
    public void deselect() {
        isSelected = false;
        setForeground(Explorer.UNSELECTED_TEXT_COLOR);
    }

    public void performAction() {
        for (SelectableBookmarkLabel label : parent.bookmarkLabels) {
            label.deselect();
        }
        select();
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
    public void mousePressed(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
