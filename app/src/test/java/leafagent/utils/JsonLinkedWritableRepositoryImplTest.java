package leafagent.utils;

import leafagent.info.BaseInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class JsonLinkedWritableRepositoryImplTest {
    private LogWritableRepository repository;

    @BeforeEach
    void createDAO() {
        repository = new JsonLinkedWritableRepositoryImpl("logTest.json");
    }
    
    @AfterEach
    void clearDAO() {
        repository.removeAll();
        repository = null;
    }

    @Test
    void insert() {
        // given
        BaseInfo info1 = new BaseInfo.Build().setName("first").build();

        // when
        repository.insert(info1);
        LinkedList<BaseInfo> actualArray = repository.getAll();

        // then
        ArrayList<BaseInfo> expectedArray = new ArrayList<>(Arrays.asList(info1));
        Assertions.assertEquals(expectedArray, actualArray);
        Assertions.assertEquals(info1, actualArray.get(0));
        Assertions.assertEquals(info1.getId(), actualArray.get(0).getId());
        Assertions.assertEquals(info1.getParentId(), actualArray.get(0).getParentId());
        Assertions.assertEquals(info1.getName(), actualArray.get(0).getName());
        Assertions.assertEquals(info1.getDesc(), actualArray.get(0).getDesc());
        Assertions.assertEquals(info1.getChildren(), actualArray.get(0).getChildren());
        Assertions.assertEquals(info1.getStartMillis(), actualArray.get(0).getStartMillis());
        Assertions.assertEquals(info1.getEndMillis(), actualArray.get(0).getEndMillis());
    }

    @Test
    void getAll() {
        // given
        BaseInfo info1 = new BaseInfo.Build().setName("first").build();
        BaseInfo info2 = new BaseInfo.Build().setName("second").build();
        BaseInfo info3 = new BaseInfo.Build().setName("third").build();

        // when
        repository.insert(info1);
        repository.insert(info2);
        repository.insert(info3);
        LinkedList<BaseInfo> actualArray = repository.getAll();

        // then
        ArrayList<BaseInfo> expectedArray = new ArrayList<>(Arrays.asList(info1, info2, info3));
        Assertions.assertEquals(expectedArray, actualArray);
    }
}