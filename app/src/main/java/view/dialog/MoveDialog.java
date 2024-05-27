package view.dialog;

import view.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MoveDialog extends JDialog implements ActionListener {
    Explorer explorer;

    public JTextField newDestination;
    public JButton apply;
    public JButton cancel;

    public MoveDialog(Explorer explorer, String title) {
        super(explorer, title);
        this.explorer = explorer;

        newDestination = new JTextField("", 13);
        apply = new JButton("Apply");
        cancel = new JButton("Cancel");
        apply.addActionListener(this);
        cancel.addActionListener(this);
        setLayout(new BorderLayout());

        add(newDestination, BorderLayout.CENTER);
        add(apply, BorderLayout.EAST);
        add(cancel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }
    public void loadDialog() {
        newDestination.setText("");
        pack();
        setVisible(true);
    }

    private String getParentFolder(String str) {
        File f = new File(str);
        return f.getParent();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "Apply":
                System.out.println("Moving");
                explorer.rename(newDestination.getText(), explorer.getCurrentFolder());
                newDestination.setText("");
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
