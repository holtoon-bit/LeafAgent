package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.ArrayList;

public class SQLiteWriter extends LogWriter {
    private LogWritableRepository repository;

    public SQLiteWriter() {}
    public SQLiteWriter(String name) {
        repository = createRepository(name);
    }

    @Override
    public LogWritableRepository createRepository(String name) {
        return new SQLiteWritableRepositoryImpl(getProjectPath() + "/" + name);
    }

    @Override
    public void writeLeaf(BaseInfo info) {
        repository.insert(info);
    }

    @Override
    public void updateLeaf(BaseInfo info) {

    }

    @Override
    public ArrayList<BaseInfo> getStruct() {
        return new ArrayList<>();
    }

    @Override
    public void clear() {
        repository.removeAll();
    }
}
