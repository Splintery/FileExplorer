package view.visualization;

import view.Explorer;

import javax.swing.*;
import java.awt.*;

public class ImageVisualizationPanel extends VisualizationPanel {
    private ImageIcon imageIcon = null;

    public ImageVisualizationPanel(Explorer parent, String filePath, ImageIcon image) {
        super(parent, filePath);
        this.imageIcon = image;
        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setBackground(Explorer.BACKGROUND_BOOKMARK_COLOR_DARK);

        imagePanel.add(new JLabel(imageIcon));
        JScrollPane scroller = new JScrollPane(imagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scroller, BorderLayout.CENTER);
    }
}
