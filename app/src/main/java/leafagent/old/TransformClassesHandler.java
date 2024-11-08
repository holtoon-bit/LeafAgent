package leafagent.old;

import java.io.File;
import java.util.LinkedList;

public class TransformClassesHandler {
    public LinkedList<File> getAllFiles(File dir) {
        LinkedList<File> files = new LinkedList<>();
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                files.addAll(getAllFiles(file));
            }
        } else {
            files.add(dir);
        }
        return files;
    }
}
