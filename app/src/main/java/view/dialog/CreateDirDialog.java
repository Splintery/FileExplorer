package view.dialog;

import view.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CreateDirDialog extends JDialog implements ActionListener {
    Explorer explorer;

    public JTextField newName;
    public JButton apply;
    public JButton cancel;

    public CreateDirDialog(Explorer explorer, String title) {
        super(explorer, title);
        this.explorer = explorer;

        newName = new JTextField("", 13);
        apply = new JButton("Create");
        cancel = new JButton("Cancel");
        apply.addActionListener(this);
        cancel.addActionListener(this);
        setLayout(new BorderLayout());

        add(newName, BorderLayout.CENTER);
        add(apply, BorderLayout.EAST);
        add(cancel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }
    public void loadDialog() {
        newName.setText("");
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
            case "Create":
                explorer.createDir(explorer.getCurrentFolder(), newName.getText());
                newName.setText("");
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
