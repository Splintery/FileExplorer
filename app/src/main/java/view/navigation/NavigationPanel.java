package view.navigation;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import view.Explorer;

public class NavigationPanel extends JPanel {

    Explorer parent;

    JPanel titleContainer;
    JLabel navTitleLabel;

    JPanel contentContainer;


    public NavigationPanel(Explorer parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        titleContainer = new JPanel();
        titleContainer.setBackground(Explorer.BACKGROUND_TITLE_COLOR_DARK);
        titleContainer.setPreferredSize(new Dimension(150, 50));
        titleContainer.setLayout(new GridBagLayout());
        navTitleLabel = new JLabel(".");
        navTitleLabel.setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TITLE_FONT_SIZE));
        titleContainer.add(navTitleLabel);

        contentContainer = new JPanel();
        contentContainer.setBackground(Explorer.BACKGROUND_NAV_COLOR_DARK);
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.X_AXIS));

        add(new JScrollPane(titleContainer, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.NORTH);
        JScrollPane tmp = new JScrollPane(contentContainer, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        addFolderPanel(".", 0);
        add(tmp, BorderLayout.CENTER);
    }

    public void addFolderPanel(String filePath, int callerIndex) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            removeFolders(callerIndex);

            FolderPanel panel = new FolderPanel(filePath, callerIndex, this);
            JScrollPane scroller = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroller.setPreferredSize(new Dimension(panel.getLongestSize() * Explorer.TEXT_FONT_SIZE, 300));
            contentContainer.add(scroller);

            parent.revalidate();
            parent.repaint();
        } else {
            parent.setVisualizationPane(parent.getAction(getExtension(filePath)), filePath);

            parent.revalidate();
            parent.pack();
        }
    }
    private String getExtension(String filePath) {
        String reversedFileExtension = "";
        for (int i = filePath.length(); i >= 0; i--) {
            reversedFileExtension += filePath.substring(i - 1, i);
            if (filePath.substring(i - 1, i).equals(".")) {
                break;
            }
        }
        String fileExtension = "";
        for (int i = reversedFileExtension.length(); i > 0; i--) {
            fileExtension += reversedFileExtension.substring(i - 1, i);
        }
        return fileExtension;
    }

    private boolean removeFolders(int callerIndex) {
        int size = contentContainer.getComponents().length;
        boolean hasDeleted = false;
        while (callerIndex < size) {
            contentContainer.remove(size - 1);
            size--;
            hasDeleted = true;
        }
        return hasDeleted;
    }

    public void removeFolderPanel(String filePath, int callerIndex) {
        removeFolders(callerIndex);
        File file = new File(filePath);
        if (file.isFile()) {
            parent.removeVisualizationPane();
        }
        parent.revalidate();
        parent.repaint();
    }
    public void setNavLabel(String newLabel) {
        navTitleLabel.setText(newLabel);
    }
    public String getCurrentFolder() {
        return navTitleLabel.getText();
    }
}
