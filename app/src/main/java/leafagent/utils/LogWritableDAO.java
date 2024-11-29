package leafagent.utils;

import leafagent.info.BaseInfo;

public interface LogWritableDAO {
    void create(BaseInfo info);
    BaseInfo get(int id);
    void update(BaseInfo info);
    void remove(BaseInfo info);
}
