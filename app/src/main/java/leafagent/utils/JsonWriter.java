package leafagent.utils;

import leafagent.info.*;

public class JsonWriter implements LogWritable {
    private static String projectPath;

    private LogWritableRepository jsonWritable;

    public JsonWriter(String name) {
        jsonWritable = createRepository(name);
    }

    public static void setProjectPath(String path) {
        projectPath = path;
    }

    public static String getProjectPath() {
        return projectPath;
    }

    @Override
    public LogWritableRepository createRepository(String name) {
        if (projectPath == null) {
            throw new NullPointerException("The projectPath is empty. Set the value using the setRootPath() method");
        }
        return new JsonWritableRepositoryImpl(projectPath + "/" + name);
    }

    @Override
    public void writeLeaf(BaseInfo info) {
        jsonWritable.insert(info);
    }
}