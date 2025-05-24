package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.Collection;

/**
 * Interface for the class that creates the Leaf Log.
 */
public interface LogWritable {
    /**
     * Create new object implementing {@link LogWritableRepository}.
     */
    LogWritableRepository createRepository(String name);
    /**
     * Write new {@link BaseInfo} info.
     * @param info - new {@link BaseInfo} info.
     */
    void writeLeaf(BaseInfo info);
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
     * Delete all {@link BaseInfo} from the Leaf structure.
     */
    void clear();
    /**
     * Save the Leaf structure.
     */
    void save();
}
