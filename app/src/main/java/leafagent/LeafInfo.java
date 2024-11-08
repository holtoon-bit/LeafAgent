package leafagent;

public class LeafInfo {
    private final String name;

    private long startMillis;
    private long endMillis;

    public LeafInfo(String methodName) {
        this.name = methodName;
    }

    public String getName() {
        return name;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
    }
}
