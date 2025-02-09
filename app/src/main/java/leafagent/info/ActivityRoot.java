package leafagent.info;

import java.util.LinkedList;

public class ActivityRoot {
    private static final LinkedList<TrunkContainer> trunks = new LinkedList<>();

    public static TrunkContainer createChild(String activityName) {
        TrunkContainer trunk = new TrunkContainer(activityName);
        return trunk;
    }

    public static LinkedList<TrunkContainer> getTrunks() {
        return trunks;
    }
}
