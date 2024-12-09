package leafagent.utils;

import com.google.gson.Gson;
import leafagent.info.BaseInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JsonLinkedWritableDAOImpl implements LogWritableDAO {
    private File file;
    private Gson gson;

    private static BlockingQueue<BaseInfo> queue = new LinkedBlockingQueue<>();
    private static ArrayList<BaseInfo> arrayChildren = new ArrayList<>();

    public JsonLinkedWritableDAOImpl(String path) {
        gson = new Gson();
        file = new File(path);
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//            writer.write("[]");
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public BaseInfo create(BaseInfo info) {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//            TypeToken<ArrayList<BaseInfo>> collectionType = new TypeToken<>(){};
//            ArrayList<BaseInfo> arrayChildren = gson.fromJson(bufferedReader.readLine(), collectionType);

        for (int i = arrayChildren.size() - 1; i >= 0; i--) {
            if ((arrayChildren.get(i).getEndMillis() == 0)) {
                info.setParentId(arrayChildren.get(i).getId());
                break;
            }
        }
        info.setId(arrayChildren.size());
        arrayChildren.add(info);
//            arrayChildren.add(info);

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(gson.toJson(arrayChildren));
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//            bufferedReader.close();
        return info;
    }

    @Override
    public BaseInfo get(int id) {
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//            TypeToken<ArrayList<BaseInfo>> collectionType = new TypeToken<>(){};
//            ArrayList<BaseInfo> arrayChildren = gson.fromJson(bufferedReader.readLine(), collectionType);
//            bufferedReader.close();
            return arrayChildren.get(id);
//        } catch (IOException e) {
//            System.out.println(e);
//            return null;
//        }
    }

    @Override
    public ArrayList<BaseInfo> getAll() {
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//            TypeToken<ArrayList<BaseInfo>> collectionType = new TypeToken<>(){};
//            String line = bufferedReader.readLine();
//            bufferedReader.close();
//            return gson.fromJson(line, collectionType);
//        } catch (IOException e) {
//            System.out.println(e);
//            return new ArrayList<>();
//        }
        return arrayChildren;
    }

    @Override
    public void update(BaseInfo info) {

    }

    @Override
    public void remove(BaseInfo info) {

    }

    @Override
    public void removeAll() {
        arrayChildren.clear();
    }
}
