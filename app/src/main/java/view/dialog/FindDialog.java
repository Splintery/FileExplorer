package view.dialog;

import view.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindDialog extends JDialog implements ActionListener {
    Explorer explorer;
    JTextField regexInput;
    JButton find;
    JButton cancel;
    String folderPath;

    public FindDialog(Explorer explorer, String title) {
        super(explorer, title);
        this.explorer = explorer;
        regexInput = new JTextField("", 13);
        find = new JButton("Find");
        cancel = new JButton("Cancel");
        find.addActionListener(this);
        cancel.addActionListener(this);
        setLayout(new BorderLayout());

        add(regexInput, BorderLayout.CENTER);
        add(find, BorderLayout.EAST);
        add(cancel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    public void loadDialog(String folderPath) {
        this.folderPath = folderPath;
        regexInput.setText("");
        pack();
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "Find":
                explorer.generateFindView(folderPath, regexInput.getText());
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
