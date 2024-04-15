package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NavigationPanel extends JPanel {

    Explorer parent;

    JPanel titleContainer;
    JLabel titleLabel;

    JPanel contentContainer;


    public NavigationPanel(Explorer parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        titleContainer = new JPanel();
        titleContainer.setBackground(Color.RED);
        titleContainer.setPreferredSize(new Dimension(150, 50));
        titleContainer.setLayout(new BorderLayout());
        titleLabel = new JLabel("Navigation");
        titleLabel.setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TITLE_FONT_SIZE));
        titleContainer.add(titleLabel, BorderLayout.WEST);

        contentContainer = new JPanel();
        contentContainer.setBackground(Color.GREEN);
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.X_AXIS));

        add(titleContainer, BorderLayout.NORTH);
        addFolderPanel(".", 0);
        add(new JScrollPane(contentContainer, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
    }

    public void addFolderPanel(String filePath, int callerIndex) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            FolderPanel panel = new FolderPanel(filePath, callerIndex, this);
            int size = contentContainer.getComponents().length;
            while (callerIndex < size) {
                contentContainer.remove(size - 1);
                size--;
            }
            JScrollPane scroller = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroller.setPreferredSize(new Dimension(300, 300));
            contentContainer.add(scroller);
            parent.revalidate();
            parent.repaint();
        } else {
            System.out.println("Not a directory: " + filePath);
            // TODO check the extension and see if we can visualize it
        }
    }
    public void removeFolderPanel(int callerIndex) {
        int size = contentContainer.getComponents().length;
        while (callerIndex < size) {
            contentContainer.remove(size - 1);
            size--;
        }
        parent.revalidate();
        parent.repaint();
    }
}
