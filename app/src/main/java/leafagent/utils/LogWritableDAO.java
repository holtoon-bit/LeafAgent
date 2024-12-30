package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.LinkedList;

public interface LogWritableDAO {
    void create(BaseInfo info);
    BaseInfo get(int id);
    LinkedList<BaseInfo> getAll();
    void update(BaseInfo info);
    void remove(BaseInfo info);
    void removeAll();
}
