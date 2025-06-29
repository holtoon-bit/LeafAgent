package leafagent.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LeafWriter {
    private static final String LEAF_FILE_DIFFERENCE = "LFD";

    private static String path;
    private static BufferedWriter bufferedWriter;

    /**
     * Set a project path to store all the application files.
     * @param projectPath path to directory with all the application files.
     */
    public static void setProjectPath(String projectPath) {
        clearOldLeafFiles(projectPath);
        path = generatePath(projectPath);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create path to new Leaf file.
     * @param projectPath path to app storage.
     * @return path to actual Leaf file.
     */
    private static String generatePath(String projectPath) {
        String newPath = projectPath + File.separator + LEAF_FILE_DIFFERENCE + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        new File(newPath).mkdir();
        newPath +=  File.separator + new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime()) + ".json";
        try {
            new File(newPath).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newPath;
    }

    /**
     * Delete all old Leaf files in the application storage.
     * @param path path to application storage.
     */
    private static void clearOldLeafFiles(String path) {
        File[] leafDirs = new File(path).listFiles();
        if (leafDirs != null) {
            for (File leafDir : leafDirs) {
                if (leafDir.isDirectory()
                        && leafDir.getName().startsWith(LEAF_FILE_DIFFERENCE)
                        && !leafDir.getName().equals(
                                LEAF_FILE_DIFFERENCE + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()))) {
                    clearOldLeafFiles(leafDir.getPath());
                }
            }
        }
    }

    /**
     * Write the Leaf Structure in Leaf file.
     * @param jsonStructure Leaf Structure.
     */
    public static void writeLeaf(String jsonStructure) {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.write(jsonStructure + ",");
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Get the Leaf Files for all time.
     * @return Leaf Files.
     */
    public static File[] getLeafFiles() {
        return new File(path).getParentFile().listFiles();
    }
}
