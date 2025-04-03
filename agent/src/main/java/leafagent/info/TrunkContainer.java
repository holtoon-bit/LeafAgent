package leafagent.info;

import leafagent.utils.AdbLeafSetting;

public class TrunkContainer extends BaseContainer {
    public TrunkContainer(String activityName, String className, String desc) {
        super(activityName, className, desc);
        AdbLeafSetting.startSocketServer();
    }

    public TrunkContainer(String activityName, String className) {
        this(activityName, className, "");
    }

    public TrunkContainer(String className) {
        this(className, className, "");
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
