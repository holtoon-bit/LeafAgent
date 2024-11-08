package leafagent.old;

import com.android.build.api.transform.*;
import org.gradle.api.Project;
import org.gradle.api.artifacts.transform.TransformAction;
import org.gradle.api.artifacts.transform.TransformOutputs;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

abstract class LeafAgentTransformer implements TransformAction {
//        @Override
//    public String getName() {
//        return "LeafAgentTransformer";
//    }
//
//    @Override
//    public Set<QualifiedContent.ContentType> getInputTypes() {
//        return Collections.singleton(QualifiedContent.DefaultContentType.CLASSES);
//    }
//
//    @Override
//    public Set<? super QualifiedContent.Scope> getScopes() {
//        return Collections.singleton(QualifiedContent.Scope.PROJECT);
//    }
//
//    @Override
//    public boolean isIncremental() {
//        return true;
//    }

    @Override
    public void transform(TransformOutputs transformOutputs) {
        System.out.println("TRANSFORMER, YES?");
//        transformInvocation.getInputs().forEach((input) -> {
//            input.getDirectoryInputs().forEach((directoryInput) -> {
//                if (directoryInput.getFile().isDirectory()) {
//                    Arrays.stream(Objects.requireNonNull(directoryInput.getFile().listFiles())).toList().forEach((file) -> {
//                        String name = file.getName();
//                        if (name.endsWith(".class") && !(name.equals("R.class")) && !(name.equals("BuildConfig.class"))) {
//                            try {
//                                ClassReader reader = new ClassReader(new FileInputStream(file).readAllBytes());
//                                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
//                                ClassVisitor visitor = new LeafAgentVisitor(writer);
//                                reader.accept(visitor, ClassReader.EXPAND_FRAMES);
//
//                                byte[] code = writer.toByteArray();
//                                String classPath = file.getParentFile().getAbsolutePath() + File.separator + name;
//                                FileOutputStream fos = new FileOutputStream(classPath);
//                                fos.write(code);
//                                fos.close();
//                            } catch (IOException e) {
//                                System.out.println(e);
//                            }
//                        }
//                    });
//                }
//
//                File dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.getName(),
//                        directoryInput.getContentTypes(), directoryInput.getScopes(),
//                        Format.DIRECTORY);
//
//                try {
//                    FileUtils.copyDirectory(directoryInput.getFile(), dest);
//                } catch (IOException e) {
//                    System.out.println(e);
//                }
//            });
//
//            input.getJarInputs().forEach((jarInput) -> {
//                String jarName = jarInput.getName();
//                String md5Name = DigestUtils.md5Hex(jarInput.getFile().getAbsolutePath());
//                if (jarName.endsWith(".jar")) {
//                    jarName = jarName.substring(0, jarName.length() - 4);
//                }
//
//                File dest = transformInvocation.getOutputProvider().getContentLocation(jarName + md5Name,
//                        jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
//
//                try {
//                    FileUtils.copyFile(jarInput.getFile(), dest);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        });
    }

//    @Override
//    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
//        String variant = transformInvocation.getContext().getVariantName();
//
//        String androidJar = project.getPath() + "/platforms/" + android.getCompileSdkVersion() + "/android.jar";
//
//        ArrayList<File> externalDepsJars = new ArrayList<>();
//        ArrayList<File> externalDepsDirs = new ArrayList<>();
//        transformInvocation.getReferencedInputs().forEach((transformInput) -> {
//            transformInput.getJarInputs().stream().map(it -> externalDepsJars.add(it.getFile()));
//            transformInput.getDirectoryInputs().stream().map(it -> externalDepsDirs.add(it.getFile()));
//        });
//
//        try {
//            transformInvocation.getOutputProvider().deleteAll();
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//        File outputDir = transformInvocation.getOutputProvider().getContentLocation(
//                "classes",
//                getInputTypes(),
//                getScopes(),
//                Format.DIRECTORY
//        );
//
//        transformInvocation.getInputs().forEach((transformInput) -> {
//            transformInput.getDirectoryInputs().forEach((inputDirectory) -> {
//                File baseDir = inputDirectory.getFile();
//                ClassPool classPool = new ClassPool();
//                classPool.appendSystemPath();
//                try {
//                    classPool.insertClassPath(baseDir.getAbsolutePath());
//                    classPool.insertClassPath(androidJar);
//                } catch (NotFoundException e) {
//                    System.out.println(e);
//                }
//
//                externalDepsJars.forEach((it) -> {
//                    try {
//                        classPool.insertClassPath(it.getAbsolutePath());
//                    } catch (NotFoundException e) {
//                        System.out.println(e);
//                    }
//                });
//                externalDepsDirs.forEach((it) -> {
//                    try {
//                        classPool.insertClassPath(it.getAbsolutePath());
//                    } catch (NotFoundException e) {
//                        System.out.println(e);
//                    }
//                });
//
//                transformInputAssert(inputDirectory, outputDir, classPool);
//            });
//        });
//    }
//
//    private void transformInputAssert(DirectoryInput inputDirectory, File outputDir, ClassPool classPool) {
//        Arrays.stream(Objects.requireNonNull(
//                inputDirectory.getFile().listFiles()
//        )).forEach((originalClassFile) -> {
//            if (originalClassFile.getPath().endsWith(".class") && originalClassFile.isFile()) {
//                try {
//                    String className = originalClassFile.getName()
//                            .replace("/", ".")
//                            .replace("\\", ".")
//                            .replace(".class", "");
//                    CtClass clazz = classPool.get(className);
//                    transformClass(clazz);
//                    clazz.writeFile(outputDir.getAbsolutePath());
//                } catch (NotFoundException | CannotCompileException | IOException e) {
//                    System.out.println(e);
//                }
//            }
//        });
//    }
//
//    private void transformClass(CtClass clazz) throws CannotCompileException {
//        clazz.instrument(new ExprEditor() {
//            @Override
//            public void edit(FieldAccess f) {
//                try {
//                    if (!f.getField().hasAnnotation("leafagent.annotations.Leaf")) return;
//                } catch (NotFoundException e) {
//                    System.out.println(e);
//                }
//
//                try {
//                    if (f.isReader()) {
//                        f.replace("{@_ = @0.${f.fieldName};net.grandcentrix.gradle.logalot.runtime.LogALot.logFieldRead(\"${f.className}\",\"${f.fieldName}\", \"${clazz.name}\", ${f.lineNumber}, (@w)@_);}".replace("@", "${'$'}"));
//                    } else {
//                        f.replace("{@0.${f.fieldName} = @1;net.grandcentrix.gradle.logalot.runtime.LogALot.logFieldWrite(\"${f.className}\",\"${f.fieldName}\", \"${clazz.name}\", ${f.lineNumber}, (@w)@1);}".replace("@", "${'$'}"));
//                    }
//                } catch (CannotCompileException e) {
//                    System.out.println(e);
//                }
//            }
//        });
//
//        Arrays.stream(clazz.getDeclaredMethods()).forEach((method) -> {
//            if (!method.isEmpty() && method.hasAnnotation("leafagent.annotations.Leaf")) {
//                try {
//                    method.insertBefore(
//                            "{net.grandcentrix.gradle.logalot.runtime.LogALot.logMethodInvocation(\"${clazz.name}\",\"${method.name}\",@args);}"
//                                    .replace("@", "${'$'}")
//                    );
//                    method.insertAfter(
//                            "{net.grandcentrix.gradle.logalot.runtime.LogALot.logMethodExit(\"${clazz.name}\",\"${method.name}\",${method.returnType.name == \"void\"},(java.lang.Object)@_);}"
//                                    .replace("@", "${'$'}")
//                    );
//                    method.addCatch(
//                            "{net.grandcentrix.gradle.logalot.runtime.LogALot.logMethodThrows(\"${clazz.name}\",\"${method.name}\",@e);throw @e;}"
//                                    .trim().replace("@", "${'$'}"),
//                            clazz.getClassPool().get("java.lang.Throwable")
//                    );
//                } catch (CannotCompileException | NotFoundException e) {
//                    System.out.println(e);
//                }
//            }
//        });
//    }
}