package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.LinkedList;

/**
 * Repository class to create the Leaf Log using the JSON format.
 */
public class JsonWritableRepositoryImpl implements LogWritableRepository {
    private LogWritableDAO dao;

    public JsonWritableRepositoryImpl(String path) {
        dao = new JsonLinkedWritableDAOImpl(path);
    }

    /**
     * Add {@link BaseInfo BaseInfo } about called method.
     * @param info {@link BaseInfo BaseInfo}
     */
    @Override
    public void insert(BaseInfo info) {
        dao.create(info);
    }

    /**
     * Get {@link BaseInfo BaseInfo} by {@code id} from the Leaf Log.
     * @param id {@link Integer int}
     * @return {@link BaseInfo BaseInfo}
     */
    @Override
    public BaseInfo get(int id) {
        return dao.get(id);
    }

    /**
     * Get {@link LinkedList LinkedList} with all {@link BaseInfo BaseInfo} from the Leaf Log.
     */
    @Override
    public LinkedList<BaseInfo> getAll() {
        return dao.getAll();
    }

    /**
     * Get {@code JSON} with all {@link BaseInfo BaseInfo} from the Leaf Log.
     * @return {@link String String} using the JSON format.
     */
    @Override
    public String getJsonArray() {
        return dao.getJsonArray();
    }

    /**
     * Update value {@link BaseInfo BaseInfo} by {@code id} from {@code newInfo}
     * @param info value {@link BaseInfo BaseInfo} with updated params, except {@code id}.
     */
    @Override
    public void update(BaseInfo info) {
        dao.update(info);
    }

    /**
     * Remove {@link BaseInfo BaseInfo}.
     */
    @Override
    public void remove(BaseInfo info) {
        dao.remove(info);
    }

    /**
     * Clear the Leaf log.
     */
    @Override
    public void removeAll() {dao.removeAll();}

    /**
     * Save the Leaf Log to local storage.
     */
    @Override
    public void save() {
        dao.save();
    }
}
