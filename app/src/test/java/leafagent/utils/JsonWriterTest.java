package leafagent.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import leafagent.info.BaseInfo;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

class JsonWriterTest {
    private static String path;

    private JsonWriter writer;

    @BeforeAll
    public static void setPath() {
        path = new File("src/test/resources/logTest.json").getAbsolutePath();
    }

    @BeforeEach
    public void createWriter() {
        writer = new JsonWriter(path);
    }

    @AfterEach
    public void clearWriter() {
        writer.clear();
        writer = null;
    }

    @Test
    public void writeLeaf() {
        // given
        BaseInfo info = new BaseInfo.Build().setName("addSeller").setClassName("foo").setThreadName("main").build();

        // when
        writer.writeLeaf(info);
        Collection<BaseInfo> expectedList = writer.getStruct();

        // then
        Assertions.assertNotNull(expectedList);
        Assertions.assertArrayEquals(expectedList.toArray(), Collections.singletonList(info).toArray());
    }

    @Test
    public void save() {
        // given
        ArrayList<BaseInfo> actualLogg = new ArrayList<>();
        BaseInfo info = new BaseInfo.Build().setName("addSeller").setClassName("foo").setThreadName("main").build();
        actualLogg.add(info);

        // when
        writer.writeLeaf(info);
        writer.save();

        // then
        ArrayList<BaseInfo> expectedLogg = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            Gson gson = new Gson();
            TypeToken<ArrayList<BaseInfo>> collectionType = new TypeToken<>(){};
            expectedLogg = gson.fromJson(bufferedReader.readLine(), collectionType);
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        Assertions.assertEquals(expectedLogg, actualLogg);
    }
}