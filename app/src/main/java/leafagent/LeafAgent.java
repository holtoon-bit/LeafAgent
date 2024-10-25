package leafagent;

import java.lang.instrument.Instrumentation;

public class LeafAgent {
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Leaf is working right now!");
        instrumentation.addTransformer(new MainTransformer());
    }
}