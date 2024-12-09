package leafagent.utils;

import com.google.gson.Gson;
import leafagent.info.BaseInfo;

import java.io.*;
import java.util.ArrayList;

public class JsonWritableDAOImpl implements LogWritableDAO {
    private File file;

    public JsonWritableDAOImpl(String path) {
        file = new File(path);
        Gson gson = new Gson();
        BaseInfo root = new BaseInfo.Build().setName("Root").build();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(gson.toJson(root));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BaseInfo create(BaseInfo info) {
        try {
            // EDIT: Нужно избавиться от постоянной записи и сделать Buffer с сохранением в памяти нескольких значений,
            // мб сам BufferReader подойдет.
            // Read the Json object and add a new child
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BaseInfo readInfo = gson.fromJson(reader.readLine(), BaseInfo.class);
            BaseInfo necessaryInfo = getLastInfo(readInfo);
            necessaryInfo.addChild(info);
            reader.close();

            // Write the Json in a log file
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(gson.toJson(readInfo));
            bufferedWriter.flush();
            bufferedWriter.close();

            new BufferedReader(new FileReader(file)).lines().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return info;
    }

    private BaseInfo getLastInfo(BaseInfo info) {
        for (BaseInfo child : info.getChildren()) {
            if (child.getEndMillis() == 0) {
                return getLastInfo(child);
            }
        }
        return info;
    }

    @Override
    public BaseInfo get(int id) {
        return null;
    }

    @Override
    public ArrayList<BaseInfo> getAll() {
        return null;
    }

    @Override
    public void update(BaseInfo info) {

    }

    @Override
    public void remove(BaseInfo info) {

    }

    @Override
    public void removeAll() {

    }
}
