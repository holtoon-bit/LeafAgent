package leafagent;


import javassist.*;

public class OfficialTest {
    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();

        CtClass clazz = classPool.get("leafagent.A");

        CtMethod foo = clazz.getMethod("foo", "(Ljava/lang/String;)V");
        foo.insertAfter("System.out.println($1);");

        clazz.writeFile("leafagent");
        Class aClass = clazz.toClass(AppAgent.class);
        A newClass = (A) aClass.newInstance();
        newClass.foo("IT'S WORKED");
    }
}
