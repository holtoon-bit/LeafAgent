package leafagent.info;

import leafagent.utils.JsonWriter;
import leafagent.utils.LogWritable;

public class BaseContainer<T extends BaseContainer> {
    protected BaseInfo info;
    private LogWritable writer;

    public BaseContainer() {
    }

    public BaseContainer(String name) {
        super();
        info = new BaseInfo(name);
        writer = new JsonWriter("logg.json");
    }

    public LogWritable getWriter() {
        return writer;
    }

    public BaseInfo getInfo() {
        return info;
    }

    public void addChild(BaseContainer container) {
        info.addChild(container.getInfo());
    }

    public void startTime() {
        info.setStartMillis(System.currentTimeMillis());
    }

    public void endTime() {
        info.setEndMillis(System.currentTimeMillis());
    }
}
