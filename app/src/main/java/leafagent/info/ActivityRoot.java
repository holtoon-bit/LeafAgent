package leafagent.info;

import java.util.LinkedList;

public class ActivityRoot {
    private static final LinkedList<TrunkContainer> trunks = new LinkedList<>();

    public static BranchContainer createChild(String activityName) {
        BranchContainer trunk = new BranchContainer(activityName);
        return trunk;
    }

    public static LinkedList<TrunkContainer> getTrunks() {
        return trunks;
    }
}
