plugins {
    kotlin("jvm") version "2.4.0" apply false
}

val osName = System.getProperty("os.name").lowercase()
val osArch = System.getProperty("os.arch").lowercase()

extra.set(
    "lwjgl-natives",
    when {
        osName.contains("win") -> if (osArch.contains("64")) "natives-windows" else "natives-windows-x86"
        osName.contains("mac") -> if (osArch.contains("aarch64") || osArch.contains("arm")) "natives-macos-arm64" else "natives-macos"
        osName.contains("nux") || osName.contains("nix") -> if (osArch.contains("aarch64") || osArch.contains("arm")) "natives-linux-arm64" else "natives-linux"
        else -> error("Unsupported OS: $osName")
    }
)

subprojects {
    apply(plugin = "java-library")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        mavenCentral()
        maven("https://central.sonatype.com/repository/maven-snapshots/") {
            mavenContent { snapshotsOnly() }
        }
        maven("https://jitpack.io")
    }

    dependencies {
        "api"(kotlin("reflect"))
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
