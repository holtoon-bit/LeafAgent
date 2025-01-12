package leafagent.utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class LogWriter implements LogWritable {
    static private String projectPath = "";

    private final int HOST = 1221;
    private ServerSocket server;
    private Socket client;
    private PrintWriter writer;

    public LogWriter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(HOST);
                    client = server.accept();
                    writer = new PrintWriter(client.getOutputStream(), true);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }).start();
    }

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (writer != null) {
                    writer.write(structure);
                    writer.println();
                }
            }
        }).start();
    }
}
