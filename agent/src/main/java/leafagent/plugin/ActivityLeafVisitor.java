package leafagent.plugin;

import leafagent.info.BaseContainer;
import leafagent.info.BranchContainer;
import leafagent.info.LeafContainer;
import leafagent.info.TrunkContainer;
import leafagent.utils.JsonWriter;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * {@link LeafVisitor LeafVisitor} for the methods from activity.
 */
public class ActivityLeafVisitor extends LeafVisitor {
    @Internal
    public static final String COST_CREATE_NAME = "onCreate";
    @Internal
    public static final String COST_START_NAME = "onStart";
    @Internal
    public static final String COST_STOP_NAME = "onStop";
    @Internal
    public static final String COST_DESTROY_NAME = "onDestroy";

    @Internal
    public final String activityName;

    public ActivityLeafVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc, String branchDescription) {
        super(api, mv, access, className, methodName, desc, branchDescription);
        if (methodName.equals(COST_INIT_NAME)) {
            this.description = branchDescription;
        }
        activityName = className.substring(className.lastIndexOf("/")+1);
    }

    /**
     * Start editing the bytecode of the method.
     */
    @Override
    public void visitCode() {
        switch (methodName) {
            case COST_START_NAME, COST_STOP_NAME -> afterBranchStart();
            case COST_CREATE_NAME -> intoOnCreateActivity();
            case COST_INIT_NAME -> intoInitActivity();
            default -> super.visitCode();
        }
    }

    /**
     * Add methods to start observation of {@code init} this class.
     */
    private void intoInitActivity() {
        addTrunk(methodName, className);
        addTrunkStartTime(methodName, className);
    }

    /**
     * Create an object of {@link TrunkContainer TrunkContainer} in this class.
     *
     * @param activityName activity name.
     * @param className class name.
     */
    private void addTrunk(String activityName, String className) {
        mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(TrunkContainer.class));
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(activityName);
        mv.visitLdcInsn(className);
        mv.visitLdcInsn(description);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                Type.getInternalName(TrunkContainer.class),
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
     * Calling a {@link TrunkContainer#startTime() TrunkContainer.startTime()} to start this class.
     *
     * @param activityName activity name.
     * @param className class name.
     */
    private void addTrunkStartTime(String activityName, String className) {
        mv.visitLdcInsn(activityName);
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
     * Add methods to start observation of {@code onCreate}.
     */
    private void intoOnCreateActivity() {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "android/content/ContextWrapper",
                "getFilesDir",
                "()Ljava/io/File;",
                false
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/File",
                "getPath",
                "()"+Type.getDescriptor(String.class),
                false
        );
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(JsonWriter.class),
                "setProjectPath",
                "("+Type.getDescriptor(String.class)+")V",
                false
        );
        afterBranchStart();
    }

    /**
     * Add methods to start observation of method in this class.
     */
    public void afterBranchStart() {
        addBranch(methodName, className);
        addBranchStartTime(methodName, className);
    }

    /**
     * Create an object of {@link BranchContainer BranchContainer} in this method.
     *
     * @param leafName method name.
     * @param className class name.
     */
    protected void addBranch(String leafName, String className) {
        mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(BranchContainer.class));
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(leafName);
        mv.visitLdcInsn(className);
        mv.visitLdcInsn(description);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                Type.getInternalName(BranchContainer.class),
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
     * Calling a {@link BranchContainer#startTime() BranchContainer.startTime()} in this method.
     *
     * @param leafName method name.
     * @param className class name.
     */
    protected void addBranchStartTime(String leafName, String className) {
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
     * Calling a {@link LeafContainer#endTime() LeafContainer.endTime()} of destroy this class.
     */
    private void beforeDestroyReturn() {
        addLeafEndTime(activityName, className);
    }

    /**
     * Finish editing the bytecode of the method.
     *
     * @param opcode the opcode of the instruction to be visited (see {@link Opcodes Opcodes}.
     */
    @Override
    public void visitInsn(int opcode) {
        if (opcode <= Opcodes.RETURN && opcode >= Opcodes.IRETURN) {
            if (methodName.equals(COST_START_NAME)
                    || methodName.equals(COST_STOP_NAME)
                    || methodName.equals(COST_CREATE_NAME)) {
                beforeReturn();
            } else if (methodName.equals(COST_DESTROY_NAME)) {
                beforeDestroyReturn();
            } else {
                super.visitInsn(opcode);
            }
        }
        mv.visitInsn(opcode);
    }
}
