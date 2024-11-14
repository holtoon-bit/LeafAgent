package leafagent.repository;

import leafagent.info.LeafInfo;

public interface LocalJsonDAO {
    void create(LeafInfo info);
    LeafInfo read(int id);
    void update(LeafInfo info);
    void delete(LeafInfo info);
}
