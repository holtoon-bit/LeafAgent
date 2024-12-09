package leafagent.info;

import java.util.LinkedList;

public class BaseInfo {
    private int id;
    private int parentId;
    private final String name;
    private String desc = "";
    private long startMillis;
    private long endMillis;

    private LinkedList<BaseInfo> children;

    public BaseInfo(Build builder) {
        this.id = builder.id;
        this.parentId = builder.parentId;
        this.name = builder.name;
        this.desc = builder.desc;
        this.startMillis = builder.startMillis;
        this.endMillis = builder.endMillis;
        this.children = builder.children;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public long getStartMillis() {
        return startMillis;
    }

    protected void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    protected void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
    }

    public void addChild(BaseInfo info) {
        children.add(info);
    }

    public LinkedList<BaseInfo> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return name;
    }

    public static class Build {
        protected int id;
        protected int parentId;
        protected String name;
        protected String desc = "";
        protected long startMillis;
        protected long endMillis;

        protected LinkedList<BaseInfo> children = new LinkedList<>();

        public Build setId(int id) {
            this.id = id;
            return this;
        }

        public Build setParentId(int parentId) {
            this.parentId = parentId;
            return this;
        }

        public Build setName(String name) {
            this.name = name;
            return this;
        }

        public Build setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public Build setStartMillis(long startMillis) {
            this.startMillis = startMillis;
            return this;
        }

        public Build setEndMillis(long endMillis) {
            this.endMillis = endMillis;
            return this;
        }

        public BaseInfo build() {
            if (!name.isEmpty()) {
                return new BaseInfo(this);
            }
            throw new NullPointerException("Set the name value, for create new the BaseInfo");
        }
    }
}
