package leafagent.utils;

import leafagent.info.BaseInfo;
import org.gradle.internal.impldep.com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class JsonLeafKeepableDAOImplTest {

    private static JsonLeafKeepableDAOImpl dao;

    @BeforeAll
    public static void setPath() {
        dao = new JsonLeafKeepableDAOImpl();
    }

    @BeforeEach
    public void clear() {
        dao.removeAll();
    }

    @Test
    void create() {
        // given
        BaseInfo info1 = new BaseInfo.Build().setName("1").setClassName("Main").setThreadName("main").setId(1).setParentId(-1).build();
        BaseInfo info2 = new BaseInfo.Build().setName("2").setClassName("Main").setThreadName("main").setId(2).setParentId(1).build();
        BaseInfo info3 = new BaseInfo.Build().setName("3").setClassName("Main").setThreadName("main").setId(3).setParentId(2).build();
        ArrayList<BaseInfo> actualList = Lists.newArrayList(info1, info2, info3);

        // when
        BaseInfo exInfo1 = new BaseInfo.Build().setName("1").setClassName("Main").setThreadName("main").setId(1).build();
        BaseInfo exInfo2 = new BaseInfo.Build().setName("2").setClassName("Main").setThreadName("main").setId(2).build();
        BaseInfo exInfo3 = new BaseInfo.Build().setName("3").setClassName("Main").setThreadName("main").setId(3).build();
        dao.create(exInfo1);
        dao.create(exInfo2);
        dao.create(exInfo3);

        // then
        Assertions.assertEquals(dao.getAll().size(), 3);
        Assertions.assertEquals(dao.getAll().get(0).getParentId(), exInfo1.getParentId());
        Assertions.assertEquals(dao.getAll().get(1).getParentId(), exInfo2.getParentId());
        Assertions.assertEquals(dao.getAll().get(2).getParentId(), exInfo3.getParentId());
        Assertions.assertArrayEquals(dao.getAll().toArray(), actualList.toArray());
    }

    @Test
    void update() {
        // given
        BaseInfo info1 = new BaseInfo.Build().setName("1").setClassName("Main").setThreadName("main").setId(1).setParentId(0).build();
        BaseInfo info2 = new BaseInfo.Build().setName("2").setClassName("Main").setThreadName("main").setId(2).setParentId(1).build();
        BaseInfo info3 = new BaseInfo.Build().setName("3").setClassName("Main").setThreadName("main").setDesc("Test2").setId(3).setParentId(2).build();
        ArrayList<BaseInfo> actualList = Lists.newArrayList(info1, info2, info3);

        // when
        BaseInfo exInfo1 = new BaseInfo.Build().setName("1").setClassName("Main").setThreadName("main").setId(1).build();
        BaseInfo exInfo2 = new BaseInfo.Build().setName("2").setClassName("Main").setThreadName("main").setId(2).build();
        BaseInfo exInfo3 = new BaseInfo.Build().setName("3").setClassName("Main").setThreadName("main").setDesc("Test").setId(3).build();
        dao.create(exInfo1);
        dao.create(exInfo2);
        dao.create(exInfo3);
        BaseInfo exUpdateInfo3 = dao.get(exInfo3.getId());
        exUpdateInfo3.setClassName("Sub");
        dao.update(exUpdateInfo3);

        // then
        Assertions.assertEquals(dao.getAll().size(), 3);
        Assertions.assertEquals(dao.getAll().get(exInfo1.getId()-1).getParentId(), info1.getParentId());
        Assertions.assertEquals(dao.getAll().get(exInfo2.getId()-1).getParentId(), info2.getParentId());
        Assertions.assertEquals(dao.getAll().get(exInfo3.getId()-1).getParentId(), info3.getParentId());
        Assertions.assertEquals(dao.getAll().get(exInfo3.getId()-1).getClassName(), exUpdateInfo3.getClassName());
        Assertions.assertArrayEquals(dao.getAll().toArray(), actualList.toArray());
    }
}