package leafagent.plugin;

import leafagent.annotations.Leaf;
import leafagent.info.*;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class LeafVisitor extends MethodVisitor {

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

    @Internal
    protected boolean isNew = false;
    @Internal
    protected boolean isAddedThread = false;

    public LeafVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc) {
        super(api, mv);
        this.className = className;
        this.methodName = methodName;
        this.desc = desc;
        argumentArrays = Type.getArgumentTypes(desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Type.getDescriptor(Leaf.class).equals(desc)) {
            isInjected = true;
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        if (isInjected || COST_INIT_NAME.equals(methodName)) {
            afterStart();
        }
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        isNew = (opcode == Opcodes.NEW);
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (isInjected && isNew && !isAddedThread && owner.equals(Type.getInternalName(Thread.class)) && name.equals(COST_INIT_NAME)) {
            isAddedThread = true;
            addLeaf(owner + "." + name);
            addLeafStartTime(owner + "." + name);
            isAddedThread = false;
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    protected void afterStart() {
        addLeaf(className+"."+methodName);
        addLeafStartTime(className+"."+methodName);
    }

    private void addLeaf(String leafName) {
        mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(LeafContainer.class));
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(leafName);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                Type.getInternalName(LeafContainer.class),
                COST_INIT_NAME,
                "("+Type.getDescriptor(String.class)+")V"
        );
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(CreatedContainers.class),
                "addNew",
                "("+Type.getDescriptor(BaseContainer.class)+")V"
        );
    }

    private void addLeafStartTime(String leafName) {
        mv.visitLdcInsn(leafName);
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(CreatedContainers.class),
                "get",
                "("+Type.getDescriptor(String.class)+")"+Type.getDescriptor(BaseContainer.class)
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(BaseContainer.class),
                "startTime",
                "()V"
        );
    }

    protected void beforeReturn() {
        addLeafEndTime(className+"."+methodName);
    }

    private void addLeafEndTime(String leafName) {
        mv.visitLdcInsn(leafName);
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(CreatedContainers.class),
                "get",
                "("+Type.getDescriptor(String.class)+")"+Type.getDescriptor(BaseContainer.class)
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(BaseContainer.class),
                "endTime",
                "()V"
        );
    }

    @Override
    public void visitInsn(int opcode) {
        if ((isInjected || COST_INIT_NAME.equals(methodName)) && opcode == Opcodes.RETURN) {
            beforeReturn();
        }
        super.visitInsn(opcode);
    }
}