plugins {
    kotlin("jvm")
}

dependencies {
    api(libs.org.joml.joml)
    api(libs.org.joml.joml.primitives)
    api(libs.com.github.saar25.jproperty)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

description = "pe-math"
