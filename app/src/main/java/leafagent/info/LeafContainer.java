package leafagent.info;

public class LeafContainer extends BaseContainer<BaseContainer> {
    public LeafContainer(String name) {super(name);}

    public LeafContainer(BranchContainer parent, String name) {
        super(name);
//        parent.addChild(this);
    }

    public LeafContainer(String name, String parentName) {
        super(name, parentName);
    }

    @Override
    public void endTime() {
        super.endTime();
    }
}
