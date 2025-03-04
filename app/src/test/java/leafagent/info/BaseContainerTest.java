package leafagent.info;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseContainerTest {

    @Test
    void getInfo() {
        // given
        BaseInfo actualInfo = new BaseInfo.Build().setName("1").setClassName("Main").setId(0).setThreadName("Test worker").build();

        // when
        BaseContainer expectedContainer = new BaseContainer("1", "Main");
        BaseInfo expectedInfo = expectedContainer.getInfo();

        // then
        Assertions.assertEquals(expectedInfo, actualInfo);
        Assertions.assertEquals(expectedInfo.getId(), actualInfo.getId());
        Assertions.assertEquals(expectedInfo.getName(), actualInfo.getName());
        Assertions.assertEquals(expectedInfo.getClassName(), actualInfo.getClassName());
        Assertions.assertEquals(expectedInfo.getThreadName(), actualInfo.getThreadName());
        Assertions.assertEquals(expectedInfo.getParentId(), actualInfo.getParentId());
    }
}