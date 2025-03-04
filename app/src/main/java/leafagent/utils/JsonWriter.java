package leafagent.utils;

import leafagent.info.*;

import java.util.ArrayList;
import java.util.Collection;

public class JsonWriter extends LogWriter {
    private final LogWritableRepository jsonRepository;

    public JsonWriter(String name) {
        jsonRepository = createRepository(name);
    }

    @Override
    public LogWritableRepository createRepository(String name) {
        return new JsonWritableRepositoryImpl(getProjectPath() + "/" + name);
    }

    @Override
    public void writeLeaf(BaseInfo info) {
        jsonRepository.insert(info);
    }

    @Override
    public void updateLeaf(BaseInfo info) {

    }

    @Override
    public Collection<BaseInfo> getStruct() {
        return jsonRepository.getAll();
    }

    @Override
    public void clear() {
        jsonRepository.removeAll();
    }

    @Override
    public void save() {
        jsonRepository.save();
    }
}