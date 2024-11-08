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
        System.out.println("<Trace with LeafAgent> ~ " + classContext.getCurrentClassData().getClassName());
        return new LeafAgentVisitor(nextClassVisitor);
    }

    public boolean isInstrumentable(ClassData classData) {
        return classData.getClassAnnotations().contains(Branch.class.getName());
    }
}