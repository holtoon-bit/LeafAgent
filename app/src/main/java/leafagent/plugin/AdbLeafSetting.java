package leafagent.plugin;

import java.io.IOException;
import java.util.Scanner;

public class AdbLeafSetting {
    public AdbLeafSetting() {
        forward();
    }

    public void forward() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("adb", "forward", "tcp:2112", "tcp:1221");
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
