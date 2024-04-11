package view;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JPanel {
    public NavigationPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Navigation Panel");
        title.setBackground(Color.MAGENTA);
        add(title, BorderLayout.NORTH);

    }
}
