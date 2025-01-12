package leafagent.info;

public class BranchContainer extends BaseContainer<LeafContainer> {
    public BranchContainer(String className) {super(className);}

    @Override
    public void startTime() {
        getInfo().setParentId(-1);
        super.startTime();
    }
}
