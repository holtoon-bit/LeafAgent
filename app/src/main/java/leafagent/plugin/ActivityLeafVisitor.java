package leafagent.plugin;

import leafagent.info.ActivityRoot;
import leafagent.info.TrunkContainer;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ActivityLeafVisitor extends LeafVisitor {

    @Internal
    protected static final String COST_START_NAME = "onStart";
    @Internal
    protected static final String COST_STOP_NAME = "onStop";

    public ActivityLeafVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc) {
        super(api, mv, access, className, methodName, desc);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println(isInjected);
        if (COST_INIT_NAME.equals(methodName)) {
        }
        else if (COST_START_NAME.equals(methodName)) {
            intoInitActivity();
            intoOnStart();
        }
        else if (COST_STOP_NAME.equals(methodName)) {
            intoOnStop();
        }
    }

    // Write an action on starting the Activity
    // and create the BranchContainer
    private void intoInitActivity() {
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
                "branchContainer",
                Type.getDescriptor(TrunkContainer.class)
        );
    }

    // set the Start Time for the Branch
    private void intoOnStart() {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(
                Opcodes.GETFIELD,
                className,
                "branchContainer",
                Type.getDescriptor(TrunkContainer.class)
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(TrunkContainer.class),
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
                Type.getDescriptor(TrunkContainer.class)
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(TrunkContainer.class),
                "endTime",
                "()V"
        );
    }
}
