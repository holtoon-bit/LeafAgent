package leafagent.utils;

import leafagent.info.BaseInfo;

public class JsonWritableRepositoryImpl implements LogWritableRepository {
    private LogWritableDAO dao;

    public JsonWritableRepositoryImpl(String path) {
        dao = new JsonWritableDAOImpl(path);
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
    public void update(BaseInfo info) {
        dao.update(info);
    }

    @Override
    public void remove(BaseInfo info) {
        dao.remove(info);
    }
}
