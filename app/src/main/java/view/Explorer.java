package view;

import utils.FileAction;
import utils.ConfigParser;

import view.bookmark.BookmarkPanel;
import view.navigation.NavigationPanel;
import view.visualization.VisualizationPanelBuilder;

import javax.swing.*;
import java.awt.*;

public class Explorer extends JFrame {

    public static final int TITLE_FONT_SIZE = 20;
    public static final int TEXT_FONT_SIZE = 18;
    public static final String APP_FONT = "Courier";
    public static final Color SELECTED_TEXT_COLOR = Color.RED;
    public static final Color UNSELECTED_TEXT_COLOR = Color.WHITE;

    public static final Color BACKGROUND_NAV_COLOR_DARK = new Color(30, 40, 65);
    public static final Color BACKGROUND_TITLE_COLOR_DARK = new Color(95, 105, 130);
    public static final Color BACKGROUND_BOOKMARK_COLOR_DARK = new Color(55, 70, 100);

    private JSplitPane mainPanel;
    private BookmarkPanel bookmarkPane;

    private JSplitPane contentPanel;
    private NavigationPanel navigationPane;
    private VisualizationPanelBuilder visualizationPanelBuilder;
    public ConfigParser parser;
    public boolean darkTheme = true;
    public int visualizationDividerLocation = 300;

    public Explorer() {
        parser = new ConfigParser();
        parser.parse(".explorer.conf");
        setTitle("Explorer");
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }

    public void init() {
        mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainPanel.setDividerLocation(200);
        mainPanel.setOneTouchExpandable(true);
        mainPanel.setContinuousLayout(true);
        bookmarkPane = new BookmarkPanel(this);
        bookmarkPane.setBookmarks(parser.bookmarks);

        contentPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        contentPanel.setOneTouchExpandable(true);
        contentPanel.setContinuousLayout(true);
        navigationPane = new NavigationPanel(this);
        visualizationPanelBuilder = new VisualizationPanelBuilder(this);

        contentPanel.setLeftComponent(navigationPane);

        mainPanel.setLeftComponent(bookmarkPane);
        mainPanel.setRightComponent(contentPanel);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(mainPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public void removeVisualizationPane() {
        visualizationDividerLocation = contentPanel.getDividerLocation();
        contentPanel.setRightComponent(null);
    }

    public void setVisualizationPane(FileAction action, String filePath) {
        switch (action) {
            case NONE:
                System.out.println("Extension not supported");

                visualizationPanelBuilder.setFilePath(filePath);
                contentPanel.setRightComponent(visualizationPanelBuilder.build());
                contentPanel.setDividerLocation(visualizationDividerLocation);
                break;
            case TEXT:
                System.out.println("Text extension");

                visualizationPanelBuilder.setFilePath(filePath);
                visualizationPanelBuilder.setTextArea(visualizationPanelBuilder.readFile(filePath));
                contentPanel.setRightComponent(visualizationPanelBuilder.build());
                contentPanel.setDividerLocation(visualizationDividerLocation);
                break;
            case IMAGE:
                System.out.println("Image extension");

                visualizationPanelBuilder.setFilePath(filePath);
                visualizationPanelBuilder.setImageIcon(new ImageIcon(filePath));
                contentPanel.setRightComponent(visualizationPanelBuilder.build());
                contentPanel.setDividerLocation(visualizationDividerLocation);
                break;
            case GIF:
                System.out.println("Gif extension");
                // TODO create a panel to display the gif
                break;
            default:
                break;
        }
    }

    public void addFolderPanel(String filePath, int callerIndex) {
        navigationPane.addFolderPanel(filePath, callerIndex);
    }

    public FileAction getAction(String fileExtension) {
        return parser.getAction(fileExtension);
    }
}