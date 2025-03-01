package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.Collection;

public interface LogWritable {
    LogWritableRepository createRepository(String name);
    void writeLeaf(BaseInfo info);
    void updateLeaf(BaseInfo info);
    Collection<BaseInfo> getStruct();
    void clear();
    void save();
}
