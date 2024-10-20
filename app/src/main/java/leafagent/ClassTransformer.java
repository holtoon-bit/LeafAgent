package leafagent;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassTransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader,
                            String className,
                            Class classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        if ("gradleassertmain.App".equals(className.replaceAll("/", "."))) {
            try {
                ClassPool classPool = ClassPool.getDefault();

                CtClass ctClass = classPool.getCtClass(className.replaceAll("/", "."));
                CtMethod ctMethod = ctClass.getDeclaredMethod("main");
                ctMethod.insertBefore("{ System.out.println(\"Real hello\"); }");

                 return ctClass.toBytecode();
            } catch (RuntimeException e) {
                System.out.println("Runtime Error Object: " + className + "\n" + e.getLocalizedMessage());
            } catch (Exception e) {
                System.out.println("Error Object: " + className + "\n" + e.getLocalizedMessage());
            }
        }
        return null;
    }

    public boolean isCorrectName(String className) {
        if (!className.startsWith("org/gradle/")
                && !className.startsWith("java/lang/")
                && !className.startsWith("java/io/")
                && !className.startsWith("java/text/")
                && !className.startsWith("java/math/")
                && !className.startsWith("java/util/")
                && !className.startsWith("java/sql/")
                && !className.startsWith("java/beans/")
                && !className.startsWith("java/nio/")
                && !className.startsWith("java/time/")
                && !className.startsWith("java/awt/")
                && !className.startsWith("java/security/")
                && !className.startsWith("java/net/")
                && !className.startsWith("com/google/")
                && !className.startsWith("jdk/")
                && !className.startsWith("org/apache/")
                && !className.startsWith("sun/")
                && !className.startsWith("net/rubygrapefruit/")
                && !className.startsWith("org/codehaus/")
                && !className.startsWith("com/sun/")
                && !className.startsWith("groovy/")
                && !className.startsWith("org/w3c/")
                && !className.startsWith("javax/")
                && !className.startsWith("org/xml/")
                && !className.startsWith("groovyjarjarasm/")
                && !className.startsWith("it/")
                && !className.startsWith("kotlin/")
                && !className.startsWith("com/jcraft/")
                && !className.startsWith("org/objectweb/")
                && !className.startsWith("org/tomlj/")
                && !className.startsWith("org/antlr/")
                && !className.startsWith("org/objectweb/")
                && !className.startsWith("org/slf4j/")
                && !className.startsWith("settings_")
                && !className.startsWith("build_")) {
            return true;
        } else {
            return false;
        }
    }
}