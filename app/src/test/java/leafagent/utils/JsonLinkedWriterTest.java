package leafagent.utils;

import leafagent.info.BaseInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class JsonLinkedWriterTest {

    private JsonLinkedWriter writer;

    @BeforeEach
    public void createWriter() {
        writer = new JsonLinkedWriter("logTest");
    }

    @AfterEach
    public void clearWriter() {
        writer.clear();
        writer = null;
    }

    @Test
    void writeLeaf() {
        // given
        BaseInfo info = new BaseInfo.Build().setName("addSeller").setStartMillis(System.currentTimeMillis()).build();

        // when
        writer.writeLeaf(info);
        Collection<BaseInfo> array = writer.getStruct();

        // then
        Assertions.assertNotNull(array);
        Assertions.assertArrayEquals(new ArrayList<>(Arrays.asList(info)).toArray(), array.toArray());
    }

    @Test
    void getStruct() {
        Collection<BaseInfo> collection = writer.getStruct();
        Assertions.assertNotNull(collection);
    }
}