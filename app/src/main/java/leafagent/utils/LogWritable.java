package leafagent.utils;

import leafagent.info.BaseInfo;

public interface LogWritable {
    LogWritableRepository createRepository(String name);
    void writeLeaf(BaseInfo info);
}
