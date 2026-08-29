plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":pe-math"))
    api(project(":pe-lwjgl-binding"))
    api(project(":pe-core"))
}

description = "pe-gui"
