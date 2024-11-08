package leafagent.old;

import javassist.*;

import java.io.*;
import java.util.LinkedList;

public class BytecodeTransformer {
    public void transform(LinkedList<File> files) {
        for (File file : files) {
            System.out.println(file.getName());
            if (file.getName().equals("SellerActivity.class")) {
                try {
                    ClassPool classPool = ClassPool.getDefault();
                    CtClass ctClass = classPool.makeClass(new FileInputStream(file));
                    CtMethod method = ctClass.getDeclaredMethod("greeting");
                    method.insertBefore("System.out.println(\"" + file.getName() + "\");");
                    ctClass.writeFile("F:\\Codding\\Java\\LeafAgentAndroidApp\\app\\build\\intermediates\\javac\\debug\\classes");
                    ctClass.defrost();
                } catch (IOException | CannotCompileException | NotFoundException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
