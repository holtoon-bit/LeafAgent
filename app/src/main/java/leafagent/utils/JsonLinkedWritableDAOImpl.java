package leafagent.utils;

import com.google.gson.Gson;
import leafagent.info.BaseInfo;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class JsonLinkedWritableDAOImpl implements LogWritableDAO {
    private final String DEFAULT_THREAD_NAME = "java/lang/Thread.<init>";
    private final String RUN_ON_UI_THREAD_NAME = "runOnUiThread";

    private File file;
    private Gson gson;

    private static LinkedList<BaseInfo> arrayChildren = new LinkedList<>();
    private BufferedWriter bufferedWriter;
    private static HashMap<String, Integer> threadsId = new HashMap<>();

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
        info.setId(arrayChildren.size()+1);

        if (info.getName().equals(DEFAULT_THREAD_NAME)) {
            threadsId.put(info.getName(), info.getId());
        }

        if (info.getParentId() != -1) {
            if (!threadsId.containsKey(info.getThreadName()) && !info.getThreadName().equals("main")) {
                info.setParentId(threadsId.get(DEFAULT_THREAD_NAME));
                threadsId.remove(DEFAULT_THREAD_NAME);
                threadsId.put(info.getThreadName(), info.getParentId());
            } else if (arrayChildren.getLast().getName().contains(RUN_ON_UI_THREAD_NAME) && info.getName().contains("lambda$")) {
                info.setParentId(arrayChildren.getLast().getId());
            } else {
                for (int i = arrayChildren.size() - 1; i >= 0; i--) {
                    if (arrayChildren.get(i).getThreadName().equals(info.getThreadName())) {
                        if (arrayChildren.get(i).getEndMillis() == 0 && !arrayChildren.get(i).getName().equals(DEFAULT_THREAD_NAME)) {
                            info.setParentId(arrayChildren.get(i).getId());
                            break;
                        } else if (threadsId.containsKey(info.getThreadName())) {
                            info.setParentId(threadsId.get(info.getThreadName()));
                        }
                    }
                }
            }
        }
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
