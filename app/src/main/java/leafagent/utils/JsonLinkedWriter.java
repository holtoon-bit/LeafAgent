package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.LinkedList;

public class JsonLinkedWriter extends LogWriter {
    private LogWritableRepository jsonRepository;

    public JsonLinkedWriter(String name) {
        jsonRepository = createRepository(name);
    }

    @Override
    public LogWritableRepository createRepository(String name) {
        return new JsonLinkedWritableRepositoryImpl(getProjectPath() + "/" + name + ".json");
    }

    @Override
    public void writeLeaf(BaseInfo info) {
        jsonRepository.insert(info);
    }

    @Override
    public void updateLeaf(BaseInfo info) {
        jsonRepository.update(info);
    }

    @Override
    public LinkedList<BaseInfo> getStruct() {
        return jsonRepository.getAll();
    }

    @Override
    public void clear() {
        jsonRepository.removeAll();
    }
}