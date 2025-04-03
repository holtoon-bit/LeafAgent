package leafagent.info;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TrunkContainerTest {
    @Test
    public void startTime() {
        // given
        BaseInfo actualInfo = new BaseInfo.Build().setName("1").setClassName("Main").setId(0).setThreadName("Test worker").setParentId(-1).build();

        // when
        TrunkContainer expectedContainer = new TrunkContainer("1", "Main");
        expectedContainer.startTime();
        BaseInfo expectedInfo = expectedContainer.getInfo();

        // then
        Assertions.assertEquals(expectedInfo, actualInfo);
        Assertions.assertEquals(expectedInfo.getParentId(), actualInfo.getParentId());
    }
}