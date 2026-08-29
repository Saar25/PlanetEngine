plugins {
    kotlin("jvm")
    application
}

val exampleMainClass: String = providers.gradleProperty("mainClass")
    .getOrElse("org.saar.example.Example")

application {
    mainClass.set(exampleMainClass)
}

dependencies {
    api(project(":pe-lwjgl-binding"))
    api(project(":pe-math"))
    api(project(":pe-core"))
    api(project(":pe-gui"))
    api(project(":pe-rhi"))
    api(project(":pe-rhi-vulkan"))
    api(project(":pe-rhi-opengl"))
    api(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}

description = "planet-examples"
