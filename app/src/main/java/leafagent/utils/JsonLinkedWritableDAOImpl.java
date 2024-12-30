package leafagent.utils;

import com.google.gson.Gson;
import leafagent.info.BaseInfo;

import java.io.*;
import java.util.LinkedList;

public class JsonLinkedWritableDAOImpl implements LogWritableDAO {
    private File file;
    private Gson gson;

    private static LinkedList<BaseInfo> arrayChildren = new LinkedList<>();

    public JsonLinkedWritableDAOImpl(String path) {
        gson = new Gson();
        file = new File(path);
    }

    @Override
    public void create(BaseInfo info) {
        for (int i = arrayChildren.size() - 1; i >= 0; i--) {
            if ((arrayChildren.get(i).getEndMillis() == 0)) {
                info.setParentId(arrayChildren.get(i).getId());
                break;
            }
        }
        info.setId(arrayChildren.size());
        arrayChildren.add(info);

        try {
            System.out.println(gson.toJson(arrayChildren));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(gson.toJson(arrayChildren));
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        System.out.println(gson.toJson(arrayChildren));
    }

    @Override
    public void remove(BaseInfo info) {

    }

    @Override
    public void removeAll() {
        arrayChildren.clear();
    }
}
