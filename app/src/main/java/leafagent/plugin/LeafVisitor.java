package leafagent.plugin;

import leafagent.info.BranchContainer;
import leafagent.info.LeafContainer;
import leafagent.info.TrunkContainer;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class LeafVisitor extends MethodVisitor {

    @Internal
    public static final String COST_ANNOTATION_DESC = "Lleafagent/annotations/Leaf;";
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
        if (COST_ANNOTATION_DESC.equals(desc)) {
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
        }
    }

    private void afterStart() {
        String descTrunk = Type.getDescriptor(TrunkContainer.class);
        mv.visitTypeInsn(
                Opcodes.NEW,
                descTrunk.substring(1, descTrunk.length()-2)
        );
        mv.visitInsn(Opcodes.DUP);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(
                Opcodes.GETFIELD,
                className,
                "branchContainer",
                descTrunk
        );
        mv.visitLdcInsn(methodName);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                Type.getInternalName(LeafContainer.class),
                COST_INIT_NAME,
                "("+Type.getDescriptor(BranchContainer.class)+Type.getDescriptor(String.class)+")V"
        );
        mv.visitVarInsn(Opcodes.ASTORE, 1);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(LeafContainer.class),
                "startTime",
                "()V"
        );
    }

    private void intoInit() {

    }

    @Override
    public void visitInsn(int opcode) {
        if (isInjected && opcode == Opcodes.RETURN) {
            beforeReturn();
        }
        super.visitInsn(opcode);
    }

    protected void beforeReturn() {
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(LeafContainer.class),
                "endTime",
                "()V"
        );
    }
}