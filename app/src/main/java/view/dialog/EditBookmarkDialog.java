package view.dialog;

import view.Explorer;
import view.bookmark.SelectableBookmarkLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class EditBookmarkDialog extends JDialog implements ActionListener {

    Explorer parent;

    List<String> bookMarks = new LinkedList<>();
    List<JToggleButton> deleteButtons = new LinkedList<>();

    JPanel contentPane;
    JPanel confirmationPane;

    JButton applyChanges;
    JButton cancelChanges;

    public EditBookmarkDialog(Explorer parent, String title) {
        super(parent, title);
        this.parent = parent;
        setLayout(new BorderLayout());
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(0, 2, 20, 5));
        add(contentPane, BorderLayout.CENTER);

        applyChanges = new JButton("Apply");
        cancelChanges = new JButton("Cancel");
        applyChanges.addActionListener(this);
        cancelChanges.addActionListener(this);

        confirmationPane = new JPanel();
        confirmationPane.setLayout(new GridBagLayout());
        confirmationPane.add(applyChanges);
        confirmationPane.add(cancelChanges);

        add(confirmationPane, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    public void loadDialog() {
        bookMarks.clear();
        deleteButtons.clear();
        contentPane.removeAll();
        for (SelectableBookmarkLabel bk : parent.getBookmarks()) {
            bookMarks.add(bk.getFilePath());
        }
        for (String key : bookMarks) {
            deleteButtons.add(new JToggleButton("Delete"));
        }

        for (int i = 0; i < bookMarks.size(); i++) {
            contentPane.add(new JLabel(bookMarks.get(i)));
            contentPane.add(deleteButtons.get(i));
        }

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "Apply":
                System.out.println("Changes applied");
                List<String> newBookmarks = new LinkedList<>();
                for (int i = 0; i < deleteButtons.size(); i++) {
                    if (!deleteButtons.get(i).isSelected()) {
                        newBookmarks.add(bookMarks.get(i));
                    }
                }
                parent.setBookmarks(newBookmarks);
                setVisible(false);
                break;
            case "Cancel":
                setVisible(false);
                break;
            default:
                break;
        }
    }
}
