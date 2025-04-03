package leafagent.plugin;

import org.gradle.api.tasks.Internal;
import org.objectweb.asm.*;

class BranchVisitor extends ClassVisitor {

    @Internal
    protected String className;
    @Internal
    protected String branchDescription = "";

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
        return new LeafVisitor(Opcodes.ASM5, methodVisitor, access, className, name, desc, branchDescription);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return new BranchAnnotationVisitor(api, super.visitAnnotation(descriptor, visible));
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    private class BranchAnnotationVisitor extends AnnotationVisitor {
        protected BranchAnnotationVisitor(int api, AnnotationVisitor av) {
            super(api, av);
        }

        @Override
        public void visit(String name, Object value) {
            if (name.equals("desc")) {
                branchDescription = (String) value;
            }
            av.visit(name, value);
        }
    }
}