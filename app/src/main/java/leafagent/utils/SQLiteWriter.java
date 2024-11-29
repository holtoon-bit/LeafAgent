package leafagent.utils;

import leafagent.info.BaseInfo;

public class SQLiteWriter implements LogWritable {
    static private String projectPath;

    public static void setProjectPath(String path) {
        projectPath = path;
    }

    @Override
    public LogWritableRepository createRepository(String name) {
        return null;
    }

    @Override
    public void writeLeaf(BaseInfo info) {

    }
}
