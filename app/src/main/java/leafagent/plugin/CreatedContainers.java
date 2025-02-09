package leafagent.plugin;

import leafagent.info.BaseContainer;

import java.util.HashMap;

public class CreatedContainers {
    private static final HashMap<String, BaseContainer> createdContainers = new HashMap<>();

    public static void addNew(BaseContainer container) {
        createdContainers.put(container.getName(), container);
    }

    public static BaseContainer get(String byName) {
        return createdContainers.get(byName);
    }
}
