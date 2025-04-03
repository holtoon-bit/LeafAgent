package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.Collection;

public class JsonWriter extends LogWriter {
    private final LogWritableRepository jsonRepository;

    public JsonWriter(String name) {
        jsonRepository = createRepository(name);
    }

    @Override
    public LogWritableRepository createRepository(String name) {
        String path = "";
        if (!name.isEmpty()) {
            path = getProjectPath() + "/" + name;
        }
        return new JsonWritableRepositoryImpl(path);
    }

    @Override
    public void writeLeaf(BaseInfo info) {
        jsonRepository.insert(info);
    }

    @Override
    public void updateLeaf(BaseInfo info) {}

    @Override
    public Collection<BaseInfo> getStruct() {
        return jsonRepository.getAll();
    }

    @Override
    public String getStringStruct() {
        return jsonRepository.getJsonArray();
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