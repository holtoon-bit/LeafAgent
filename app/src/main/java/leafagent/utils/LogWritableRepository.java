package leafagent.utils;

import leafagent.info.BaseInfo;

public interface LogWritableRepository {
    void insert(BaseInfo info);
    BaseInfo get(int id);
    void update(BaseInfo info);
    void remove(BaseInfo info);
}
