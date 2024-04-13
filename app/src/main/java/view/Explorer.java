package view;

import utils.ConfigParser;

import javax.swing.*;
import java.awt.*;

public class Explorer extends JFrame {

    public static final int TITLE_FONT_SIZE = 20;
    public static final int TEXT_FONT_SIZE = 18;
    public static final String APP_FONT = "Courier";
    public static final Color SELECTED_TEXT_COLOR = Color.RED;
    public static final Color UNSELECTED_TEXT_COLOR = Color.WHITE;

    private JSplitPane mainPanel;
    private BookmarkPanel bookmarkPane;
    private NavigationPanel navigationPane;
    public ConfigParser parser;

    public Explorer() {
        parser = new ConfigParser();
        parser.parse(".explorer.conf");
        setTitle("Explorer");
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }

    public void init() {
        mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        bookmarkPane = new BookmarkPanel(this);
        bookmarkPane.setBookmarks(parser.bookmarks);
        navigationPane = new NavigationPanel(this);

        mainPanel.setDividerLocation(200);
        mainPanel.setOneTouchExpandable(true);
        mainPanel.setContinuousLayout(true);
        mainPanel.setLeftComponent(bookmarkPane);
        mainPanel.setRightComponent(new JScrollPane(navigationPane, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(mainPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public void addFolderPanel(String filePath, int callerIndex) {
        navigationPane.addFolderPanel(filePath, callerIndex);
    }
}