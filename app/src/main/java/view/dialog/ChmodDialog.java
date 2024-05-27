package view.dialog;

import view.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChmodDialog extends JDialog implements ActionListener {
    Explorer explorer;

    public JCheckBox writable;
    public JCheckBox readable;
    public JCheckBox executable;
    public JCheckBox ownerOnly;
    public JButton apply;
    public JButton cancel;

    public ChmodDialog(Explorer explorer, String title) {
        super(explorer, title);
        this.explorer = explorer;

        writable = new JCheckBox("Writable");
        readable = new JCheckBox("Readable");
        executable = new JCheckBox("Executable");
        ownerOnly = new JCheckBox("Owner only");

        JPanel contentPane = new JPanel(new GridLayout(0, 1, 10, 10));
        contentPane.add(writable);
        contentPane.add(readable);
        contentPane.add(executable);
        contentPane.add(ownerOnly);

        apply = new JButton("Apply");
        cancel = new JButton("Cancel");
        apply.addActionListener(this);
        cancel.addActionListener(this);
        setLayout(new BorderLayout());

        add(contentPane, BorderLayout.CENTER);
        add(apply, BorderLayout.EAST);
        add(cancel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }
    public void loadDialog() {
        File f = new File(explorer.getCurrentFolder());
        writable.setSelected(f.canWrite());
        readable.setSelected(f.canRead());
        executable.setSelected(f.canExecute());

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
                if (!explorer.chmod(writable.isSelected(), readable.isSelected(), executable.isSelected(), ownerOnly.isSelected())) {
                    System.out.println("Failed to modify access");
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
