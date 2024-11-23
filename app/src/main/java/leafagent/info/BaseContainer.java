package leafagent.info;

import leafagent.utils.JsonWriter;
import leafagent.utils.LogWritable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class BaseContainer<T extends BaseContainer> {
    protected BaseInfo info;
    protected LogWritable writer;

    public BaseContainer(String name) {
        info = new BaseInfo(name);
        System.out.println(name);
        writer = new JsonWriter("logg.json");
    }

    public void startTime() {
        System.out.println(writer);
        try {
            System.out.println(new BufferedReader(new FileReader(writer.createFile("logg.json"))).readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        info.setStartMillis(System.currentTimeMillis());
    }

    public void endTime() {
        info.setEndMillis(System.currentTimeMillis());
    }
}
