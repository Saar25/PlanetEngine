plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":pe-lwjgl-binding"))
    api(project(":pe-rhi"))
    api(project(":pe-rhi-opengl"))
}

description = "pe-core"
