package leafagent;

public interface LocalJsonDAO {
    void create(LeafInfo info);
    LeafInfo read(int id);
    void update(LeafInfo info);
    void delete(LeafInfo info);
}
