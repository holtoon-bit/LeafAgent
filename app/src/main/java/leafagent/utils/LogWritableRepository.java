package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.ArrayList;
import java.util.LinkedList;

public interface LogWritableRepository {
    void insert(BaseInfo info);
    BaseInfo get(int id);
    LinkedList<BaseInfo> getAll();
    void update(BaseInfo info);
    void remove(BaseInfo info);
    void removeAll();
}
