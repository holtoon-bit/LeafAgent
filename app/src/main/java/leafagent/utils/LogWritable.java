package leafagent.utils;

import leafagent.info.BaseContainer;

import java.io.File;
import java.io.FileNotFoundException;

public interface LogWritable {
    File createFile(String name) throws FileNotFoundException;
    void startLeaf(BaseContainer leaf);
    void endLeaf();
}
