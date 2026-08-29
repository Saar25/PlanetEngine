plugins {
    kotlin("jvm")
}

fun DependencyHandler.lwjglModule(
    dependency: Provider<MinimalExternalModuleDependency>,
    classifier: String = rootProject.extra.get("lwjgl-natives") as String
) {
    api(dependency)
    runtimeOnly(variantOf(dependency) { classifier(classifier) })
}

dependencies {
    api(project(":pe-math"))
    api(project(":pe-rhi"))
    api(project(":pe-rhi-opengl"))

    lwjglModule(libs.org.lwjgl.lwjgl.core)
    lwjglModule(libs.org.lwjgl.lwjgl.glfw)
    lwjglModule(libs.org.lwjgl.lwjgl.opengl)
    lwjglModule(libs.org.lwjgl.lwjgl.openal)
    lwjglModule(libs.org.lwjgl.lwjgl.assimp)
    lwjglModule(libs.org.lwjgl.lwjgl.stb)
    lwjglModule(libs.org.lwjgl.lwjgl.shaderc)
}

description = "pe-lwjgl-binding"
