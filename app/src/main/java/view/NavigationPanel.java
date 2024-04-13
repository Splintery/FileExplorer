package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NavigationPanel extends JPanel implements MouseListener {

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
        contentContainer.addMouseListener(this);
        contentContainer.setBackground(Color.GREEN);
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.X_AXIS));

        add(titleContainer, BorderLayout.NORTH);
        add(new JScrollPane(contentContainer, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
    }

    public void addFolderPanel(String filePath, int callerIndex) {
        FolderPanel panel = new FolderPanel(filePath);
        int size = contentContainer.getComponents().length;
        while (callerIndex != size || size != 0) {
            contentContainer.remove(size - 1);
            size--;
        }
        contentContainer.add(panel);
        parent.revalidate();
        parent.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("Mouse Clicked");
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
