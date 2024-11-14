package leafagent.plugin;

import leafagent.info.TrunkContainer;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class BranchVisitor extends ClassVisitor {

    @Internal
    protected String className;

    public BranchVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
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
        return new LeafVisitor(Opcodes.ASM5, methodVisitor, access, className, name, desc);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}