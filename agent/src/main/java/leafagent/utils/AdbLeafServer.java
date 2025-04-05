package leafagent.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket Server to exchange data between a connected device.<br>
 * Using after {@link AdbLeafSetting#forward() AdbLeafSetting.forward()} to connect to the developer's device.
 */
public class AdbLeafServer extends ServerSocket {
    private final LogWritable leafWriter;

    public AdbLeafServer() throws IOException {
        super(AdbLeafSetting.REMOTE_PORT);
        leafWriter = new JsonWriter("");
    }

    public void start() throws IOException {
        while (!isClosed()) {
            Socket client = accept();
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
            writer.write(leafWriter.getStringStruct());
            writer.println();
            writer.close();
            client.close();
        }
    }
}
