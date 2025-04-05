package leafagent.info;

import leafagent.plugin.CreatedContainers;
import leafagent.utils.JsonWriter;
import leafagent.utils.LogWritable;

/**
 * Handler of methods and objects for filling {@link BaseInfo BaseInfo}.
 */
public class BaseContainer {
    protected BaseInfo info;
    private LogWritable writer;

    public BaseContainer() {
        writer = new JsonWriter("logg.json");
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

    public LogWritable getWriter() {
        return writer;
    }

    public BaseInfo getInfo() {
        return info;
    }

     /**
     * It is called when the method/object starts working.
     * <br><br>
     * Set startMillis in {@link BaseInfo BaseInfo} and write information in object extended by {@link LogWritable LogWritable}.
     */
    public void startTime() {
        info.setStartMillis(System.currentTimeMillis());
        writer.writeLeaf(info);
    }

    /**
     * It is called when the method/object stops working.
     * <br><br>
     * Set endMillis in {@link BaseInfo BaseInfo} and update information in object extended by {@link LogWritable LogWritable}.
     */
    public void endTime() {
        info.setEndMillis(System.currentTimeMillis());
        writer.updateLeaf(info);
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