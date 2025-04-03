package leafagent.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogWriterTest {

    @Test
    void setProjectPath() {
        // given
        String path = "newPath";

        // when
        LogWriter.setProjectPath(path);

        // then
        Assertions.assertNotNull(LogWriter.getProjectPath());
        Assertions.assertEquals(LogWriter.getProjectPath(), path);
    }

    @Test
    void isHaveProjectPath() {
        // given
        String path = "newPath1";

        // when
        LogWriter.setProjectPath(path);

        // then
        Assertions.assertTrue(LogWriter.isHaveProjectPath());
    }
}