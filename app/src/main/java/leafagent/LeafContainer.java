package leafagent;

public class LeafContainer extends BaseContainer<BaseContainer> {
    public LeafContainer(String name) {super(name);}
    public LeafContainer(BranchContainer parent, String name) {super(parent, name);}
}
