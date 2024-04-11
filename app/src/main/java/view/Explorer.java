package view;

import javax.swing.*;
import java.awt.*;

public class Explorer extends JFrame {

    private JSplitPane mainPanel;
    private JPanel bookmarkPane;
    private JPanel navigationPane;

    public Explorer() {
        setTitle("Explorer");
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }

    public void init() {
        mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        bookmarkPane = new BookMarkPane();
        navigationPane = new NavigationPanel();

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
}