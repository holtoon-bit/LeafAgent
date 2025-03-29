package leafagent.plugin;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;
import com.android.build.api.instrumentation.ClassContext;
import leafagent.annotations.Branch;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;

abstract public class AgentClassVisitorFactory implements AsmClassVisitorFactory<AgentClassVisitorFactory.LeafAgentVisitorParameters> {

    public abstract static class LeafAgentVisitorParameters implements InstrumentationParameters {}

    @Override
    public @NotNull ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor nextClassVisitor) {
        if (classContext.getCurrentClassData().getSuperClasses().contains("androidx.appcompat.app.AppCompatActivity")) {
            return new ActivityBranchVisitor(nextClassVisitor);
        }
        return new BranchVisitor(nextClassVisitor);
    }

    public boolean isInstrumentable(ClassData classData) {
        return classData.getClassAnnotations().contains(Branch.class.getName());
    }
}