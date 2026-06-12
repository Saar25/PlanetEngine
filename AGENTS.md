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

## No tests

Zero test directories (`src/test`) in any module.

## Modules

| Module | Role |
|---|---|
| `pe-math` | Math: Vector3/4, Matrix3/4, noise, transform utilities. Depends on JOML 1.10.4, JProperty 1.1.6 |
| `pe-lwjgl-binding` | LWJGL/GLFW/OpenGL/OpenAL bindings wrappers |
| `pe-core` | Engine + Application lifecycle, R3D/R2D renderers, deferred/forward pipelines, cameras, lights, post-processing, terrain system. Depends on pe-lwjgl-binding + reflections 0.10.2 |
| `pe-gui` | GUI framework (UI components, styles, fonts). Kotlin-only module |
| `planet-examples` | 30+ runnable examples. Depends on all other modules + kotlinx-coroutines 1.9.0 |

## Key conventions

- **Vector3 is a static utility** wrapping JOML's `Vector3f`. Has `create()`, `of(x,y,z)`, `add/sub/mul/div/cross/normalize` — all return new `Vector3f` instances. Instance `.div(float)` etc. mutate in place.
- **R3D renderers** use annotation-driven reflection: `@UniformProperty(UniformTrigger.PER_INSTANCE)` on uniform fields, `@ShaderProperty` on shader fields, on `RendererPrototype<T>` subclasses.
