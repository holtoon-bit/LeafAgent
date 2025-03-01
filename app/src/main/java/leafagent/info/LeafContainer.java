package leafagent.info;

public class LeafContainer extends BaseContainer {
    public LeafContainer(String name, String className) {super(name, className);}

    public LeafContainer(String name, String className, String description) {super(name, className, description);}

    @Override
    public void endTime() {
        super.endTime();
    }
}
