package leafagent.info;

import java.util.LinkedList;

public class BaseInfo {
    private final String name;

    private long startMillis;
    private long endMillis;

    private LinkedList<BaseInfo> children = new LinkedList<>();

    public BaseInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getStartMillis() {
        return startMillis;
    }

    protected void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    protected void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
    }

    @Override
    public String toString() {
        return name;
    }
}
