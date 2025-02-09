package leafagent.info;

public class TrunkContainer extends BranchContainer {
    public TrunkContainer(String activityName) {
        super(activityName);
    }

    @Override
    public void startTime() {
        getInfo().setParentId(-1);
        super.startTime();
    }
}
