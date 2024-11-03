package leafagent.plugin;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;
import com.android.build.api.instrumentation.ClassContext;
import leafagent.annotations.Branch;
import org.objectweb.asm.ClassVisitor;

abstract public class LeafClassVisitorFactory implements AsmClassVisitorFactory<LeafClassVisitorFactory.LeafVisitorParameters> {

    public abstract static class LeafVisitorParameters implements InstrumentationParameters {
//        @OutputDirectory
//        private Long invalidate = 0L;
//        @OutputDirectory
//        private Boolean debug = false;

        public void LeafVisitorParameters() {
            System.out.println("FACTORY SET");
//            this.invalidate = invalidate;
//            this.debug = debug;
        }

//        public void setDebug(Boolean debug) {
//            this.debug = debug;
//        }
//        @OutputDirectory
//        public Boolean getDebug() {
//            return debug;
//        }
//
//        public void setInvalidate(Long invalidate) {
//            this.invalidate = invalidate;
//        }
//        @OutputDirectory
//        public Long getInvalidate() {
//            return invalidate;
//        }
//
//        public void setTmpDir(File tmpDir) {
//            this.tmpDir = tmpDir;
//        }
//        @OutputDirectory
//        public File getTmpDir() {
//            return tmpDir;
//        }
    }

    @Override
    public ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor nextClassVisitor) {
        System.out.println("CREATE VISITOR");
        // If we return true from the isInstrumentable below, we should return a ClassVisitor that will inject our code for measuring the execution time
        System.out.println("=> " + classContext.getCurrentClassData().getClassName());
        if (isInstrumentable(classContext.getCurrentClassData())) {
            System.out.println("true => " + classContext.getCurrentClassData().getClassName());
            return new LeafAgentVisitor(nextClassVisitor);
        }
        return null;
    }

    public boolean isInstrumentable(ClassData classData) {
        return classData.getClassAnnotations().contains(Branch.class); // Determine if we are interested in instrumenting the given ClassData. For us it would mean a class annotated with @Dao
    }
}