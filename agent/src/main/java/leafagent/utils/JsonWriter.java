package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Class to create the Leaf Log using the JSON format.
 */
public class JsonWriter extends LogWriter {
    private final LogWritableRepository jsonRepository;

    public JsonWriter(String name) {
        jsonRepository = createRepository(name);
    }

    @Override
    public LogWritableRepository createRepository(String name) {
        String path = "";
        if (!name.isEmpty()) {
            path = getProjectPath() + "/" + name;
        }
        return new JsonWritableRepositoryImpl(path);
    }

    /**
     * Add {@link BaseInfo BaseInfo } about called method.
     * @param info {@link BaseInfo BaseInfo}
     */
    @Override
    public void writeLeaf(BaseInfo info) {
        jsonRepository.insert(info);
    }

    @Override
    public void updateLeaf(BaseInfo info) {}

    /**
     * Get {@link LinkedList LinkedList} with all {@link BaseInfo BaseInfo} from the Leaf Log.
     */
    @Override
    public Collection<BaseInfo> getStruct() {
        return jsonRepository.getAll();
    }

    /**
     * Get {@code JSON} with all {@link BaseInfo BaseInfo} from the Leaf Log.
     * @return {@link String String} using the JSON format.
     */
    @Override
    public String getStringStruct() {
        return jsonRepository.getJsonArray();
    }

    /**
     * Clear the Leaf log.
     */
    @Override
    public void clear() {
        jsonRepository.removeAll();
    }

    /**
     * Save the Leaf Log to local storage.
     */
    @Override
    public void save() {
        jsonRepository.save();
    }
}