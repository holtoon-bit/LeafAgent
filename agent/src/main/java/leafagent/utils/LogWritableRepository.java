package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.ArrayList;
import java.util.LinkedList;

public interface LogWritableRepository {
    void insert(BaseInfo info);
    BaseInfo get(int id);
    LinkedList<BaseInfo> getAll();
    String getJsonArray();
    void update(BaseInfo info);
    void remove(BaseInfo info);
    void removeAll();
    void save();
}
