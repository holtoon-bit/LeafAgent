package leafagent.plugin;

import leafagent.info.TrunkContainer;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ActivityBranchVisitor extends BranchVisitor {
    @Internal
    private boolean isOnStartCreated = false;
    @Internal
    private boolean isOnStopCreated = false;

    public ActivityBranchVisitor(ClassVisitor classVisitor) {
        super(classVisitor);
//        cv.visitField(
//                Opcodes.ACC_PRIVATE,
//                "trunkContainer",
//                Type.getDescriptor(TrunkContainer.class),
//                null,
//                null
//        );
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        switch (name) {
            case (ActivityLeafVisitor.COST_START_NAME):
                isOnStartCreated = true;
                break;
            case (ActivityLeafVisitor.COST_STOP_NAME):
                isOnStopCreated = true;
                break;
        }
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new ActivityLeafVisitor(Opcodes.ASM5, methodVisitor, access, className, name, desc);
    }

    @Override
    public void visitEnd() {
        if (!isOnStartCreated) {
            createActivitySuperFunction(Opcodes.ACC_PROTECTED, ActivityLeafVisitor.COST_START_NAME, "()V", null, null);
        }
        if (!isOnStopCreated) {
            createActivitySuperFunction(Opcodes.ACC_PROTECTED, ActivityLeafVisitor.COST_STOP_NAME, "()V", null, null);
        }
        super.visitEnd();
    }

    private void createActivitySuperFunction(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor smv = super.visitMethod(access, name, desc, signature, exceptions);
        smv = new ActivityLeafVisitor(Opcodes.ASM5, smv, access, className, name, desc);
        smv.visitCode();
        smv.visitVarInsn(Opcodes.ALOAD, 0);
        smv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                "androidx/appcompat/app/AppCompatActivity",
                name,
                desc,
                false
        );
        smv.visitInsn(Opcodes.RETURN);
        smv.visitEnd();
    }
}