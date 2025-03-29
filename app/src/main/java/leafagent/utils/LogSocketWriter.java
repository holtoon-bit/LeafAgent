package leafagent.utils;

public abstract class LogSocketWriter implements LogWritable {
    static private String projectPath = "";

    public static void setProjectPath(String path) {
        projectPath = path;
    }

    public static String getProjectPath() {
        return projectPath;
    }

    public static boolean isHaveProjectPath() {
        return !projectPath.isEmpty();
    }

    protected void sendLeafStructure(String structure) {
    }
}
