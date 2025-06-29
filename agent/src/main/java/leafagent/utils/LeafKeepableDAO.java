package leafagent.utils;

import leafagent.info.BaseInfo;
import java.util.LinkedList;

/**
 * Interface for the DAO class that creates the Leaf Log.
 */
public interface LeafKeepableDAO {
    void create(BaseInfo info);
    BaseInfo get(int id);
    LinkedList<BaseInfo> getAll();
    String getJsonArray();
    void update(BaseInfo info);
    void remove(BaseInfo info);
    void removeAll();
    String getJsonFor(BaseInfo info);
}
