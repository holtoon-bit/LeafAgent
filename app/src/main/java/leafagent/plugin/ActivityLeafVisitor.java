package leafagent.plugin;

import leafagent.info.ActivityRoot;
import leafagent.info.TrunkContainer;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ActivityLeafVisitor extends LeafVisitor {

    @Internal
    protected static final String COST_ONCREATE_NAME = "onCreate";
    @Internal
    protected static final String COST_ONDESTROY_NAME = "onDestroy";

    public ActivityLeafVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc) {
        super(api, mv, access, className, methodName, desc);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        if (COST_INIT_NAME.equals(methodName)) {
            intoInitActivity();
        }
        else if (COST_ONCREATE_NAME.equals(methodName)) {
            intoOnCreate();
        }
        else if (COST_ONDESTROY_NAME.equals(methodName)) {
            intoOnDestroy();
        }
    }

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

    private void intoOnCreate() {
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

    private void intoOnDestroy() {
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
