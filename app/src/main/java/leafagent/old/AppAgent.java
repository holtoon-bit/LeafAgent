package leafagent.old;

import java.lang.instrument.Instrumentation;

public class AppAgent {
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Classes loaded: " + instrumentation.getAllLoadedClasses().length);
        instrumentation.addTransformer(new ClassTransformer());
    }
}