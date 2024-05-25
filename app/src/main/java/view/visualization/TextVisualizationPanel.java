package view.visualization;

import view.Explorer;

import javax.swing.*;
import java.awt.*;

public class TextVisualizationPanel extends VisualizationPanel {
    private JTextArea textArea = null;

    public TextVisualizationPanel(Explorer parent, String filePath, JTextArea textArea) {
        super(parent, filePath);
        this.textArea = textArea;
        textArea.setLineWrap(true);
        textArea.setEnabled(false);

        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Explorer.BACKGROUND_NAV_COLOR_DARK);
        textArea.setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TEXT_FONT_SIZE));
        JScrollPane scroller = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scroller, BorderLayout.CENTER);
    }
}
