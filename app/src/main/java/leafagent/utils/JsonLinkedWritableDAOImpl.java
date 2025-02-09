package leafagent.utils;

import com.google.gson.Gson;
import leafagent.info.BaseInfo;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;

public class JsonLinkedWritableDAOImpl implements LogWritableDAO {
    private File file;
    private Gson gson;

    private static LinkedList<BaseInfo> arrayChildren = new LinkedList<>();
    private BufferedWriter bufferedWriter;
    private HashSet<String> threadsSet = new HashSet<>();

    public JsonLinkedWritableDAOImpl(String path) {
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
        if (info.getParentId() != -1) {
            for (int i = arrayChildren.size() - 1; i >= 0; i--) {
                if (arrayChildren.get(i).getEndMillis() == 0 &&
                        (arrayChildren.get(i).getThreadName().equals(info.getThreadName())
                                || !threadsSet.contains(info.getThreadName()))) {
                    threadsSet.add(info.getThreadName());
                    info.setParentId(arrayChildren.get(i).getId());
                    break;
                }
            }
        }
        info.setId(arrayChildren.size());
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
