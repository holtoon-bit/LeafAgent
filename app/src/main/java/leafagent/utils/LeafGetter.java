package leafagent.utils;

import leafagent.info.BaseContainer;

import java.util.LinkedList;

public class LeafGetter {
    public LinkedList<BaseContainer> getChildren(BaseContainer leaf) {
        return leaf.getChildren();
    }
}