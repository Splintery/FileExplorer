package view.find;

import view.Explorer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class FinderSwingWorker extends SwingWorker<List<String>, String> {
    public Explorer explorer;
    public String regex;
    public String folderPath;

    public FinderSwingWorker(Explorer explorer, String folderPath, String regex) {
        this.explorer = explorer;
        this.folderPath = folderPath;
        this.regex = regex;
    }
    @Override
    protected List<String> doInBackground() throws Exception {
        List<String> res = new LinkedList<>();
        File f = new File(folderPath);
        String[] command = new String[] {"/bin/sh", "-c", "find -name \"" + regex + "\""};
        Process p = Runtime.getRuntime().exec(command, null, f);
        BufferedReader out = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = out.readLine()) != null) {
            res.add(folderPath + line.substring(1));
        }
        Scanner sErr = new Scanner(p.getErrorStream()).useDelimiter("\\A");
        while (sErr.hasNext()) {
            System.out.println(sErr.next());
        }
        System.out.println(p.waitFor());
        out.close();
        sErr.close();
        return res;
    }

    @Override
    protected void process(List chunks) {
        for (Object chunk : chunks) {
            String val = (String) chunk;
            System.out.println(val);
        }
    }
    @Override
    protected void done() {
        try {
            explorer.addFindView(get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
