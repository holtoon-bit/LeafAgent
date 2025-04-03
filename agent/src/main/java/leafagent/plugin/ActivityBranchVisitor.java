package leafagent.plugin;

import org.gradle.api.tasks.Internal;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ActivityBranchVisitor extends BranchVisitor {
    @Internal
    protected final String COST_APP_COMPAT_ACTIVITY_NAME = "androidx/appcompat/app/AppCompatActivity";

    @Internal
    private boolean isOnStartCreated = false;
    @Internal
    private boolean isOnStopCreated = false;
    @Internal
    private boolean isOnDestroyCreated = false;
    @Internal
    private boolean isInitCreated = false;

    public ActivityBranchVisitor(ClassVisitor classVisitor) {
        super(classVisitor);
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
            case (ActivityLeafVisitor.COST_DESTROY_NAME):
                isOnDestroyCreated = true;
                break;
            case (ActivityLeafVisitor.COST_INIT_NAME):
                isInitCreated = true;
                break;
        }
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new ActivityLeafVisitor(Opcodes.ASM5, methodVisitor, access, className, name, desc, branchDescription);
    }

    @Override
    public void visitEnd() {
        if (!isOnStartCreated) {
            createActivitySuperFunction(Opcodes.ACC_PROTECTED, COST_APP_COMPAT_ACTIVITY_NAME, ActivityLeafVisitor.COST_START_NAME, "()V", null, null);
        }
        if (!isOnStopCreated) {
            createActivitySuperFunction(Opcodes.ACC_PROTECTED, COST_APP_COMPAT_ACTIVITY_NAME, ActivityLeafVisitor.COST_STOP_NAME, "()V", null, null);
        }
        if (!isOnDestroyCreated) {
            createActivitySuperFunction(Opcodes.ACC_PROTECTED, COST_APP_COMPAT_ACTIVITY_NAME, ActivityLeafVisitor.COST_DESTROY_NAME, "()V", null, null);
        }
        if (!isInitCreated) {
            createActivitySuperFunction(Opcodes.ACC_PUBLIC, "", ActivityLeafVisitor.COST_INIT_NAME, "()V", null, null);
        }
        super.visitEnd();
    }

    private void createActivitySuperFunction(int access, String owner, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor smv = super.visitMethod(access, name, desc, signature, exceptions);
        smv = new ActivityLeafVisitor(Opcodes.ASM5, smv, access, className, name, desc, branchDescription);
        smv.visitCode();
        smv.visitVarInsn(Opcodes.ALOAD, 0);
        smv.visitMethodInsn(Opcodes.INVOKESPECIAL, owner, name, desc, false);
        smv.visitInsn(Opcodes.RETURN);
        smv.visitEnd();
    }
}