plugins {
    kotlin("jvm")
}

val lwjglNatives = "natives-linux"

dependencies {
    api(project(":pe-rhi"))
    api(libs.org.lwjgl.lwjgl.opengl)
    runtimeOnly(variantOf(libs.org.lwjgl.lwjgl.opengl) { classifier(lwjglNatives) })
}

description = "pe-rhi-opengl"
