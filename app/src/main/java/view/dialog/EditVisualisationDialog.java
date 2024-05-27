package view.dialog;

import view.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class EditVisualisationDialog extends JDialog implements ActionListener {

    Explorer explorer;

    JPanel contentPane;
    JTextField extensionInput;
    JComboBox<String> visual;

    JButton add;
    JButton cancel;

    public EditVisualisationDialog(Explorer explorer, String title) {
        super(explorer, title);
        this.explorer = explorer;
        JPanel contentPane = new JPanel();
        extensionInput = new JTextField("", 13);
        visual = new JComboBox<>(new String[]{"TEXT", "IMAGE", "GIF", "NONE"});

        contentPane.add(extensionInput);
        contentPane.add(visual);
        add = new JButton("Add");
        cancel = new JButton("Cancel");
        add.addActionListener(this);
        cancel.addActionListener(this);
        setLayout(new BorderLayout());


        add(contentPane, BorderLayout.CENTER);
        add(add, BorderLayout.EAST);
        add(cancel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    public void loadDialog() {
        extensionInput.setText("");
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "Add":
                explorer.addExtension(extensionInput.getText(), (String) visual.getSelectedItem());
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
