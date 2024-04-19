import view.Explorer;

import javax.swing.*;

public class Main implements Runnable {
    public static void main(String[] args) {
        try {
		    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch(Exception e) {
		    e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        Explorer explorer = new Explorer();
    }
}