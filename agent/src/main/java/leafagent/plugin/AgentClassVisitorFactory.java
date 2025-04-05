package leafagent.plugin;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;
import com.android.build.api.instrumentation.ClassContext;
import leafagent.annotations.Branch;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;

/**
 * Create the visitors class to edit the bytecode.
 */
abstract public class AgentClassVisitorFactory implements AsmClassVisitorFactory<AgentClassVisitorFactory.LeafAgentVisitorParameters> {

    public abstract static class LeafAgentVisitorParameters implements InstrumentationParameters {}

    /**
     * Determine which visitor to use ({@link BranchVisitor BranchVisitor} or {@link ActivityBranchVisitor ActivityBranchVisitor}).
     *
     * @param classContext {@link ClassContext ClassContext}
     * @param nextClassVisitor {@link ClassVisitor ClassVisitor}
     * @return {@link BranchVisitor BranchVisitor} / {@link ActivityBranchVisitor ActivityBranchVisitor}
     */
    @Override
    public @NotNull ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor nextClassVisitor) {
        if (classContext.getCurrentClassData().getSuperClasses().contains("androidx.appcompat.app.AppCompatActivity")) {
            return new ActivityBranchVisitor(nextClassVisitor);
        }
        return new BranchVisitor(nextClassVisitor);
    }

    /**
     * Checks whether the class has an annotation {@link leafagent.annotations.Branch @Branch}.
     *
     * @param classData {@link ClassData ClassData}
     * @return {@code boolean}
     */
    public boolean isInstrumentable(ClassData classData) {
        return classData.getClassAnnotations().contains(Branch.class.getName());
    }
}