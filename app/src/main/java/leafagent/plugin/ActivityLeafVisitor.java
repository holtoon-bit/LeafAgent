package leafagent.plugin;

import leafagent.info.BaseContainer;
import leafagent.info.TrunkContainer;
import leafagent.utils.JsonWriter;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ActivityLeafVisitor extends LeafVisitor {

    @Internal
    public static final String COST_CREATE_NAME = "onCreate";
    @Internal
    public static final String COST_START_NAME = "onStart";
    @Internal
    public static final String COST_STOP_NAME = "onStop";
    @Internal
    public static final String COST_DESTROY_NAME = "onDestroy";

    public ActivityLeafVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc, String branchDescription) {
        super(api, mv, access, className, methodName, desc, branchDescription);
        if (methodName.equals(COST_INIT_NAME)) {
            this.description = branchDescription;
        }
    }

    @Override
    public void visitCode() {
        switch (methodName) {
            case COST_START_NAME, COST_STOP_NAME -> afterStart();
            case COST_CREATE_NAME -> intoOnCreateActivity();
            case COST_INIT_NAME -> intoInitActivity();
            default -> super.visitCode();
        }
    }

    private void intoInitActivity() {
        addTrunk(className);
        addTrunkStartTime(className);
    }

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
        afterStart();
    }

    private void addTrunk(String className) {
        mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(TrunkContainer.class));
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(className.substring(className.lastIndexOf("/")+1));
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

    private void addTrunkStartTime(String className) {
        mv.visitLdcInsn(className.substring(className.lastIndexOf("/")+1));
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

    private void beforeDestroyReturn() {
        addLeafEndTime(className, className);
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode == Opcodes.RETURN && !methodName.equals(COST_INIT_NAME)) {
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
