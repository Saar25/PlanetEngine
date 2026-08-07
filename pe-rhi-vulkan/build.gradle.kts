plugins {
    kotlin("jvm")
}

val lwjglNatives = "natives-linux"

dependencies {
    api(project(":pe-rhi"))
    api(libs.org.lwjgl.lwjgl.vulkan)
    api(libs.org.lwjgl.lwjgl.vma)
    runtimeOnly(variantOf(libs.org.lwjgl.lwjgl.vma) { classifier(lwjglNatives) })
}

description = "pe-rhi-vulkan"
