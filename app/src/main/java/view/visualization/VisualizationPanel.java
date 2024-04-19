package view.visualization;

import view.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

public class VisualizationPanel extends JPanel implements ActionListener {
    protected String filePath;
    protected Explorer parent;

    public VisualizationPanel(Explorer parent, String filePath) {
        super();
        this.parent = parent;
        this.filePath = filePath;
        setLayout(new BorderLayout());

        JPanel fileNamePanel = new JPanel(new GridBagLayout());
        fileNamePanel.setBackground(Explorer.BACKGROUND_TITLE_COLOR_DARK);
        fileNamePanel.setPreferredSize(new Dimension(150, 50));
        JPanel fileInfoPanel = new JPanel(new GridBagLayout());

        JLabel fileLabel = new JLabel(filePath);
        fileLabel.setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TITLE_FONT_SIZE));
        JButton closeVisual = new JButton("Close");
        closeVisual.setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TITLE_FONT_SIZE));
        closeVisual.addActionListener(this);

        fileNamePanel.add(fileLabel);
        fileNamePanel.add(closeVisual);
        add(fileNamePanel, BorderLayout.NORTH);

        JTextArea infoArea = getFileInfo(filePath);
        infoArea.setFont(new Font(Explorer.APP_FONT, Font.PLAIN, Explorer.TEXT_FONT_SIZE));
        infoArea.setBackground(Explorer.BACKGROUND_TITLE_COLOR_DARK);
        fileInfoPanel.add(infoArea);
        fileInfoPanel.setBackground(Explorer.BACKGROUND_TITLE_COLOR_DARK);
        add(fileInfoPanel, BorderLayout.SOUTH);
    }

    protected static JTextArea getFileInfo(String filePath) {
        File file = new File(filePath);
        JTextArea text = new JTextArea();
        try {
            BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            text.append("Size: " + Long.toString(attributes.size()) + "\n");
            text.append("Created: " + attributes.creationTime().toString() + "\n");
        } catch (IOException io) {
            io.printStackTrace();
        }
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        parent.removeVisualizationPane();
    }
}
