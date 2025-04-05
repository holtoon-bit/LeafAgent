package leafagent.utils;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Setting the ADB connect.
 */
public class AdbLeafSetting {
    public final static int LOCAL_PORT = 8338;
    public final static int REMOTE_PORT = 8787;

    private static Thread currentThread;

    /**
     * Setting a redirect of the TCP connect between mobile device and device for development.
     *
     * @return {@code boolean} is correct redirect.
     */
    public static boolean forward() {
        return Objects.requireNonNull(runFunction("adb", "devices", "-l")).contains("product:")
                && runFunction("adb", "forward", "tcp:" + LOCAL_PORT, "tcp:" + REMOTE_PORT) != null;
    }

    private static String runFunction(String... command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            Scanner sc = new Scanner(process.getErrorStream());
            if (sc.hasNext()) {
                StringBuilder errorMes = new StringBuilder();
                while (sc.hasNext()) {
                    errorMes.append(sc.next());
                    errorMes.append(" ");
                }
                new Exception(errorMes.toString()).printStackTrace();
            }
            sc.close();
            sc = new Scanner(process.getInputStream());
            if (sc.hasNext()) {
                StringBuilder inputMes = new StringBuilder();
                while (sc.hasNext()) {
                    inputMes.append(sc.next());
                    inputMes.append(" ");
                }
                return inputMes.toString();
            }
            sc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Start a server for exchange data between devices.
     */
    public static void startSocketServer() {
        if (currentThread == null) {
            currentThread = new Thread(() -> {
                try {
                    AdbLeafServer serverSocket = new AdbLeafServer();
                    serverSocket.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            currentThread.start();
        }
    }
}
