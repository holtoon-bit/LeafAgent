package leafagent;

import leafagent.utils.JsonWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestCode {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(2113);
            Socket socket = server.accept();
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.write("Hello world!\n\r");
            writer.println();
            writer.close();
            server.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}