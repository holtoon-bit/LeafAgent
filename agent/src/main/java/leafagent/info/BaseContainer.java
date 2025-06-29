package leafagent.info;

import leafagent.plugin.CreatedContainers;
import leafagent.utils.JsonLeafKeeper;
import leafagent.utils.LeafKeepable;
import leafagent.utils.LeafWriter;

/**
 * Handler of methods and objects for filling {@link BaseInfo BaseInfo}.
 */
public class BaseContainer {
    protected BaseInfo info;
    private LeafKeepable keeper;

    public BaseContainer() {
        keeper = new JsonLeafKeeper();
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

    public LeafKeepable getKeeper() {
        return keeper;
    }

    public BaseInfo getInfo() {
        return info;
    }

     /**
     * It is called when the method/object starts working.
     * <br><br>
     * Set startMillis in {@link BaseInfo BaseInfo} and write information in object extended by {@link LeafKeepable}.
     */
    public void startTime() {
        info.setStartMillis(System.currentTimeMillis());
        keeper.insertLeaf(info);
    }

    /**
     * It is called when the method/object stops working.
     * <br><br>
     * Set endMillis in {@link BaseInfo BaseInfo} and update information in object extended by {@link LeafKeepable}.
     */
    public void endTime() {
        info.setEndMillis(System.currentTimeMillis());
        keeper.updateLeaf(info);
        CreatedContainers.remove(info.getName(), info.getClassName());
        LeafWriter.writeLeaf(keeper.getJsonFor(info));
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