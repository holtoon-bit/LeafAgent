package leafagent.utils;

import leafagent.info.BaseInfo;

import java.util.Collection;

public class JsonLinkedByBranchWriter extends LogWriter {
    private LogWritableRepository jsonRepository;

    public JsonLinkedByBranchWriter(String name) {
        jsonRepository = createRepository(name);
    }

    @Override
    public LogWritableRepository createRepository(String name) {
        return new JsonLinkedByBranchRepositoryImpl(getProjectPath() + "/" + name + ".json");
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
    public Collection<BaseInfo> getStruct() {
        return jsonRepository.getAll();
    }

    @Override
    public void clear() {
        jsonRepository.removeAll();
    }
}
