package leafagent.plugin;

import leafagent.annotations.Leaf;
import leafagent.info.*;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.*;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Base {@link MethodVisitor MethodVisitor} for each method. Using to editing the bytecode of a method.
 */
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

    /**
     * Check if the method has a {@link Leaf @Leaf} annotation.
     *
     * @param desc the class descriptor of the annotation class.
     * @param visible {@literal true} if the annotation is visible at runtime.
     * @return {@link LeafAnnotationVisitor LeafAnnotationVisitor}
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Type.getDescriptor(Leaf.class).equals(desc)) {
            isInjected = true;
        }
        return new LeafAnnotationVisitor(api, super.visitAnnotation(desc, visible));
    }

    /**
     * Start editing the bytecode of the method.
     */
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

    /**
     * Add methods to start observation in {@link Thread Thread} and {@code runOnUiThread}.
     *
     * @param opcode the opcode of the type instruction to be visited. This opcode is either
     *     INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE.
     * @param owner the internal name of the method's owner class (see {@link
     *     Type#getInternalName()}).
     * @param name the method's name.
     * @param descriptor the method's descriptor (see {@link Type}).
     * @param isInterface if the method's owner class is an interface.
     */
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

    /**
     * Add methods to start observation in {@code lambda}.
     *
     * @param name the method's name.
     * @param descriptor the method's descriptor (see {@link Type}).
     * @param bootstrapMethodHandle the bootstrap method.
     * @param bootstrapMethodArguments the bootstrap method constant arguments. Each argument must be
     *     an {@link Integer}, {@link Float}, {@link Long}, {@link Double}, {@link String}, {@link
     *     Type}, {@link Handle} or {@link ConstantDynamic} value. This method is allowed to modify
     *     the content of the array so a caller should expect that this array may change.
     */
    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        if (isInjected && name.equals(COST_RUNNABLE_NAME)) {
            String nameLambda = Arrays.stream(bootstrapMethodArguments).toList().get(1).toString();
            lambdaInjected.add(Arrays.stream(Arrays.stream(nameLambda.split("\\.")).toList().get(1).split(" ")).toList().get(0));
        }
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    /**
     * Add methods to start observation.
     */
    protected void afterStart() {
        addLeaf(methodName, className);
        addLeafStartTime(methodName, className);
    }

    /**
     * Create an object of {@link LeafContainer LeafContainer} in this method.
     *
     * @param leafName method name.
     * @param className class name.
     */
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

    /**
     * Calling a {@link LeafContainer#startTime() LeafContainer.startTime()} in this method.
     *
     * @param leafName method name.
     * @param className class name.
     */
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

    /**
     * Add methods to finish observation all the edited method.
     */
    protected void beforeReturn() {
        addLeafEndTime(methodName, className);
    }

    /**
     * Calling a {@link LeafContainer#endTime() LeafContainer.endTime()} in this method.
     *
     * @param leafName method name.
     * @param className class name.
     */
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

    /**
     * Finish editing the bytecode of the method.
     *
     * @param opcode the opcode of the instruction to be visited (see {@link Opcodes Opcodes}.
     */
    @Override
    public void visitInsn(int opcode) {
        if (opcode <= Opcodes.RETURN && opcode >= Opcodes.IRETURN
                && (isInjected || COST_INIT_NAME.equals(methodName) || lambdaInjected.contains(methodName + desc))) {
            beforeReturn();
        }
        super.visitInsn(opcode);
    }

    private class LeafAnnotationVisitor extends AnnotationVisitor {
        protected LeafAnnotationVisitor(int api, AnnotationVisitor av) {
            super(api, av);
        }

        /**
         * Get a {@code desc} of the annotation.
         *
         * @param name the value name.
         * @param value the actual value, whose type must be {@link Byte}, {@link Boolean}, {@link
         *     Character}, {@link Short}, {@link Integer} , {@link Long}, {@link Float}, {@link Double},
         *     {@link String} or {@link Type} of {@link Type#OBJECT} or {@link Type#ARRAY} sort. This
         *     value can also be an array of byte, boolean, short, char, int, long, float or double values
         *     (this is equivalent to using {@link #visitArray} and visiting each array element in turn,
         *     but is more convenient).
         */
        @Override
        public void visit(String name, Object value) {
            if (name.equals("desc")) {
                description = (String) value;
            }
            av.visit(name, value);
        }
    }
}