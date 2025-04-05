package leafagent.info;

/**
 * Handler for an object with an annotation {@link leafagent.annotations.Branch @Branch}.
 */
public class BranchContainer extends BaseContainer {
    public BranchContainer(String className) {super(className, className);}

    /**
     * Override {@link BaseContainer#startTime() startTime()} and set {@code id} to {@code -1} for {@link BaseInfo BaseInfo}.
     */
    @Override
    public void startTime() {
        getInfo().setParentId(-1);
        super.startTime();
    }
}
