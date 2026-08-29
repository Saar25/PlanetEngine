# PlanetEngine

Maven project, Java 17, mixed Java + Kotlin in `src/main/java/` (no separate kotlin dir).

## Build & run

```sh
# build all
mvn compile

# build one module + deps
mvn compile -pl planet-examples -am

# run an example (use -am if it fails on missing modules)
mvn -pl planet-examples exec:java -Dexec.mainClass=org.saar.example.TactonicExampleKt
mvn -pl planet-examples exec:java -Dexec.mainClass=org.saar.example.terrain.TerrainApplicationKt
```

## Modules

| Module             | Role                                                                                                                                                                              |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `pe-math`          | Math: Vector3/4, Matrix3/4, noise, transform utilities. Depends on JOML 1.10.4, JProperty 1.1.6                                                                                   |
| `pe-lwjgl-binding` | LWJGL/GLFW/OpenGL/OpenAL bindings wrappers                                                                                                                                        |
| `pe-rhi`           | Render Hardware Interface: abstract rendering API. Kotlin-only module                                                                                                             |
| `pe-rhi-opengl`    | OpenGL implementation of pe-rhi. Depends on pe-rhi + lwjgl-opengl                                                                                                                 |
| `pe-rhi-vulkan`    | Vulkan implementation of pe-rhi. Depends on pe-rhi + lwjgl-vulkan + lwjgl-vma                                                                                                     |
| `pe-core`          | Engine + Application lifecycle, R3D/R2D renderers, deferred/forward pipelines, cameras, lights, post-processing, terrain system. Depends on pe-lwjgl-binding + reflections 0.10.2 |
| `pe-gui`           | GUI framework (UI components, styles, fonts). Kotlin-only module                                                                                                                  |
| `planet-examples`  | 30+ runnable examples. Depends on all other modules + kotlinx-coroutines 1.9.0                                                                                                    |

## Key conventions

- New code will always be written in kotlin

## Core Kotlin Principles

- Prefer immutability: Use `val` over `var`, `List` over `MutableList`, and `data class` for data models.
- Enforce strict null safety: Avoid using the `!!` operator entirely. Use `?.let`, `?:`, or smart casts.
- Use idiomatic syntax: Leverage scope functions (`apply`, `also`, `run`), destructuring, and expression bodies.

## Code Style & Formatting

- Code style: Strictly adhere to standard `ktlint` rules and `.editorconfig`.
- Extension functions: Place extension functions in dedicated files named after the type they extend (e.g.,
  `StringExt.kt`).

## Testing

Full testing conventions live in [docs/TESTING.md](docs/TESTING.md).