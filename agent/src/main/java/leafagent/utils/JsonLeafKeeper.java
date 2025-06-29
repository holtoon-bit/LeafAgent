package leafagent.utils;

import leafagent.info.BaseInfo;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Class to create the Leaf Log using the JSON format.
 */
public class JsonLeafKeeper implements LeafKeepable {
    private final LeafKeepableRepository jsonRepository;

    public JsonLeafKeeper() {
        jsonRepository = createRepository();
    }

    /**
     * Create new object implementing {@link LeafKeepableRepository}.
     */
    @Override
    public LeafKeepableRepository createRepository() {
        return new JsonLeafKeepableRepositoryImpl();
    }

    /**
     * Add {@link BaseInfo BaseInfo } about called method.
     * @param info {@link BaseInfo BaseInfo}
     */
    @Override
    public void insertLeaf(BaseInfo info) {
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
     * Get {@code JSON} with all {@link BaseInfo BaseInfo} for all the time from the Leaf Log.
     * @return {@link String String} using the JSON format.
     */
    @Override
    public String getStringStruct() {
        return jsonRepository.getJsonArray();
    }

    /**
     * Get the Leaf structure for any {@link BaseInfo}.
     * @param info {@link BaseInfo}
     * @return {@link String}
     */
    @Override
    public String getJsonFor(BaseInfo info) {
        return jsonRepository.getJsonFor(info);
    }

    /**
     * Clear the Leaf log.
     */
    @Override
    public void clear() {
        jsonRepository.removeAll();
    }
}