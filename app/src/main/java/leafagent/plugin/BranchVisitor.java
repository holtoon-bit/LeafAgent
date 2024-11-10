package leafagent.plugin;

import leafagent.LeafInfo;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.LinkedList;

class BranchVisitor extends ClassVisitor {

    @Internal
    private String className;

    public BranchVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        methodVisitor = new LeafVisitor(Opcodes.ASM5, methodVisitor, access, className, name, desc);
        return methodVisitor;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}