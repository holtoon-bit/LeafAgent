package leafagent.plugin;

import org.gradle.api.tasks.Internal;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class LeafAgentMethodVisitor extends MethodVisitor {

    @Internal
    private static final String COST_ANNOTATION_DESC = "Lleafagent/annotations/Branch;";

    @Internal
    private boolean isInjected = false;

    @Internal
    private int startTimeId;

    @Internal
    private String className;

    @Internal
    private String methodName;

    @Internal
    private String desc;

    @Internal
    private boolean isStaticMethod;

    @Internal
    private Type[] argumentArrays;

    public LeafAgentMethodVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc) {
        super(api, mv);
        this.className = className;
        this.methodName = methodName;
        this.desc = desc;
        argumentArrays = Type.getArgumentTypes(desc);
        isStaticMethod = ((access & Opcodes.ACC_STATIC) != 0);
        startTimeId = (int) System.currentTimeMillis();
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (COST_ANNOTATION_DESC.equals(desc)) {
            isInjected = true;
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        if (isInjected) {
            mv.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "android/util/Log",
                    "e",
                    "(Ljava/lang/String;Ljava/lang/String;)I",
                    false
            );
            mv.visitInsn(Opcodes.POP);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitIntInsn(Opcodes.LSTORE, startTimeId);
        }
    }

//    @Override
//    public void visitEnd() {
//        mv.visitLdcInsn(className);
//        mv.visitMethodInsn(
//                Opcodes.INVOKESTATIC,
//                "android/util/Log",
//                "e",
//                "(Ljava/lang/String;Ljava/lang/String;)I",
//                false
//        );
//        mv.visitInsn(Opcodes.POP);
//        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//        mv.visitIntInsn(Opcodes.LSTORE, endTimeId);
//        super.visitEnd();
//    }
}