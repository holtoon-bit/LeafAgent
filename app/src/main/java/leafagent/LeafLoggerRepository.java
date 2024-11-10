package leafagent;

import java.util.HashMap;

public interface LeafLoggerRepository {
    void add(LeafInfo info);
    LeafInfo get(int id);
    HashMap<LeafInfo, HashMap> getAll();
    void update(LeafInfo info);
    void remove(LeafInfo info);
}
