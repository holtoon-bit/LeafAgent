package leafagent.plugin;

import leafagent.annotations.Leaf;
import leafagent.info.*;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.*;

import java.util.Arrays;
import java.util.LinkedList;

public class LeafVisitor extends MethodVisitor {

    @Internal
    public static final String COST_INIT_NAME = "<init>";
    @Internal
    public static final String COST_RUNNABLE_NAME = "run";
    @Internal
    public static final String COST_RUN_ON_UI_THREAD_NAME = "runOnUiThread";

    @Internal
    protected boolean isInjected = false;

    @Internal
    protected int access;
    @Internal
    protected String className;
    @Internal
    protected String methodName;
    @Internal
    protected String desc;
    @Internal
    protected Type[] argumentArrays;

    @Internal
    protected String description = "";

    @Internal
    protected boolean isNew = false;
    @Internal
    protected boolean isAddedThread = false;

    @Internal
    protected static LinkedList<String> lambdaInjected = new LinkedList<>();

    public LeafVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc, String branchDescription) {
        super(api, mv);
        this.access = access;
        this.className = className;
        this.methodName = methodName;
        this.desc = desc;
        argumentArrays = Type.getArgumentTypes(desc);
        if (methodName.equals(COST_INIT_NAME)) {
            this.description = branchDescription;
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Type.getDescriptor(Leaf.class).equals(desc)) {
            isInjected = true;
        }
        return new LeafAnnotationVisitor(api, super.visitAnnotation(desc, visible));
    }

    @Override
    public void visitCode() {
        if (isInjected || COST_INIT_NAME.equals(methodName) || lambdaInjected.contains(methodName + desc)) {
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
        if (isInjected && (
                (isNew && !isAddedThread && owner.equals(Type.getInternalName(Thread.class)) && name.equals(COST_INIT_NAME))
                || name.equals(COST_RUN_ON_UI_THREAD_NAME)
            )) {
            this.description = "";
            isAddedThread = true;
            addLeaf(name, owner);
            addLeafStartTime(name, owner);
            isAddedThread = false;
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        if (isInjected && name.equals(COST_RUNNABLE_NAME)) {
            String nameLambda = Arrays.stream(bootstrapMethodArguments).toList().get(1).toString();
            lambdaInjected.add(Arrays.stream(Arrays.stream(nameLambda.split("\\.")).toList().get(1).split(" ")).toList().get(0));
        }
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    protected void afterStart() {
        addLeaf(methodName, className);
        addLeafStartTime(methodName, className);
    }

    protected void addLeaf(String leafName, String className) {
        mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(LeafContainer.class));
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(leafName);
        mv.visitLdcInsn(className);
        mv.visitLdcInsn(description);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                Type.getInternalName(LeafContainer.class),
                COST_INIT_NAME,
                "("+Type.getDescriptor(String.class)+Type.getDescriptor(String.class)+Type.getDescriptor(String.class)+")V"
        );
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(CreatedContainers.class),
                "addNew",
                "("+Type.getDescriptor(BaseContainer.class)+")V"
        );
    }

    protected void addLeafStartTime(String leafName, String className) {
        mv.visitLdcInsn(leafName);
        mv.visitLdcInsn(className);
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(CreatedContainers.class),
                "get",
                "("+Type.getDescriptor(String.class)+Type.getDescriptor(String.class)+")"+Type.getDescriptor(BaseContainer.class)
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(BaseContainer.class),
                "startTime",
                "()V"
        );
    }

    protected void beforeReturn() {
        addLeafEndTime(methodName, className);
    }

    protected void addLeafEndTime(String leafName, String className) {
        mv.visitLdcInsn(leafName);
        mv.visitLdcInsn(className);
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(CreatedContainers.class),
                "get",
                "("+Type.getDescriptor(String.class)+Type.getDescriptor(String.class)+")"+Type.getDescriptor(BaseContainer.class)
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
        if (opcode == Opcodes.RETURN && (isInjected || COST_INIT_NAME.equals(methodName) || lambdaInjected.contains(methodName + desc))) {
            beforeReturn();
        }
        super.visitInsn(opcode);
    }

    private class LeafAnnotationVisitor extends AnnotationVisitor {
        protected LeafAnnotationVisitor(int api, AnnotationVisitor av) {
            super(api, av);
        }

        @Override
        public void visit(String name, Object value) {
            if (name.equals("desc")) {
                description = (String) value;
            }
            av.visit(name, value);
        }
    }
}