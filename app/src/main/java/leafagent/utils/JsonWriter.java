package leafagent.utils;

import leafagent.info.BaseContainer;

import java.io.*;

public class JsonWriter implements LogWritable {
    private static String projectPath;

    public JsonWriter() {}

    public JsonWriter(String name) {
//        try {
//            System.out.println(new BufferedReader(new FileReader(createFile(name))).readLine());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void setRootPath(String path) {
        projectPath = path;
        System.out.println("==> " + path);
    }

    @Override
    public File createFile(String name) {
        if (projectPath == null) {
            throw new NullPointerException("The projectPath is empty. Set the value using the setRootPath() method");
        }
        String path = projectPath + "/" + name;
        try {
            FileOutputStream file = new FileOutputStream(path);
            file.write("{}".getBytes());
            file.flush();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(path);
    }

    @Override
    public void startLeaf(BaseContainer leaf) {

    }

    @Override
    public void endLeaf() {

    }
}