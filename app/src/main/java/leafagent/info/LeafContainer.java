package leafagent.info;

public class LeafContainer extends BaseContainer<BaseContainer> {
    public LeafContainer(String name) {super(name);}
    public LeafContainer(BranchContainer parent, String name) {
        super(name);
//        parent.addChild(this);
    }

    @Override
    public void endTime() {
        super.endTime();
    }
}
