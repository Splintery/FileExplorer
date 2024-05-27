package view.find;

import view.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class FindResultPanel extends JPanel implements ActionListener {
    Explorer explorer;
    List<SelectableFindLabel> labels;
    JPanel contentPane;
    JButton close;

    public FindResultPanel(Explorer explorer, List<String> results) {
        setLayout(new BorderLayout());
        setBackground(Explorer.BACKGROUND_NAV_COLOR_DARK);
        this.explorer = explorer;
        close = new JButton("Close");
        close.addActionListener(this);
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBackground(Explorer.BACKGROUND_BOOKMARK_COLOR_DARK);
        this.labels = new LinkedList<>();
        results.sort(null);
        for (String s : results) {
            SelectableFindLabel sfl = new SelectableFindLabel(s, explorer, this);
            labels.add(sfl);
            contentPane.add(sfl);
        }
        JScrollPane tmp = new JScrollPane(contentPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tmp.setPreferredSize(new Dimension(300, 300));
        add(tmp, BorderLayout.CENTER);
        add(close, BorderLayout.SOUTH);
    }

    public List<SelectableFindLabel> getLabels() {return labels;};

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "Close":
                explorer.removeFindView();
                break;
            default:
                break;
        }
    }
}
