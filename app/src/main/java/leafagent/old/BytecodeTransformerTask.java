package leafagent.old;

import leafagent.plugin.LeafPlugin;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.LinkedList;

public class BytecodeTransformerTask extends DefaultTask {

    @TaskAction
    public void transform() {
//        File classesDir = new File(LeafPlugin.path, "\\build\\intermediates\\javac\\debug\\classes\\com\\market\\leafandroid");
//        TransformClassesHandler handler = new TransformClassesHandler();
//        LinkedList<File> classes = handler.getAllFiles(classesDir);
//        BytecodeTransformer transformer = new BytecodeTransformer();
//        transformer.transform(classes);
    }

}
