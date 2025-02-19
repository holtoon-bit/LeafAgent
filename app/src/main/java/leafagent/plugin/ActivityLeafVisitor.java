package leafagent.plugin;

import leafagent.info.ActivityRoot;
import leafagent.info.BaseContainer;
import leafagent.info.BranchContainer;
import leafagent.info.TrunkContainer;
import leafagent.utils.JsonWriter;
import org.gradle.api.tasks.Internal;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ActivityLeafVisitor extends LeafVisitor {

    @Internal
    public static final String COST_CREATE_NAME = "onCreate";
    @Internal
    public static final String COST_START_NAME = "onStart";
    @Internal
    public static final String COST_STOP_NAME = "onStop";

    public ActivityLeafVisitor(int api, MethodVisitor mv, int access, String className, String methodName, String desc) {
        super(api, mv, access, className, methodName, desc);
    }

    @Override
    public void visitCode() {
        if (COST_INIT_NAME.equals(methodName)) {
            return;
        }
        if (COST_START_NAME.equals(methodName) || COST_STOP_NAME.equals(methodName)) {
            afterStart();
        } else if (COST_CREATE_NAME.equals(methodName)) {
            intoInitActivity();
        }
        super.visitCode();
    }

    private void intoInitActivity() {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "android/content/ContextWrapper",
                "getFilesDir",
                "()Ljava/io/File;",
                false
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/File",
                "getPath",
                "()"+Type.getDescriptor(String.class),
                false
        );
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(JsonWriter.class),
                "setProjectPath",
                "("+Type.getDescriptor(String.class)+")V",
                false
        );
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitLdcInsn(className);
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(ActivityRoot.class),
                "createChild",
                "("+Type.getDescriptor(String.class)+")"+Type.getDescriptor(TrunkContainer.class),
                false
        );
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(CreatedContainers.class),
                "addNew",
                "("+Type.getDescriptor(BaseContainer.class)+")V"
        );
        mv.visitLdcInsn(className);
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(CreatedContainers.class),
                "get",
                "("+Type.getDescriptor(String.class)+")"+Type.getDescriptor(BaseContainer.class)
        );
        mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(BaseContainer.class),
                "startTime",
                "()V"
        );
        afterStart();
    }

    @Override
    public void visitInsn(int opcode) {
        if (COST_INIT_NAME.equals(methodName)) {
            mv.visitInsn(opcode);
            return;
        }
        if (opcode == Opcodes.RETURN &&
                (COST_START_NAME.equals(methodName) || COST_STOP_NAME.equals(methodName) || COST_CREATE_NAME.equals(methodName))) {
            beforeReturn();
        }
        super.visitInsn(opcode);
    }
}
