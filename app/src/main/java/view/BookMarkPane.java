package view;

import javax.sql.rowset.BaseRowSet;
import javax.swing.*;
import java.awt.*;

public class BookMarkPane extends JPanel {

    JPanel titleContainer;
    JLabel titleLabel;

    JPanel contentContainer;


    public BookMarkPane() {
        setLayout(new BorderLayout());
        titleContainer = new JPanel();
        titleContainer.setMaximumSize(new Dimension(100, 50));
        titleContainer.setBackground(Color.RED);
        titleLabel = new JLabel("BookMarks");

        titleContainer.add(titleLabel);

        contentContainer = new JPanel();
        contentContainer.setBackground(Color.GREEN);
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS));

        for (int i = 0; i < 50; i++) {
            JLabel label = new JLabel("bookmark" + Integer.toString(i));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentContainer.add(label, BoxLayout.X_AXIS);
        }

        add(titleContainer, BorderLayout.NORTH);
        add(new JScrollPane(contentContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
    }
}
