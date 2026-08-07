plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

fun DependencyHandler.lwjglModule(
    dependency: Provider<MinimalExternalModuleDependency>,
    classifier: String = "natives-linux"
) {
    add("api", dependency)
    add("runtimeOnly", variantOf(dependency) { classifier(classifier) })
}
