package leafagent.plugin;

import com.android.build.api.instrumentation.FramesComputationMode;
import com.android.build.api.instrumentation.InstrumentationScope;
import com.android.build.api.variant.AndroidComponentsExtension;
import com.android.build.api.variant.Variant;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.AppliedPlugin;
import org.jetbrains.annotations.NotNull;

public class LeafPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPluginManager().withPlugin("com.android.application", new Action<AppliedPlugin>() {
            @Override
            public void execute(@NotNull AppliedPlugin appliedPlugin) {
                AndroidComponentsExtension androidComponentsExtension = project.getExtensions().getByType(AndroidComponentsExtension.class);
                androidComponentsExtension.onVariants(
                    androidComponentsExtension.selector().all(),
                    new Action<Variant>() {
                        @Override
                        public void execute(Variant variant) {
                            variant.getInstrumentation().transformClassesWith(
                                    AgentClassVisitorFactory.class,
                                    InstrumentationScope.PROJECT,
                                    (params) -> null
                            );
                            variant.getInstrumentation().setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS);
                        }
                });
            }
        });
    }
}
