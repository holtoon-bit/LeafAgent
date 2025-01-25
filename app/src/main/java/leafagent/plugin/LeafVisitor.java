package leafagent.plugin;

import leafagent.info.*;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class LeafVisitor extends MethodVisitor {

    @Internal
    public static final String COST_ANNOTATION_LEAF = "Lleafagent/annotations/Leaf;";
    @Internal
    protected static final String COST_INIT_NAME = "<init>";

    @Internal
    protected boolean isInjected = false;

    @Internal
    protected String className;
    @Internal
    protected String methodName;
    @Internal
    protected String desc;
    @Internal
    protected Type[] argumentArrays;

    public LeafVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc) {
        super(api, mv);
        this.className = className;
        this.methodName = methodName;
        this.desc = desc;
        argumentArrays = Type.getArgumentTypes(desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (COST_ANNOTATION_LEAF.equals(desc)) {
            isInjected = true;
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        if (isInjected) {
            afterStart();
        }
        if (COST_INIT_NAME.equals(methodName)) {
            intoInit();
            startBranch();
        }
    }

    protected void afterStart() {
        String descTrunk = Type.getDescriptor(LeafContainer.class);
        mv.visitTypeInsn(
                Opcodes.NEW,
                descTrunk.substring(1, descTrunk.length()-1)
        );
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(methodName);
        mv.visitLdcInsn(className);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                Type.getInternalName(LeafContainer.class),
                COST_INIT_NAME,
                "("+Type.getDescriptor(String.class)+Type.getDescriptor(String.class)+")V"
        );
        mv.visitVarInsn(Opcodes.ASTORE, 2);
        // LINENUMBER 7 L0
        //    NEW leafagent/info/LeafContainer
        //    DUP
        //    LDC "nameS"
        //    LDC "nameP"
        //    INVOKESPECIAL leafagent/info/LeafContainer.<init> (Ljava/lang/String;Ljava/lang/String;)V
        //    ASTORE 1
        // LINENUMBER 8 L1
        //    ALOAD 1
        //    INVOKEVIRTUAL leafagent/info/LeafContainer.startTime ()V
        // LINENUMBER 11 L4
        //    ALOAD 1
        //    INVOKEVIRTUAL leafagent/info/LeafContainer.endTime ()V
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(LeafContainer.class),
                "startTime",
                "()V"
        );
    }

    protected void beforeReturn() {
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(LeafContainer.class),
                "endTime",
                "()V"
        );
    }

    protected void intoInit() {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(BranchContainer.class));
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(className);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                Type.getInternalName(BranchContainer.class),
                COST_INIT_NAME,
                "(Ljava/lang/String;)V"
        );
        mv.visitFieldInsn(
                Opcodes.PUTFIELD,
                className,
                "branchContainer",
                Type.getDescriptor(BranchContainer.class)
        );
    }

    private void startBranch() {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(
                Opcodes.GETFIELD,
                className,
                "branchContainer",
                Type.getDescriptor(BranchContainer.class)
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(BranchContainer.class),
                "startTime",
                "()V"
        );
    }

    @Override
    public void visitInsn(int opcode) {
        if (isInjected && opcode == Opcodes.RETURN) {
            beforeReturn();
        }
        super.visitInsn(opcode);
    }
}