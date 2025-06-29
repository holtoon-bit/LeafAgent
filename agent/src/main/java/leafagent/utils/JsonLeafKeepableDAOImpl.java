package leafagent.utils;

import com.google.gson.Gson;
import leafagent.info.BaseInfo;
import leafagent.plugin.LeafVisitor;
import org.objectweb.asm.Type;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * DAO class to create the Leaf Log using the JSON format.
 */
public class JsonLeafKeepableDAOImpl implements LeafKeepableDAO {
    public static final String KEY_THREAD_NAME = "newThread";

    private final Gson gson;

    private static final LinkedList<BaseInfo> arrayChildren = new LinkedList<>();
    private static final HashMap<String, Integer> threadsId = new HashMap<>();

    public JsonLeafKeepableDAOImpl() {
        this.gson = new Gson();
    }

    /**
     * Add {@link BaseInfo BaseInfo } about called method.
     * @param info {@link BaseInfo BaseInfo}
     */
    @Override
    public void create(BaseInfo info) {
        info.setId(arrayChildren.size()+1);

        if (info.getClassName().equals(Type.getInternalName(Thread.class))
                && info.getName().equals(LeafVisitor.COST_INIT_NAME)) {
            threadsId.put(KEY_THREAD_NAME, info.getId());
        }

        if (info.getParentId() != -1) {
            if (!threadsId.containsKey(info.getThreadName()) && !info.getThreadName().equals("main")) {
                info.setParentId(threadsId.get(KEY_THREAD_NAME));
                threadsId.remove(KEY_THREAD_NAME);
                threadsId.put(info.getThreadName(), info.getParentId());
            } else if (info.getName().contains("lambda$")) {
                for (int i = arrayChildren.size() - 1; i >= 0; i--) {
                    if ((arrayChildren.get(i).getClassName().equals(Type.getInternalName(Thread.class))
                                && arrayChildren.get(i).getName().equals(LeafVisitor.COST_INIT_NAME)
                                || arrayChildren.get(i).getName().contains(LeafVisitor.COST_RUN_ON_UI_THREAD_NAME))
                            && arrayChildren.get(i).getEndMillis() == 0) {
                        info.setParentId(arrayChildren.get(i).getId());
                        break;
                    }
                }
            } else {
                for (int i = arrayChildren.size() - 1; i >= 0; i--) {
                    if (arrayChildren.get(i).getThreadName().equals(info.getThreadName())
                            && !(arrayChildren.get(i).getClassName().equals(Type.getInternalName(Thread.class))
                                && arrayChildren.get(i).getName().equals(LeafVisitor.COST_INIT_NAME))
                            && !arrayChildren.get(i).getName().contains(LeafVisitor.COST_RUN_ON_UI_THREAD_NAME)
                            && arrayChildren.get(i).getEndMillis() == 0) {
                        info.setParentId(arrayChildren.get(i).getId());
                        break;
                    }
                }
            }
        }
        arrayChildren.add(info);
    }

    /**
     * Get {@link BaseInfo BaseInfo} by {@code id} from the Leaf Log.
     * @param id {@link Integer int}
     * @return {@link BaseInfo BaseInfo}
     */
    @Override
    public BaseInfo get(int id) {
        return arrayChildren.get(id-1);
    }

    /**
     * Get {@link LinkedList LinkedList} with all {@link BaseInfo BaseInfo} from the Leaf Log.
     */
    @Override
    public LinkedList<BaseInfo> getAll() {
        return arrayChildren;
    }

    /**
     * Get {@code JSON} with all {@link BaseInfo BaseInfo} from the Leaf Log.
     * @return {@link String String} using the JSON format.
     */
    @Override
    public String getJsonArray() {
        return gson.toJson(arrayChildren.toArray());
    }

    /**
     * Update value {@link BaseInfo BaseInfo} by {@code id} from {@code newInfo}
     * @param newInfo value {@link BaseInfo BaseInfo} with updated params, except {@code id}.
     */
    @Override
    public void update(BaseInfo newInfo) {
        arrayChildren.set(arrayChildren.indexOf(newInfo), newInfo);
    }

    /**
     * Remove {@link BaseInfo BaseInfo}.
     */
    @Override
    public void remove(BaseInfo info) {}

    /**
     * Clear the Leaf log.
     */
    @Override
    public void removeAll() {
        arrayChildren.clear();
    }

    @Override
    public String getJsonFor(BaseInfo info) {
        return gson.toJson(info);
    }
}