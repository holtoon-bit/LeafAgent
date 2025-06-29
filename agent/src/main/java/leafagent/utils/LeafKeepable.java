package leafagent.utils;

import leafagent.info.BaseInfo;
import java.util.Collection;

/**
 * Interface for the class that creates the Leaf Log.
 */
public interface LeafKeepable {
    /**
     * Create new object implementing {@link LeafKeepableRepository}.
     */
    LeafKeepableRepository createRepository();
    /**
     * Insert new {@link BaseInfo} info.
     * @param info - new {@link BaseInfo} info.
     */
    void insertLeaf(BaseInfo info);
    /**
     * Update Leaf in the Leaf Log.
     * @param info updated Leaf info.
     */
    void updateLeaf(BaseInfo info);
    /**
     * Get the Leaf structure as {@link Collection<BaseInfo>}.
     * @return {@link Collection<BaseInfo>}
     */
    Collection<BaseInfo> getStruct();
    /**
     * Get the Leaf structure as {@link String}.
     * @return {@link String}
     */
    String getStringStruct();
    /**
     * Get the Leaf structure for any {@link BaseInfo}.
     * @param info {@link BaseInfo}
     * @return {@link String}
     */
    String getJsonFor(BaseInfo info);
    /**
     * Delete all {@link BaseInfo} from the Leaf structure.
     */
    void clear();
}
