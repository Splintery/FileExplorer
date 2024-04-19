package view.visualization;

import view.Explorer;

import javax.swing.*;
import java.awt.*;

public class UnsupportedVisualizationPanel extends VisualizationPanel {
    public UnsupportedVisualizationPanel(Explorer parent, String filePath) {
        super(parent, filePath);

        JPanel notSupported = new JPanel(new GridBagLayout());
        notSupported.setBackground(Explorer.BACKGROUND_BOOKMARK_COLOR_DARK);

        JLabel text = new JLabel("File format not supported");
        notSupported.add(text);
        add(notSupported, BorderLayout.CENTER);
    }
}
