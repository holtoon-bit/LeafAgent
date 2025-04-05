package leafagent.plugin;

import leafagent.info.BaseContainer;

import java.util.HashMap;

/**
 * Stores all {@link BaseContainer BaseContainer}'s objects,
 * then calling {@link BaseContainer BaseContainer}'s methods in code.
 */
public class CreatedContainers {
    private static final HashMap<String, BaseContainer> createdContainers = new HashMap<>();

    public static void addNew(BaseContainer container) {
        createdContainers.put(container.getInfo().getClassName()+"."+container.getInfo().getName(), container);
    }

    public static BaseContainer get(String name, String className) {
        return createdContainers.get(className+"."+name);
    }

    public static void remove(String name, String className) {
        createdContainers.remove(className+"."+name);
    }

    public static HashMap<String, BaseContainer> getCreatedContainers() {
        return createdContainers;
    }
}
