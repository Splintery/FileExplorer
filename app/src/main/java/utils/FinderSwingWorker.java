package utils;

import view.Explorer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class FinderSwingWorker extends SwingWorker<List<String>, String> {
    public Explorer explorer;
    public String regex;

    public FinderSwingWorker(Explorer explorer, String regex) {
        this.explorer = explorer;
        this.regex = regex;
    }
    @Override
    protected List<String> doInBackground() throws Exception {
        List<String> res = new LinkedList<>();
        File f = new File(explorer.getCurrentFolder());
        String[] command = new String[] {"/bin/sh", "-c", "find -name \"" + regex + "\""};
//        Process p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "find -name \"*.java\""}, null, f);
        Process p = Runtime.getRuntime().exec(command, null, f);
        Scanner sOut = new Scanner(p.getInputStream()).useDelimiter("\\A");
        while (sOut.hasNext()) {
            System.out.println(sOut.next());
        }
        Scanner sErr = new Scanner(p.getErrorStream()).useDelimiter("\\A");
        while (sErr.hasNext()) {
            System.out.println(sErr.next());
        }
        System.out.println(p.waitFor());
        sOut.close();
        sErr.close();
        return res;
    }

    @Override
    protected void process(List chunks) {
        for (int i = 0; i < chunks.size(); i++) {
            String val = (String) chunks.get(i);
            System.out.println(val);
        }
    }
    @Override
    protected void done() {
        try {
            for (String s : get()) {
                System.out.println(s);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
