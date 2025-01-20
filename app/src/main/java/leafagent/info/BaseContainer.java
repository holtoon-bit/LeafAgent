package leafagent.info;

import leafagent.utils.JsonLinkedWriter;
import leafagent.utils.LogWriter;

public class BaseContainer<T extends BaseContainer> {
    protected BaseInfo info;
    private LogWriter writer;

    public BaseContainer() {
    }

    public BaseContainer(String name) {
        super();
        info = new BaseInfo.Build().setName(name).build();
        writer = new JsonLinkedWriter("logg");
    }

    public LogWriter getWriter() {
        return writer;
    }

    public BaseInfo getInfo() {
        return info;
    }

    public void addChild(BaseContainer container) {
        info.addChild(container.getInfo());
    }

    public void startTime() {
        System.out.println("startTime " + info.getName());
        info.setStartMillis(System.currentTimeMillis());
        getWriter().writeLeaf(info);
    }

    public void endTime() {
        info.setEndMillis(System.currentTimeMillis());
        getWriter().updateLeaf(info);
    }
}