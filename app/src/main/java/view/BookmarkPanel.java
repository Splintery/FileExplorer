package view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BookmarkPanel extends JPanel {

    Explorer parent;

    JPanel titleContainer;
    JLabel titleLabel;

    JPanel contentContainer;
    List<SelectableBookmarkLabel> bookmarkLabels;


    public BookmarkPanel(Explorer parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        titleContainer = new JPanel();
        titleContainer.setLayout(new GridBagLayout());
        titleContainer.setPreferredSize(new Dimension(150, 50));
        titleContainer.setBackground(Color.RED);
        titleLabel = new JLabel("BookMarks");
        titleLabel.setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TITLE_FONT_SIZE));
        titleContainer.add(titleLabel);

        contentContainer = new JPanel();
        contentContainer.setBackground(Color.BLUE);
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS));

        bookmarkLabels = new LinkedList<>();

        add(titleContainer, BorderLayout.NORTH);
        add(new JScrollPane(contentContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
    }

    public void setBookmarks(List<String> bookmarks) {
        if (bookmarks != null) {
            for (String path : bookmarks) {
                SelectableBookmarkLabel label = new SelectableBookmarkLabel(path, this);
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                bookmarkLabels.add(label);
                contentContainer.add(label, BoxLayout.X_AXIS);
            }
        } else {
            System.out.println("Failed to display bookmarks");
        }
    }

    public void addFolderPanel(String filePath, int callerIndex) {
        parent.addFolderPanel(filePath, callerIndex);
    }
}
