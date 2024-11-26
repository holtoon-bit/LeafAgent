package leafagent.plugin;

import leafagent.info.BranchContainer;
import leafagent.info.TrunkContainer;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ActivityBranchVisitor extends BranchVisitor {

    public ActivityBranchVisitor(ClassVisitor classVisitor) {
        super(classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        cv.visitField(
                Opcodes.ACC_PRIVATE,
                "branchContainer",
                Type.getDescriptor(TrunkContainer.class),
                null,
                null
        );
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        return new ActivityLeafVisitor(Opcodes.ASM5, methodVisitor, access, className, name, desc);
    }
}