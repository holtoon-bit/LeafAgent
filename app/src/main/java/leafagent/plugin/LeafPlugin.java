package leafagent.plugin;

import com.android.build.api.instrumentation.FramesComputationMode;
import com.android.build.api.instrumentation.InstrumentationScope;
import com.android.build.api.variant.*;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.AppliedPlugin;

public class LeafPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPluginManager().withPlugin("com.android.application", new Action<AppliedPlugin>() {
            @Override
            public void execute(AppliedPlugin appliedPlugin) {
                AndroidComponentsExtension androidComponentsExtension = project.getExtensions().getByType(AndroidComponentsExtension.class);
                androidComponentsExtension.onVariants(
                        androidComponentsExtension.selector().all(),
                        new Action<Variant>() {
                    @Override
                    public void execute(Variant variant) {
                        variant.getInstrumentation().transformClassesWith(
                                LeafClassVisitorFactory.class,
                                InstrumentationScope.PROJECT,
                                (params) -> {
//                                    project.getExtensions().add("leafPluginSettings124", LeafPluginExtension.class);
//                                    LeafPluginExtension extension = (LeafPluginExtension) project.getExtensions().findByName("leafPluginSettings124");
//                                    params.setInvalidate(System.currentTimeMillis());
//                                    params.setTmpDir(new File(extension.tmpDir));
                                    return null;
                                }
                        );
                        variant.getInstrumentation().setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS);
                    }
                });
            }
        });

//        File buildScript = project.getBuildFile();
//        path = buildScript.getParent();
//        project.getTasks().create("leafTransform", BytecodeTransformerTask.class);
//        project.getTasks().getAt("preBuild").dependsOn("leafTransform");
    }

    public static class LeafPluginExtension {
        String tmpDir;
    }
}
