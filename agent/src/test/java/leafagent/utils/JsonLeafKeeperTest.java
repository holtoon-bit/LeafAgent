package leafagent.utils;

import leafagent.info.BaseInfo;
import java.util.Collections;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonLeafKeeperTest {
    private static JsonLeafKeeper keeper;

    @BeforeAll
    public static void createKeeper() {
        keeper = new JsonLeafKeeper();
    }

    @BeforeEach
    public void clearKeeper() {
        keeper.clear();
    }

    @Test
    public void insertLeaf() {
        // given
        BaseInfo info = new BaseInfo.Build().setName("addSeller").setClassName("foo").setThreadName("main").build();

        // when
        keeper.insertLeaf(info);
        Collection<BaseInfo> expectedList = keeper.getStruct();

        // then
        Assertions.assertNotNull(expectedList);
        Assertions.assertArrayEquals(expectedList.toArray(), Collections.singletonList(info).toArray());
    }
}