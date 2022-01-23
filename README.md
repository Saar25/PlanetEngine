# PlanetEngine

An abstract game engine written on top of opengl api and lwjgl

## Lwjgl binding
every opengl and lwjgl object is wrapped with a java class,  
allowing many benefits like strong typing and encapsulation  
under pe-lwjgl-binding module

```java
// org.saar.example.Example.java

// Create the window
final Window window = Window.create("Lwjgl", 700, 500, true);

// Create a vertex array object and a vertex buffer object
final Vao vao = Vao.create();
final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);

// Allocating and storing data in out object
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

## Core engine
Most of the time it would be safer not to handle vaos and vbos manually  
using some helpful classes we can wrap out code with some nicer objects  
under pe-core module

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
renderer.render(new RenderContext(camera), model);
```

the rendering pipeline consists of some primary interfaces

### Vertex

represents a vertex of the mesh

```kotlin
interface Vertex3D : Vertex {
    val position3f: Vector3fc
    val normal3f: Vector3fc
    val colour3f: Vector3fc
}
```

### Instance

represents an instance (used for instance rendering)

```kotlin
interface Instance3D : Instance {
    val transform: Transform
}
```

### Mesh

contains the vbos and vaos that hold the data for the vertices, instances

```java
public interface Mesh {
    void draw();
    void delete();
}
```

### Model

holds the mesh with some attributes, like texture or transform

```kotlin
class Model3D(override val mesh: Mesh3D, val transform: SimpleTransform) : Model {
    constructor(mesh: Mesh3D) : this(mesh, SimpleTransform())
}
```

### Node

base class for complex objects in the scene
usually holds the model and a renderer, and has at least one render method

```java
final Model3D cubeModel = buildCubeModel();
final Node3D cube = new Node3D(cubeModel);

cube.renderForward(new RenderContext(camera))
```

### Renderer

Prototype objects are used in order to write a unique rendering pipeline

```kotlin
// org.saar.core.common.r3d.DeferredRenderer3D.kt

// Connect uniforms in the shaders to our code
@UniformProperty(UniformTrigger.PER_INSTANCE)
private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

// Use vertex and fragment shaders in the shaders program
@ShaderProperty
private val vertex = Shader.createVertex(GlslVersion.V400,
    ShaderCode.loadSource("/shaders/r3d/vertex.glsl"))

@ShaderProperty
private val fragment = Shader.createFragment(GlslVersion.V400,
    ShaderCode.loadSource("/shaders/r3d/fragmentDeferred.glsl"))

// Bind per vertex attributes
override fun vertexAttributes() = arrayOf(
    "in_position", "in_colour", "in_transformation")

// Being called before rendering
override fun onRenderCycle(context: RenderContext) {
    ProvokingVertex.setFirst();
    BlendTest.disable()
    DepthTest.enable()
}

// Being called before each instance draw
override fun onInstanceDraw(context: RenderContext, model: Model3D) {
    val v = context.camera.viewMatrix
    val p = context.camera.projection.matrix
    val m = model.transform.transformationMatrix

    this.mvpMatrixUniform.value = p.mul(v, Matrix4.temp).mul(m)
}
```

## Features

The engine features some pre-made concepts, for example

### Common rendering pipelines

Renderer2D, Renderer3D, ObjRenderer, NormalMappedRenderer and more are already implemented

### Deferred rendering

The engine makes a great usage in deferred rendering  
Render passes like LightRenderPass and ShadowsRenderPass are implemented  
as well as unique renderers like ObjDeferredRenderer and NormalMappedDeferredRenderer  
and DeferredRenderingPath to wrap it all

### Post-processing

Post-processing is extremely simple using this engine  
all that it takes is to create your own PostProcessor and create your PostProcessingPipeline

```java
// org.saar.example.normalmapping.NormalMappingExample.java

// Create the pipeline
final PostProcessingPipeline pipeline = new PostProcessingPipeline(
    new ContrastPostProcessor(1.8f),
    new GaussianBlurPostProcessor(11, 2)
);

// Render to texture using deferred renderer
final ReadOnlyTexture texture = deferredRenderer.render().toTexture();

// Post process the texture and output to the screen
pipeline.process(texture).toMainScreen();
```

### Gui

The Gui architecture is already implemented  
with some useful ui components like UISlider and UIButton

```kotlin
// org.saar.example.gui.UIButtonExample.kt

val display = UIDisplay(window)

val uiButton = UIButton().apply {
    style.x.value = center()
    style.y.value = center()
    style.width.value = percent(50f)
    style.height.value = ratio(.5f)
    setOnAction { println("Clicked!") }
}
display.add(uiButton)

display.render(RenderContext(null))
```

### Text rendering

Together with the gui, text components are also included  
allowing you to load ttf fonts and render text using simple operations

```kotlin
val font = FontLoader.loadFont("C:/Windows/Fonts/arial.ttf", 48f, 512, 512,
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

val uiText = UIText(font, "The quick brown fox jumps over the lazy dog").apply {
    style.width.value = fitContent()
    style.x.value = center()
    style.y.value = center()
}
display.add(uiText)
```

### Components

Components are the implementation of the ECS (Entity Component System) design pattern  
any Component is attachable to every ComponentNode  
allowing better composition and reusable code

```java
final ComponentGroup components = new ComponentGroup(
    // Move with WASD at 50 units per second
    new KeyboardMovementComponent(keyboard, 50f, 50f, 50f),
    
    // Change movement velocity by the mouse scroll
    new KeyboardMovementScrollVelocityComponent(mouse),
    
    // Rotate by the mouse movement
    new MouseRotationComponent(mouse, -.3f)
);

final Camera camera = new Camera(projection, components);
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

### Example classes

You can try some examples that are under planet-examples module

for example:  
MultisamplingExample.java  
NormalMappingExample.java  
ReflectionExample.java  
ManyCubesExample.java
GuiExample.java