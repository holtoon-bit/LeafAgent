package leafagent.info;

import leafagent.plugin.CreatedContainers;
import leafagent.utils.JsonWriter;
import leafagent.utils.LogWriter;

public class BaseContainer {
    protected BaseInfo info;
    private LogWriter writer;

    public BaseContainer() {
        writer = new JsonWriter("logg");
    }

    public BaseContainer(String name, String className) {
        this();
        info = new BaseInfo.Build()
                .setName(name)
                .setClassName(className)
                .setThreadName(Thread.currentThread().getName())
                .build();
    }

    public BaseContainer(String name, String className, String description) {
        this();
        info = new BaseInfo.Build()
                .setName(name)
                .setClassName(className)
                .setDesc(description)
                .setThreadName(Thread.currentThread().getName())
                .build();
    }

    public LogWriter getWriter() {
        return writer;
    }

    public BaseInfo getInfo() {
        return info;
    }

    public void startTime() {
        info.setStartMillis(System.currentTimeMillis());
        getWriter().writeLeaf(info);
    }

    public void endTime() {
        info.setEndMillis(System.currentTimeMillis());
        getWriter().updateLeaf(info);
        CreatedContainers.remove(info.getName(), info.getClassName());
    }

    @Override
    public boolean equals(Object obj) {
        BaseContainer obj2 = (BaseContainer) obj;
        return info.equals(obj2.getInfo()) && info.getName().equals(obj2.getInfo().getName());
    }

    @Override
    public int hashCode() {
        return info.hashCode() + info.getName().hashCode();
    }
}