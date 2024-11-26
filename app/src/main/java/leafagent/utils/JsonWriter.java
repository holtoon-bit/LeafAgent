package leafagent.utils;

import com.google.gson.Gson;
import leafagent.info.*;

import java.io.*;

public class JsonWriter implements LogWritable {
    private static String projectPath;

    private File file;

    public JsonWriter() {}

    public JsonWriter(String name) {
        file = createFile(name);
    }

    public static void setProjectPath(String path) {
        projectPath = path;
    }

    public static String getProjectPath() {
        return projectPath;
    }

    @Override
    public File createFile(String name) {
        if (projectPath == null) {
            throw new NullPointerException("The projectPath is empty. Set the value using the setRootPath() method");
        }
        File file = new File(projectPath + "/" + name);
        Gson gson = new Gson();
        BaseInfo root = new BaseInfo("Root");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(gson.toJson(root));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    @Override
    public void startLeaf(BaseInfo info) {
        try {

            // Create the Json object
//            Gson gson = new Gson();
//            BaseInfo parent = new BaseInfo("LeadAgentApplicationApp");
//            BaseInfo info1_0 = new BaseInfo("SellersActivity");
//            parent.addChild(info1_0);
//            BaseInfo info2_0 = new BaseInfo("addSellers0");
//            BaseInfo info2_1 = new BaseInfo("addSellers1");
//            info2_0.setEndMillis(124);
//            info1_0.addChild(info2_0);
//            info1_0.addChild(info2_1);
//            String json = gson.toJson(parent);
//
//            // Write the Json in a log file
//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
//            bufferedWriter.write(json);
//            bufferedWriter.flush();
//            bufferedWriter.close();

            // Read the Json object and add a new child
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BaseInfo readInfo = gson.fromJson(reader.readLine(), BaseInfo.class);
            BaseInfo necessaryInfo = getLastInfo(readInfo);
            necessaryInfo.addChild(info);
            reader.close();

            // Write the Json in a log file
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(gson.toJson(readInfo));
            bufferedWriter.flush();
            bufferedWriter.close();

            new BufferedReader(new FileReader(file)).lines().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BaseInfo getLastInfo(BaseInfo info) {
        for (BaseInfo child : info.getChildren()) {
            if (child.getEndMillis() == 0) {
                return getLastInfo(child);
            }
        }
        return info;
    }

    @Override
    public void endLeaf() {

    }
}