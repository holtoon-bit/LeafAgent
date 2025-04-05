package leafagent.plugin;

import org.gradle.api.tasks.Internal;
import org.objectweb.asm.*;

/**
 * Base {@link ClassVisitor ClassVisitor} for each class. Using to editing the bytecode of a class.
 */
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

    /**
     * Create {@link MethodVisitor MethodVisitor} to editing a methods bytecode.
     *
     * @param access the method's access flags (see {@link Opcodes}). This parameter also indicates if
     *     the method is synthetic and/or deprecated.
     * @param name the method's name.
     * @param desc the method's descriptor (see {@link Type}).
     * @param signature the method's signature. May be {@literal null} if the method parameters,
     *     return type and exceptions do not use generic types.
     * @param exceptions the internal names of the method's exception classes (see {@link
     *     Type#getInternalName()}). May be {@literal null}.
     * @return {@link LeafVisitor LeafVisitor}
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return new LeafVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc, signature, exceptions),
                                access, className, name, desc, branchDescription);
    }

    /**
     * Check class annotations (see {@link BranchAnnotationVisitor#visit(String, Object) BranchAnnotationVisitor.visit()}).
     *
     * @param descriptor the class descriptor of the annotation class.
     * @param visible {@literal true} if the annotation is visible at runtime.
     * @return {@link BranchAnnotationVisitor BranchAnnotationVisitor}
     */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return new BranchAnnotationVisitor(api, super.visitAnnotation(descriptor, visible));
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    /**
     * Using to get and check class annotations.
     */
    private class BranchAnnotationVisitor extends AnnotationVisitor {
        protected BranchAnnotationVisitor(int api, AnnotationVisitor av) {
            super(api, av);
        }

        /**
         * Get {@code desc} value for {@link leafagent.annotations.Leaf Leaf} annotation.
         *
         * @param name the value name.
         * @param value the actual value, whose type must be {@link Byte}, {@link Boolean}, {@link
         *     Character}, {@link Short}, {@link Integer} , {@link Long}, {@link Float}, {@link Double},
         *     {@link String} or {@link Type} of {@link Type#OBJECT} or {@link Type#ARRAY} sort. This
         *     value can also be an array of byte, boolean, short, char, int, long, float or double values
         *     (this is equivalent to using {@link #visitArray} and visiting each array element in turn,
         *     but is more convenient).
         */
        @Override
        public void visit(String name, Object value) {
            if (name.equals("desc")) {
                branchDescription = (String) value;
            }
            av.visit(name, value);
        }
    }
}