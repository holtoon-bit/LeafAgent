package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.LinkedList;

public class JsonLinkedWritableRepositoryImpl implements LogWritableRepository {
    private LogWritableDAO dao;

    public JsonLinkedWritableRepositoryImpl(String path) {
        dao = new JsonLinkedWritableDAOImpl(path);
    }

    @Override
    public void insert(BaseInfo info) {
        dao.create(info);
    }

    @Override
    public BaseInfo get(int id) {
        return dao.get(id);
    }

    @Override
    public LinkedList<BaseInfo> getAll() {
        return dao.getAll();
    }

    @Override
    public void update(BaseInfo info) {
        dao.update(info);
    }

    @Override
    public void remove(BaseInfo info) {
        dao.remove(info);
    }

    @Override
    public void removeAll() {
        dao.removeAll();
    }
}
