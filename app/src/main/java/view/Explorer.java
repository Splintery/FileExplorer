package view;

import utils.FileAction;
import utils.ConfigParser;

import view.dialog.*;
import view.find.FindResultPanel;
import view.find.FinderSwingWorker;
import view.bookmark.BookmarkPanel;
import view.bookmark.SelectableBookmarkLabel;
import view.navigation.NavigationPanel;
import view.visualization.VisualizationPanelBuilder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Explorer extends JFrame implements ActionListener, WindowListener {

    public static final int TITLE_FONT_SIZE = 20;
    public static final int TEXT_FONT_SIZE = 18;
    public static final String APP_FONT = "Courier";
    public static final Color SELECTED_TEXT_COLOR = Color.RED;
    public static final Color UNSELECTED_TEXT_COLOR = Color.WHITE;
    public static final Color UNSELECTED_DIR_COLOR = Color.GRAY;

    public static final Color BACKGROUND_NAV_COLOR_DARK = new Color(30, 40, 65);
    public static final Color BACKGROUND_TITLE_COLOR_DARK = new Color(95, 105, 130);
    public static final Color BACKGROUND_BOOKMARK_COLOR_DARK = new Color(55, 70, 100);
    public static final Color BACKGROUND_MENU_COLOR_DARK = new Color(120, 135, 175);

    public String userHomeDir;

    private JMenuBar menuBar;

    private EditBookmarkDialog editBookmarksDialog;
    private EditVisualisationDialog editVisualizer;
    private FindDialog findDialog;
    private RenameDialog renameDialog;
    private DeleteDialog deleteDialog;
    private MoveDialog moveDialog;
    private ChmodDialog chmodDialog;
    private CreateDirDialog createDirDialog;

    private JSplitPane mainPanel;
    private BookmarkPanel bookmarkPane;

    private JSplitPane contentPanel;
    private NavigationPanel navigationPane;
    private VisualizationPanelBuilder visualizationPanelBuilder;
    public ConfigParser parser;
    public boolean darkTheme = true;
    public int visualizationDividerLocation = 300;

    FindResultPanel findResultPane;

    public Explorer() {
        userHomeDir = System.getProperty("user.home");
        parser = new ConfigParser(userHomeDir);
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

        JMenuItem findFromCurrentFolder = new JMenuItem("From Current Folder");
        JMenuItem findFromHome = new JMenuItem("From Home");

        findFromCurrentFolder.addActionListener(this);
        findFromHome.addActionListener(this);

        menuBar.getMenu(1).add(findFromCurrentFolder);
        menuBar.getMenu(1).add(findFromHome);

        JMenuItem darkTheme = new JMenuItem("Dark theme");
        JMenuItem lightTheme = new JMenuItem("Light theme");
        darkTheme.setEnabled(false);
        menuBar.getMenu(2).add(darkTheme);
        menuBar.getMenu(2).add(lightTheme);


        setJMenuBar(menuBar);
    }
    private void initDialogs() {
        editBookmarksDialog = new EditBookmarkDialog(this, "Edit bookmarks");
        findDialog = new FindDialog(this, "Find");
        editVisualizer = new EditVisualisationDialog(this, "Edit visualisations");
        renameDialog = new RenameDialog(this, "Rename current folder/file");
        deleteDialog = new DeleteDialog(this, "Confirmation");
        moveDialog = new MoveDialog(this, "Move current folder/file");
        chmodDialog = new ChmodDialog(this, "Change current folder/file accessibility");
        createDirDialog = new CreateDirDialog(this, "Create directory in current folder");
    }
    private void initLeftMenuButtons() {
        JPanel modifyPanel = new JPanel();
        modifyPanel.setLayout(new BoxLayout(modifyPanel, BoxLayout.Y_AXIS));
        modifyPanel.setAlignmentX(SwingConstants.CENTER);
        modifyPanel.setAlignmentY(SwingConstants.CENTER);
        modifyPanel.setPreferredSize(new Dimension(80, 50));

        JButton rename = new JButton("Rename");
        rename.setFont(new Font(APP_FONT, Font.BOLD, 14));
        rename.setMaximumSize(new Dimension(75, 65));
        rename.addActionListener(this);
        modifyPanel.add(rename);

        JButton del = new JButton("Delete");
        del.setFont(new Font(APP_FONT, Font.BOLD, 14));
        del.setMaximumSize(new Dimension(75, 65));
        del.addActionListener(this);
        modifyPanel.add(del);

        JButton ch = new JButton("Chmod");
        ch.setFont(new Font(APP_FONT, Font.BOLD, 14));
        ch.setMaximumSize(new Dimension(75, 65));
        ch.addActionListener(this);
        modifyPanel.add(ch);

        JButton move = new JButton("Move");
        move.setFont(new Font(APP_FONT, Font.BOLD, 14));
        move.setMaximumSize(new Dimension(75, 65));
        move.addActionListener(this);
        modifyPanel.add(move);

        JButton mkdir = new JButton("Mkdir");
        mkdir.setFont(new Font(APP_FONT, Font.BOLD, 14));
        mkdir.setMaximumSize(new Dimension(75, 65));
        mkdir.addActionListener(this);
        modifyPanel.add(mkdir);

        getContentPane().add(modifyPanel, BorderLayout.WEST);

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

        initLeftMenuButtons();

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
                visualizationPanelBuilder.setFilePath(filePath);
                contentPanel.setRightComponent(visualizationPanelBuilder.build());
                contentPanel.setDividerLocation(visualizationDividerLocation);
                break;
            case TEXT:
                visualizationPanelBuilder.setFilePath(filePath);
                visualizationPanelBuilder.setTextArea(visualizationPanelBuilder.readFile(filePath));
                contentPanel.setRightComponent(visualizationPanelBuilder.build());
                contentPanel.setDividerLocation(visualizationDividerLocation);
                break;
            case IMAGE:
                visualizationPanelBuilder.setFilePath(filePath);
                visualizationPanelBuilder.setImageIcon(new ImageIcon(filePath));
                contentPanel.setRightComponent(visualizationPanelBuilder.build());
                contentPanel.setDividerLocation(visualizationDividerLocation);
                break;
            case GIF:
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
        switch (actionEvent.getActionCommand()) {
            case "Edit bookmarks":
                editBookmarksDialog.loadDialog();
                break;
            case "Edit file visualizer":
                editVisualizer.loadDialog();
                break;
            case "From Current Folder":
                if (findResultPane != null) {
                    removeFindView();
                }
                findDialog.loadDialog(getCurrentFolder());
                break;
            case "From Home":
                if (findResultPane != null) {
                    removeFindView();
                }
                findDialog.loadDialog(getUserHomeDir());
                break;
            case "Rename":
                renameDialog.loadDialog();
                break;
            case "Delete":
                deleteDialog.loadDialog();
                break;
            case "Chmod":
                chmodDialog.loadDialog();
                break;
            case "Move":
                moveDialog.loadDialog();
                break;
            case "Mkdir":
                createDirDialog.loadDialog();
                break;
            default:
                break;
        }
    }

    public void generateFindView(String folderPath, String regex) {
        FinderSwingWorker fw = new FinderSwingWorker(this, folderPath, regex);
        fw.execute();
    }
    public void addFindView(List<String> l) {
        findResultPane = new FindResultPanel(this, l);
        getContentPane().add(findResultPane, BorderLayout.EAST);
        validate();
    }
    public void removeFindView() {
        getContentPane().remove(findResultPane);
        validate();
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
            newBkL.add(sbkl.getFilePath());
        }
        newBkL.add(newBookmark);
        setBookmarks(newBkL);
    }
    public void addExtension(String ext, String visual) {
        Map<String, FileAction> newExt = parser.getExtensions();
        newExt.put(ext, parser.getFileActionFromString(visual));
        parser.setExtensions(newExt);
    }
    public void createDir(String location, String name) {
        File loc = new File(location);
        if (loc.exists() && loc.isDirectory()) {
            System.out.println(location + File.separator + name);
            File newDir = new File(location + File.separator + name);
            if (!newDir.exists()) {
                newDir.mkdir();
            }
        }
    }
    public void rename(String newName, String oldName) {
        File old = new File(oldName);
        if (old.exists()) {
            File newDest = new File(newName);
            if (!newDest.exists()) {
                if(!old.renameTo(newDest)) {
                    System.out.println("Failed to rename");
                } else {
                    System.out.println("Renamed: " + oldName + ",to: " + newName);
                }
            }
        }
    }
    public boolean chmod(boolean writable, boolean readable, boolean executable, boolean ownerOnly) {
        File f = new File(getCurrentFolder());
        return (
                    f.setWritable(writable, ownerOnly)
                    && f.setReadable(readable, ownerOnly)
                    && f.setExecutable(executable, ownerOnly)
                );
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