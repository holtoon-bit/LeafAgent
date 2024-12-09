package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.ArrayList;

public interface LogWritableDAO {
    BaseInfo create(BaseInfo info);
    BaseInfo get(int id);
    ArrayList<BaseInfo> getAll();
    void update(BaseInfo info);
    void remove(BaseInfo info);
    void removeAll();
}
