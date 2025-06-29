package leafagent.info;

import leafagent.utils.AdbLeafSetting;

/**
 * Handler for an activity object with an annotation {@link leafagent.annotations.Branch @Branch}.
 */
public class TrunkContainer extends BaseContainer {
    public TrunkContainer(String activityName, String className, String desc) {
        super(activityName, className, desc);
        AdbLeafSetting.startSocketServer();
    }

    public TrunkContainer(String activityName, String className) {
        this(activityName, className, "");
    }

    /**
     * Override {@link BaseContainer#startTime() startTime()} and set {@code id} to {@code -1} for {@link BaseInfo BaseInfo}.
     */
    @Override
    public void startTime() {
        getInfo().setParentId(-1);
        super.startTime();
    }

    /**
     * Override {@link BaseContainer#endTime() endTime()} and save the Leaf Log to local device using {@link leafagent.utils.LeafKeepable}.
     */
    @Override
    public void endTime() {
        super.endTime();
    }
}
