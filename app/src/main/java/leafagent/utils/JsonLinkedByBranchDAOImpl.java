package leafagent.utils;

import com.google.gson.Gson;
import leafagent.info.BaseInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class JsonLinkedByBranchDAOImpl implements LogWritableDAO {
    private File file;
    private Gson gson;

    private static LinkedList<BaseInfo> arrayChildren = new LinkedList<>();
    private BufferedWriter bufferedWriter;

    public JsonLinkedByBranchDAOImpl(String path) {
        gson = new Gson();
        file = new File(path);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void create(BaseInfo info) {
        for (int i = arrayChildren.size() - 1; i >= 0; i--) {
            if (info.getParentName() != null || info.getParentId() == -1) {
                if (arrayChildren.get(i).getName().equals(info.getParentName())) {
                    info.setParentId(arrayChildren.get(i).getId());
                    break;
                }
            } else if ((arrayChildren.get(i).getEndMillis() == 0)) {
                info.setParentId(arrayChildren.get(i).getId());
                break;
            }
        }

        if (((Integer) info.getId()).describeConstable().isEmpty()) {
            return;
        }

        info.setId(arrayChildren.size()+1);
        arrayChildren.add(info);

        try {
            System.out.println(gson.toJson(arrayChildren.toArray()));
            bufferedWriter.write(gson.toJson(arrayChildren.toArray()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public BaseInfo get(int id) {
        return arrayChildren.get(id);
    }

    @Override
    public LinkedList<BaseInfo> getAll() {
        return arrayChildren;
    }

    @Override
    public void update(BaseInfo newInfo) {
        arrayChildren.set(arrayChildren.indexOf(newInfo), newInfo);
        System.out.println(gson.toJson(arrayChildren.toArray()));
    }

    @Override
    public void remove(BaseInfo info) {

    }

    @Override
    public void removeAll() {
        arrayChildren.clear();
    }
}
