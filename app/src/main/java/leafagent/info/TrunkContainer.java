package leafagent.info;

public class TrunkContainer extends LeafContainer {
    public TrunkContainer(String activityName, String className) {
        super(activityName, className);
    }

    public TrunkContainer(String className) {
        super(className, className);
    }

    @Override
    public void startTime() {
        getInfo().setParentId(-1);
        super.startTime();
    }

    @Override
    public void endTime() {
        getWriter().save();
        super.endTime();
    }
}
