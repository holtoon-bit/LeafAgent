package leafagent.info;

import leafagent.utils.JsonWriter;
import leafagent.utils.LogWriter;

public class BaseContainer {
    protected BaseInfo info;
    private LogWriter writer;

    public BaseContainer() {
    }

    public BaseContainer(String name, String className) {
        info = new BaseInfo.Build()
                .setName(name)
                .setClassName(className)
                .setThreadName(Thread.currentThread().getName())
                .build();
        writer = new JsonWriter("logg");
    }

    public LogWriter getWriter() {
        return writer;
    }

    public BaseInfo getInfo() {
        return info;
    }

    public String getName() {
        return info.getName();
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
        return info.equals(obj2.getInfo()) && info.getName().equals(obj2.getName());
    }

    @Override
    public int hashCode() {
        return info.hashCode() + info.getName().hashCode();
    }
}