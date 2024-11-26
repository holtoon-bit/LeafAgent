package leafagent.utils;

import leafagent.info.BaseInfo;

import java.io.File;
import java.io.FileNotFoundException;

public interface LogWritable {
    File createFile(String name) throws FileNotFoundException;
    void startLeaf(BaseInfo info);
    void endLeaf();
}
