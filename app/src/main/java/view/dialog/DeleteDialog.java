package view.dialog;

import view.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DeleteDialog extends JDialog implements ActionListener {
    Explorer explorer;

    public JButton apply;
    public JButton cancel;

    public DeleteDialog(Explorer explorer, String title) {
        super(explorer, title);
        this.explorer = explorer;

        apply = new JButton("Confirm");
        cancel = new JButton("Cancel");
        apply.addActionListener(this);
        cancel.addActionListener(this);
        setLayout(new BorderLayout());

        add(apply, BorderLayout.WEST);
        add(cancel, BorderLayout.EAST);

        setLocationRelativeTo(null);
    }
    public void loadDialog() {
        pack();
        setVisible(true);
    }

    private String getParentFolder(String str) {
        File f = new File(str);
        return f.getParent();
    }
    private String getLastFolder(String str) {
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i).contains(File.separator)) {
                index = i;
            }
        }
        return str.substring(index);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "Confirm":
                if (!(explorer.getCurrentFolder().equals(".") || explorer.getCurrentFolder().equals(".."))) {
                    explorer.rename(explorer.getUserHomeDir() + File.separator + ".poubelle" + getLastFolder(explorer.getCurrentFolder()), explorer.getCurrentFolder());
                }
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
