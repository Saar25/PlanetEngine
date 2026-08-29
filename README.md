# 🌍 PlanetEngine

An OpenGL-based game engine built on top of LWJGL  
Written in Java and Kotlin and gradually transforming to only Kotlin

## Table of Contents

- [Getting Started](#getting-started)
- [Modules](#modules)
- [LWJGL Binding](#lwjgl-binding)
- [Core Engine](#core-engine)
- [Graphical User Interface](#graphical-user-interface)
- [Entity Component System](#entity-component-system)
- [Example Classes](#example-classes)
- [License](#license)

## 🚀 Getting Started

### Prerequisites

- Java 17+

### Build

```bash
./gradlew build
```

### Run an Example

```bash
./gradlew :planet-examples:run -PmainClass=org.saar.example.terrain.TerrainApplicationKt
```

Replace the class with any of the available examples.

## Modules

- `pe-math` - Vector and matrix math utilities.
- `pe-lwjgl-binding` - Low-level LWJGL and OpenGL bindings.
- `pe-core` - High-level rendering pipelines.
- `pe-gui` - UI system with components and text rendering.
- `planet-examples` - A collection of runnable engine examples.

## LWJGL Binding
All OpenGL and LWJGL objects are wrapped in strongly typed Java classes, providing encapsulation, safety, and improved usability.  
These bindings are located in the pe-lwjgl-binding module.

```java
// org.saar.example.Example.java

// Create the window
final Window window = Window.create("Lwjgl", 700, 500, true);

// Create a vertex array object and a vertex buffer object
final Vao vao = Vao.create();
final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);

// Allocating and storing data in vbo object
vbo.allocateFloat(18);
vbo.storeFloat(0, new float[]{
        -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f,
        +0.0f, +0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
        +0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f});

vao.loadVbo(vbo,
        Attribute.of(0, 2, DataType.FLOAT, false),
        Attribute.of(1, 3, DataType.FLOAT, false),
        Attribute.of(2, 1, DataType.FLOAT, false));

// Create a simple shaders program
final ShadersProgram shadersProgram = ShadersProgram.create(
        Shader.createVertex("/vertex.glsl"),
        Shader.createFragment("/fragment.glsl"));
shadersProgram.bindAttribute(0, "in_position");

// Bind our objects
shadersProgram.bind();

vao.bind();
vao.enableAttributes();

// Game loop
final Keyboard keyboard = window.getKeyboard();
while (window.isOpen() && !keyboard.isKeyPressed('E')) {

    // Render a triangle
    GlRendering.drawArrays(RenderMode.TRIANGLES, 0, 3);

    // Update window
    window.update(true);
    window.pollEvents();
}

// Free all memory
window.destroy();
```

## Core Engine
While direct management of VAOs and VBOs is supported, it's often more convenient and safer to use the high-level abstractions provided by the engine.  

```java
// org.saar.example.renderer.RendererExample.java

// Create the vertices and indices for a 2d mesh
final float s = 0.7f;
final int[] indices = {0, 1, 2, 0, 2, 3};
final Vertex2D[] vertices = {
        R2D.vertex(Vector2.of(-s, -s), Vector3.of(+0.0f, +0.0f, +0.5f)),
        R2D.vertex(Vector2.of(-s, +s), Vector3.of(+0.0f, +1.0f, +0.5f)),
        R2D.vertex(Vector2.of(+s, +s), Vector3.of(+1.0f, +1.0f, +0.5f)),
        R2D.vertex(Vector2.of(+s, -s), Vector3.of(+1.0f, +0.0f, +0.5f))};

// Create the mesh, model, and renderer
final Mesh2D mesh = Mesh2D.load(vertices, indices);
final Model2D model = new Model2D(mesh);
final Renderer2D renderer = new Renderer2D();

// Render the model
renderer.render(new ForwardRenderContext(camera), model);
```

The rendering pipeline consists of some primary interfaces:

### Vertex

Defines a vertex of the mesh

```kotlin
interface Vertex3D : Vertex {
    val position3f: Vector3fc
    val normal3f: Vector3fc
    val color3f: Vector3fc
}
```

### Instance

Defines an instance (used for instance rendering)

```kotlin
interface Instance3D : Instance {
    val transform: Transform
}
```

### Mesh

Defines the draw method used to draw a mesh

```kotlin
interface Mesh {
    fun draw()
    fun delete()
}
```

### Model

Defines the mesh and additional attributes used to render the model, such as texture or transform

```kotlin
class Model3D(override val mesh: Mesh3D, val transform: SimpleTransform) : Model {
    constructor(mesh: Mesh3D) : this(mesh, SimpleTransform())
}
```

### Node

Base class for complex objects in the scene
Usually holds the model and a renderer, and has at least one render method

```kotlin
val cubeModel = buildCubeModel()
val cube = Node3D(cubeModel)

cube.renderForward(RenderContext())

```

### Renderer

Renderers compose custom ShaderLink objects, and use them to connect the model data to the shader

```kotlin
// org.saar.core.common.r3d.DeferredRenderer3D.kt
private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

override val shaders = arrayOf(
    Shader.createVertex(GlslVersion.V400, 
        ShaderCode.loadSource("/shaders/r3d/vertex.glsl")),
    Shader.createFragment(GlslVersion.V400, 
        ShaderCode.loadSource("/shaders/r3d/fragmentDeferred.glsl"))
)

override val vertexAttributes = arrayOf("in_position", "in_color", "in_transformation")
```

And then inside the renderer:

```kotlin
override fun render(context: DeferredRenderContext, models: Iterable<Model3D>) {
    models.forEach { model ->
        val p = context.camera.projection.matrix
        val v = context.camera.viewMatrix
        val m = model.transform.transformationMatrix
        val mvp = p.mul(v, Matrix4.create()).mul(m)

        this.shadersLink.mvpMatrixUniform.value = mvp

        this.uniformsLoader.load()

        model.mesh.draw()
    }
}
```

### RenderGraph

Render graphs are used to combine multiple render passes in an elegant way

```kotlin
val renderGraph = RenderGraph(
    renderNode
        .asDeferredRenderPass(camera)
        .onto(screen1),
    ContrastPostProcessor(prototype1.albedoTexture, 1.3f)
        .onto(screen2),
    FxaaPostProcessor(prototype2.albedoTexture)
        .onto(MainScreen),
    uiDisplay.onto(MainScreen)
)

renderGraph.render(RenderContext())
```

Or using the DSL
```kotlin
renderGraph(WIDTH, HEIGHT) {
    val deferredNodePassOutput = deferredNodePass {
        this.camera = camera
        this.renderNode = renderNode
    }
    val lightPassOutput = lightPass {
        this.albedoBuffer = deferredNodePassOutput.albedo
        this.normalSpecularBuffer = deferredNodePassOutput.normalSpecular
        this.depthBuffer = deferredNodePassOutput.depth
        this.camera = camera
        this.directionalLights = arrayOf(light)
    }
    val fogPassOutput = fogPass {
        this.albedoBuffer = lightPassOutput.albedo
        this.depthBuffer = deferredNodePassOutput.depth
        this.camera = camera
        this.fog = Fog(Vector3.of(0f), MAX_DISTANCE_CLIP * .7f, MAX_DISTANCE_CLIP)
        this.fogDistance = FogDistance.XZ
    }
    skyboxPass(fogPassOutput.screen) {
        this.cubeMap = cubeMap
        this.camera = camera
    }
    fxaaPass(MainScreen) {
        this.albedoBuffer = fogPassOutput.albedo
    }
}
```

### Graphical User Interface

The project uses a custom Gui inspired heavily by CSS and HTML  
It can be written in a DSL in Kotlin as well as manually in Java

```kotlin
val display = UIDisplay(window) {
    +UIButton {
        style.x.value = center()
        style.y.value = center()
        style.width.value = percent(50f)
        style.height.value = ratio(.5f)
        setOnAction { println("Clicked!") }
    }

    +UIText("The quick brown fox jumps over the lazy dog") {
        style.x.value = center()
        style.y.value = center()
    }
}

display.render(RenderContext())
```

### Entity Component System

Components are the implementation of the ECS design pattern  
every Component is attachable to every ComponentNode  
allowing better composition and reusable code

```kotlin
val components = ComponentGroup(
    // Move with WASD at 50 units per second
    KeyboardMovementComponent(keyboard, 50f, 50f, 50f),
    
    // Change movement velocity by the mouse scroll
    KeyboardMovementScrollVelocityComponent(mouse),
    
    // Rotate by the mouse movement
    MouseRotationComponent(mouse, -.3f)
)

val camera = Camera(projection, components)
```

Components are very easy to create and handle  
this component implements third person view

```kotlin
// org.saar.core.common.components.ThirdPersonViewComponent.kt

class ThirdPersonViewComponent(private val toFollow: Transform, private val distance: Float) : Component {

    private lateinit var transformComponent: TransformComponent

    override fun start(node: ComponentNode) {
        // Get dependent components at initialization
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComponentNode) {
        // Update node position every update
        val position = this.transformComponent.transform.rotation.direction
            .normalize(this.distance).add(this.toFollow.position.value)
        this.transformComponent.transform.position.set(position)
    }
}
```

### Example Classes

You can try some examples that are under planet-examples module

For example:  
MultisamplingExample.java  
NormalMappingExample.kt  
ReflectionExample.kt  
ManyCubesExample.java
GuiExample.kt

## License

GNU