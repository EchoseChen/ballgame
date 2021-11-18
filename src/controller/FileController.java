package controller;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileController extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;
        return f.getName().endsWith(".psc");
    }

    @Override
    public String getDescription() {
        return ".psc";
    }
}
