package leafagent.plugin;

import leafagent.annotations.Leaf;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class LeafAgentVisitor extends ClassVisitor {

    @Internal
    private String mClassName;

    public LeafAgentVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.mClassName = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        methodVisitor = new LeafAgentMethodVisitor(Opcodes.ASM5, methodVisitor, access, mClassName, name, desc);
        return methodVisitor;
    }
}