package view.visualization;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import view.Explorer;

public class VisualizationPanelBuilder {

    Explorer parent;
    private String filePath = null;
    private JTextArea textArea = null;
    private ImageIcon imageIcon = null;

    public VisualizationPanelBuilder(Explorer parent) {
        this.parent = parent;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setTextArea(JTextArea textArea) {
        if (imageIcon == null) {
            this.textArea = textArea;
        }
    }
    public void setImageIcon(ImageIcon imageIcon) {
        if (textArea == null) {
            this.imageIcon = imageIcon;
        }
    }
    public JTextArea readFile(String filePath) {
        return new JTextArea(parent.parser.getFileContent(filePath));
    }

    public void resetFields() {
        filePath = null;
        textArea = null;
        imageIcon = null;
    }

    public VisualizationPanel build() {
        try {
            if (filePath != null) {
                if (textArea != null) {
                    return new TextVisualizationPanel(parent, filePath, textArea);
                } else if (imageIcon != null) {
                    return new ImageVisualizationPanel(parent, filePath, imageIcon);
                } else {
                    return new UnsupportedVisualizationPanel(parent, filePath);
                }
            } else {
                return null;
            }
        } finally {
            resetFields();
        }
    }
}
