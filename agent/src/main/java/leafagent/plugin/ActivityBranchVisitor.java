package leafagent.plugin;

import org.gradle.api.tasks.Internal;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * {@link BranchVisitor BranchVisitor} for the activity class.
 */
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

    /**
     * Creating {@link MethodVisitor MethodVisitor} to edit the bytecode of each method.
     * And determine the availability of necessary methods for tracking the activity status.
     *
     * @param access the method's access flags (see {@link Opcodes}). This parameter also indicates if
     *     the method is synthetic and/or deprecated.
     * @param name the method's name.
     * @param desc the method's descriptor (see {@link org.objectweb.asm.Type Type}).
     * @param signature the method's signature. May be {@literal null} if the method parameters,
     *     return type and exceptions do not use generic types.
     * @param exceptions the internal names of the method's exception classes (see {@link
     *     org.objectweb.asm.Type#getInternalName() getInternalName()}). May be {@literal null}.
     * @return {@link ActivityLeafVisitor ActivityLeafVisitor}
     */
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
        return new ActivityLeafVisitor(Opcodes.ASM5, cv.visitMethod(access, name, desc, signature, exceptions),
                                        access, className, name, desc, branchDescription);
    }

    /**
     * Create missing methods, that were not received using {@link #visitMethod(int, String, String, String, String[]) visitMethod()} method.
     * <br>
     * And call {@link ClassVisitor#visitEnd() super.visitEnd()}.
     */
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

    /**
     * Process of creating a new method in this class.
     *
     * @param access the method's access flags (see {@link Opcodes}). This parameter also indicates if
     *     the method is synthetic and/or deprecated.
     * @param name the method's name.
     * @param desc the method's descriptor (see {@link org.objectweb.asm.Type Type}).
     * @param signature the method's signature. May be {@literal null} if the method parameters,
     *     return type and exceptions do not use generic types.
     * @param exceptions the internal names of the method's exception classes (see {@link
     *     org.objectweb.asm.Type#getInternalName() getInternalName()}). May be {@literal null}.
     */
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