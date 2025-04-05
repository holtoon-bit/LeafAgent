package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.Collection;

/**
 * Interface for the class that creates the Leaf Log.
 */
public interface LogWritable {
    LogWritableRepository createRepository(String name);
    void writeLeaf(BaseInfo info);
    void updateLeaf(BaseInfo info);
    Collection<BaseInfo> getStruct();
    String getStringStruct();
    void clear();
    void save();
}
