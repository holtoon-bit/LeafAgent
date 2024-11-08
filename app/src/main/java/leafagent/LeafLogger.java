package leafagent;

import java.util.HashMap;

public class LeafLogger {
    private static HashMap<String, LeafInfo> allLeaf = new HashMap<>();

    public static void createNewLeaf(String methodName) {
        allLeaf.put(methodName, new LeafInfo(methodName));
    }

    public static void logStart(String methodName) {
        if (!allLeaf.containsKey(methodName)) {createNewLeaf(methodName);}
        LeafInfo info = allLeaf.get(methodName);
        info.setStartMillis(System.currentTimeMillis());
    }

    public static void logEnd(String methodName) {
        if (!allLeaf.containsKey(methodName)) {createNewLeaf(methodName);}
        LeafInfo info = allLeaf.get(methodName);
        info.setEndMillis(System.currentTimeMillis());
    }
}
