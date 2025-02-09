package leafagent;

import leafagent.info.LeafContainer;
import leafagent.plugin.CreatedContainers;

public class TestCode {
    public static void main(String[] args) {
        CreatedContainers.addNew(new LeafContainer("nameS", "nameP"));
        CreatedContainers.get("nameS").startTime();
        CreatedContainers.get("nameS").endTime();
    }
}