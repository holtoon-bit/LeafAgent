package leafagent.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogSocketWriterTest {

    @Test
    void setProjectPath() {
        // given
        String path = "newPath";

        // when
        LogSocketWriter.setProjectPath(path);

        // then
        Assertions.assertNotNull(LogSocketWriter.getProjectPath());
        Assertions.assertEquals(LogSocketWriter.getProjectPath(), path);
    }

    @Test
    void isHaveProjectPath() {
        // given
        String path = "newPath1";

        // when
        LogSocketWriter.setProjectPath(path);

        // then
        Assertions.assertTrue(LogSocketWriter.isHaveProjectPath());
    }
}