package leafagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import leafagent.annotations.Branch;
import leafagent.annotations.Leaf;

import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

public class MainTransformer implements ClassFileTransformer {
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        try {
            if (classBeingRedefined.isAnnotationPresent(Branch.class)) {
                for (Method method : classBeingRedefined.getMethods()) {
                    if (method.isAnnotationPresent(Leaf.class)) {
                        ClassPool classPool = ClassPool.getDefault();
                        CtClass ctClass = classPool.getCtClass(className.replaceAll("/", "."));
                        CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName());
                        ctMethod.insertBefore("System.out.println(\"LeafAgent is worked\"");
                        return ctClass.toBytecode();
                    }
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Runtime Error Object: " + className + "\n" + e.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println("Error Object: " + className + "\n" + e.getLocalizedMessage());
        }
        return null;
    }
}
