package view;

import utils.FileAction;
import utils.ConfigParser;

import utils.FinderSwingWorker;
import view.Dialog.EditBookmarkDialog;
import view.bookmark.BookmarkPanel;
import view.bookmark.SelectableBookmarkLabel;
import view.navigation.NavigationPanel;
import view.visualization.VisualizationPanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.LinkedList;
import java.util.List;

public class Explorer extends JFrame implements ActionListener, WindowListener {

    public static final int TITLE_FONT_SIZE = 20;
    public static final int TEXT_FONT_SIZE = 18;
    public static final String APP_FONT = "Courier";
    public static final Color SELECTED_TEXT_COLOR = Color.RED;
    public static final Color UNSELECTED_TEXT_COLOR = Color.WHITE;

    public static final Color BACKGROUND_NAV_COLOR_DARK = new Color(30, 40, 65);
    public static final Color BACKGROUND_TITLE_COLOR_DARK = new Color(95, 105, 130);
    public static final Color BACKGROUND_BOOKMARK_COLOR_DARK = new Color(55, 70, 100);
    public static final Color BACKGROUND_MENU_COLOR_DARK = new Color(120, 135, 175);

    public String userHomeDir;

    private JMenuBar menuBar;

    private EditBookmarkDialog editBookmarksDialog;
    private JDialog editVisualizer;

    private JSplitPane mainPanel;
    private BookmarkPanel bookmarkPane;

    private JSplitPane contentPanel;
    private NavigationPanel navigationPane;
    private VisualizationPanelBuilder visualizationPanelBuilder;
    public ConfigParser parser;
    public boolean darkTheme = true;
    public int visualizationDividerLocation = 300;

    public Explorer() {
        userHomeDir = System.getProperty("user.home");
        parser = new ConfigParser(userHomeDir + FileSystems.getDefault().getSeparator() + ".explorer.conf");
        setTitle("Explorer");
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(this);
        init();
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(BACKGROUND_NAV_COLOR_DARK);
        String[][] toolBarTitles = {
                {"Config", "Modify bookmarks and file visualizations"},
                {"Find", "Find file/folder on computer"},
                {"Tools", "Tinker with files/folders"},
                {"Preferences", "Change color schemes"}
        };
        for (String[] titles : toolBarTitles) {
            JMenu menu = new JMenu(titles[0]);
            menu.setForeground(Color.WHITE);
            menu.setBackground(BACKGROUND_MENU_COLOR_DARK);
            menu.setToolTipText(titles[1]);
            menuBar.add(menu);
        }
        JMenuItem editBookmarks = new JMenuItem("Edit bookmarks");
        JMenuItem editVisualizer = new JMenuItem("Edit file visualizer");

        editBookmarks.addActionListener(this);
        editVisualizer.addActionListener(this);

        menuBar.getMenu(0).add(editBookmarks);
        menuBar.getMenu(0).add(editVisualizer);

        JMenuItem findFromHome = new JMenuItem("From Home");

        findFromHome.addActionListener(this);

        menuBar.getMenu(1).add(findFromHome);

        JMenuItem rename = new JMenuItem("Rename");
        JMenuItem move = new JMenuItem("Move");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem chmod = new JMenuItem("Chmod");
        JMenuItem create = new JMenuItem("Create");
        menuBar.getMenu(2).add(rename);
        menuBar.getMenu(2).add(move);
        menuBar.getMenu(2).add(delete);
        menuBar.getMenu(2).add(chmod);
        menuBar.getMenu(2).add(create);

        JMenuItem darkTheme = new JMenuItem("Dark theme");
        JMenuItem lightTheme = new JMenuItem("Light theme");
        darkTheme.setEnabled(false);
        menuBar.getMenu(3).add(darkTheme);
        menuBar.getMenu(3).add(lightTheme);


        setJMenuBar(menuBar);
    }
    private void initDialogs() {
        editBookmarksDialog = new EditBookmarkDialog(this, "Edit bookmarks");


        editVisualizer = new JDialog(this, "Edit file visualizer");
        editVisualizer.setLayout(new GridBagLayout());
        editVisualizer.add(new JLabel("test"));
        editVisualizer.setSize(new Dimension(300, 300));
        editVisualizer.setLocationRelativeTo(null);
    }
    public void init() {
        initMenuBar();
        initDialogs();

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == menuBar.getMenu(0).getItem(0)) {
            editBookmarksDialog.loadDialog();
        } else if (actionEvent.getSource() == menuBar.getMenu(0).getItem(1)) {
            System.out.println("YEEEEEY");
            editVisualizer.setVisible(true);
        } else if (actionEvent.getSource() == menuBar.getMenu(1).getItem(0)) {
            System.out.println("finding");
            FinderSwingWorker fw = new FinderSwingWorker(this, "*.java");
            fw.execute();
        }
    }

    public List<SelectableBookmarkLabel> getBookmarks() {
        return bookmarkPane.getBookmarks();
    }
    public void setBookmarks(List<String> newBookmarks) {
        bookmarkPane.setBookmarks(newBookmarks);
        parser.setBookmarks(newBookmarks);
        bookmarkPane.revalidate();
        bookmarkPane.repaint();
    }
    public void addBookmark(String newBookmark) {
        List<SelectableBookmarkLabel> list = bookmarkPane.getBookmarks();
        LinkedList<String> newBkL = new LinkedList<>();
        for (SelectableBookmarkLabel sbkl : list) {
            newBkL.add(sbkl.getText());
        }
        newBkL.add(newBookmark);
        setBookmarks(newBkL);
        System.out.println(newBookmark);
    }
    public String getUserHomeDir() {
        return userHomeDir;
    }
    public String getCurrentFolder() {
        return navigationPane.getCurrentFolder();
    }
    public void setNavLabel(String newLabel) {
        navigationPane.setNavLabel(newLabel);
    }
    public void removeFolderPanel(String filePath, int callerIndex) {
        navigationPane.removeFolderPanel(filePath, callerIndex);
    }
    @Override
    public void windowOpened(WindowEvent windowEvent) {}
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        System.out.println("Closing it !");
    }
    @Override
    public void windowClosed(WindowEvent windowEvent) {}
    @Override
    public void windowIconified(WindowEvent windowEvent) {}
    @Override
    public void windowDeiconified(WindowEvent windowEvent) {}
    @Override
    public void windowActivated(WindowEvent windowEvent) {}
    @Override
    public void windowDeactivated(WindowEvent windowEvent) {}
}