package leafagent.info;

public class BranchContainer extends BaseContainer {
    public BranchContainer(String className) {super(className, className);}

    @Override
    public void startTime() {
        getInfo().setParentId(-1);
        super.startTime();
    }
}
