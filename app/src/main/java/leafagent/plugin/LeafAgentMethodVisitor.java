package leafagent.plugin;

import leafagent.LeafLogger;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class LeafAgentMethodVisitor extends MethodVisitor {

    @Internal
    private static final String COST_ANNOTATION_DESC = "Lleafagent/annotations/Leaf;";

    @Internal
    private boolean isInjected = false;

    @Internal
    private String className;

    @Internal
    private String methodName;

    @Internal
    private String desc;

    @Internal
    private Type[] argumentArrays;

    public LeafAgentMethodVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc) {
        super(api, mv);
        this.className = className;
        this.methodName = methodName;
        this.desc = desc;
        argumentArrays = Type.getArgumentTypes(desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (COST_ANNOTATION_DESC.equals(desc)) {
            isInjected = true;
            LeafLogger.createNewLeaf(methodName);
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        if (isInjected) {
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    Type.getInternalName(LeafLogger.class),
                    "logStart",
                    "(Ljava/lang/String;)V",
                    false
            );
        }
    }

    @Override
    public void visitInsn(int opcode) {
        if (isInjected && opcode == Opcodes.RETURN) {
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    Type.getInternalName(LeafLogger.class),
                    "logEnd",
                    "(Ljava/lang/String;)V",
                    false
            );
        }
        super.visitInsn(opcode);
    }
}