package leafagent.plugin;

import leafagent.info.ActivityRoot;
import leafagent.info.BranchContainer;
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

    public ActivityLeafVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc) {
        super(api, mv, access, className, methodName, desc);
    }

    @Override
    public void visitCode() {
        if (isInjected || COST_START_NAME.equals(methodName) || COST_STOP_NAME.equals(methodName)) {
            afterStart();
        }
        else if (COST_CREATE_NAME.equals(methodName)) {
            intoInitActivity();
        }
    }

    // Write an action on starting the Activity
    // and create the BranchContainer
    private void intoInitActivity() {
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
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitLdcInsn(className);
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(ActivityRoot.class),
                "createChild",
                "("+Type.getDescriptor(String.class)+")"+Type.getDescriptor(TrunkContainer.class),
                false
        );
        mv.visitFieldInsn(
                Opcodes.PUTFIELD,
                className,
                "trunkContainer",
                Type.getDescriptor(TrunkContainer.class)
        );
        super.intoInit();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(
                Opcodes.GETFIELD,
                className,
                "trunkContainer",
                Type.getDescriptor(TrunkContainer.class)
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(TrunkContainer.class),
                "startTime",
                "()V"
        );
    }

    // set the Start Time for the Branch
    private void intoOnStart() {
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

    private void intoOnStop() {
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
                "endTime",
                "()V"
        );
    }
}
