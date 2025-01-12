package leafagent.utils;

import com.google.gson.Gson;
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
        sendLeafStructure(getStringStructure());
    }

    @Override
    public void updateLeaf(BaseInfo info) {
        jsonRepository.update(info);
        sendLeafStructure(getStringStructure());
    }

    @Override
    public LinkedList<BaseInfo> getStruct() {
        return jsonRepository.getAll();
    }

    private String getStringStructure() {
        return new Gson().toJson(getStruct());
    }

    @Override
    public void clear() {
        jsonRepository.removeAll();
    }
}