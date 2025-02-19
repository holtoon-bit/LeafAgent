package leafagent.info;

import leafagent.utils.JsonWriter;
import leafagent.utils.LogWriter;

public class BaseContainer<T extends BaseContainer> {
    private String name;

    protected BaseInfo info;
    private LogWriter writer;

    public BaseContainer() {
    }

    public BaseContainer(String name) {
        super();
        this.name = name;
        String threadName = Thread.currentThread().getName();
        info = new BaseInfo.Build().setName(name).setThreadName(threadName).build();
        writer = new JsonWriter("logg");
    }

    public LogWriter getWriter() {
        return writer;
    }

    public BaseInfo getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public void addChild(BaseContainer container) {
        info.addChild(container.getInfo());
    }

    public void startTime() {
        info.setStartMillis(System.currentTimeMillis());
        getWriter().writeLeaf(info);
    }

    public void endTime() {
        info.setEndMillis(System.currentTimeMillis());
        getWriter().updateLeaf(info);
    }

    @Override
    public boolean equals(Object obj) {
        BaseContainer obj2 = (BaseContainer) obj;
        return info.equals(obj2.getInfo()) && name.equals(obj2.getName());
    }

    @Override
    public int hashCode() {
        return info.hashCode() + name.hashCode();
    }
}