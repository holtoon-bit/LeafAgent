package leafagent;

import java.util.LinkedList;

public class BaseContainer<T extends BaseContainer> extends BaseInfo {
    protected LinkedList<T> childrenContainers = new LinkedList<>();

    public BaseContainer(String name) {
        super(name);
    }

    public BaseContainer(BaseContainer parent, String name) {
        super(name);
        parent.addChild(this);
    }

    public void addChild(T child) {
        childrenContainers.add(child);
    }

    public void startTime() {
        setStartMillis(System.currentTimeMillis());
    }

    public void endTime() {
        setEndMillis(System.currentTimeMillis());
    }

    public LinkedList<T> getChildrenContainers() {
        return childrenContainers;
    }
}
