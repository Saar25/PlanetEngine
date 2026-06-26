package org.saar.example.light

import org.joml.Vector2i
import org.saar.core.camera.Camera
import org.saar.core.camera.Projection
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.*
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D
import org.saar.core.common.renderpass.FxaaPostProcessor
import org.saar.core.common.renderpass.LightRenderPass
import org.saar.core.common.renderpass.SkyboxPostProcessor
import org.saar.core.common.terrain.color.NormalColor
import org.saar.core.common.terrain.color.NormalColorGenerator
import org.saar.core.common.terrain.components.TerrainGravityComponent
import org.saar.core.common.terrain.height.NoiseHeightGenerator
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainFactory
import org.saar.core.common.terrain.lowpoly.LowPolyWorld
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator
import org.saar.core.light.Attenuation
import org.saar.core.light.DirectionalLight
import org.saar.core.light.PointLight
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.deferred.DeferredNodeRenderPass
import org.saar.core.renderer.deferred.DeferredScreenPrototype
import org.saar.core.renderer.onto
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.assureSize
import org.saar.core.screen.clear
import org.saar.core.util.Fps
import org.saar.example.ExamplesUtils
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.style.Colors
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.arrangement.ArrangementValues
import org.saar.gui.style.length.LengthValues.percent
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColor
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.texture.CubeMapTextureBuilder
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.noise.Noise2f
import org.saar.maths.noise.layered
import org.saar.maths.noise.multiplied
import org.saar.maths.noise.spread
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3

private const val WIDTH = 1200
private const val HEIGHT = 700

fun Number.format(digits: Int) = "%.${digits}f".format(this)

fun main() {
    val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)
    ClearColor.set(.0f, .7f, .8f)

    val projection: Projection = ScreenPerspectiveProjection(70f, 1f, 1000f)

    val components = NodeComponentGroup(
        KeyboardMovementComponent(window.keyboard, 50f, 50f, 50f),
        KeyboardMovementScrollVelocityComponent(window.mouse),
        MouseDragRotationComponent(window.mouse, -.3f)
    )

    val camera = Camera(projection, components).apply {
        transform.position.set(0f, 50f, 0f)
        transform.rotation.lookAlong(Vector3.of(0f, 0f, 1f))
    }

    val heightGenerator = NoiseHeightGenerator(
        Noise2f.simplex.layered(5).spread(50f).multiplied(200f)
    )
    val terrainFactory = LowPolyTerrainFactory(
        DiamondMeshGenerator(64), heightGenerator,
        NormalColorGenerator(
            Vector3.upward(),
            NormalColor(0.5f, Vector3.of(.41f, .41f, .41f)),
            NormalColor(1.0f, Vector3.of(.07f, .52f, .06f))
        ),
        Vector2.of(256f, 256f)
    )
    val world = LowPolyWorld(terrainFactory)
    world.createTerrain(Vector2i(0, 0))

    val lights = Array(200) {
        val lightComponents = NodeComponentGroup(
            TransformComponent().apply { transform.position.set(0f, 500f, 0f) },
            VelocityComponent(),
            AccelerationComponent(),
            RandomMovementComponent(),
            TerrainGravityComponent(world),
        )
        PointLight(lightComponents).apply {
            attenuation = Attenuation.DISTANCE_32
            Vector3.randomize(color)
            update()
        }
    }

    val cubeModel = buildCubeModel().apply {
        transform.position.addX(-30f)
        transform.position.addY(10f)
    }
    val cube = Node3D(cubeModel)

    val cubeMap = createCubeMap()

    val uiDisplay = UIDisplay(window)

    val uiTextGroup = UIElement().apply {
        style.fontSize.set(32)
        style.fontColor.set(Colors.WHITE)
        style.width.value = percent(100f)
        style.height.value = percent(100f)
        style.alignment.value = AlignmentValues.horizontal
        style.arrangement.value = ArrangementValues.spaceBetween
    }

    val uiFps = UIText("Fps: ???")
    uiTextGroup.add(uiFps)

    val uiDelta = UIText("Delta: ???").apply {
        style.y.set(32)
    }
    uiTextGroup.add(uiDelta)

    uiDisplay.add(uiTextGroup)

    val depthTexture = MutableTexture2D.create()

    val screenPrototype1 = DeferredScreenPrototype(depthTexture = depthTexture)
    val screen1 = screenPrototype1.toScreen(Fbo.create(), WIDTH, HEIGHT)

    val screenPrototype2 = DeferredScreenPrototype(depthTexture = depthTexture)
    val screen2 = screenPrototype2.toScreen(Fbo.create(), WIDTH, HEIGHT)

    val renderGraph = RenderGraph(
        DeferredNodeRenderPass(camera, world, cube).onto(screen1),
        LightRenderPass(
            albedoBuffer = screenPrototype1.albedoTexture,
            normalSpecularBuffer = screenPrototype1.normalSpecularTexture,
            depthBuffer = depthTexture,
            camera = camera,
            pointLights = lights,
            directionalLights = arrayOf(
                DirectionalLight().also {
                    it.color.set(Vector3.of(.2f))
                    it.direction.set(Vector3.DOWN)
                }
            )
        ).onto(screen2),
        SkyboxPostProcessor(cubeMap, camera).onto(screen2),
        uiDisplay.onto(screen2),
        FxaaPostProcessor(screenPrototype2.albedoTexture).onto(MainScreen)
    )

    val fps = Fps()

    while (window.isOpen && !window.keyboard.isKeyPressed('T'.code)) {
        camera.update()
        uiDisplay.update()
        lights.forEach { it.update() }

        screen1.clear(GlBuffer.COLOR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen1.assureSize(MainScreen.width, MainScreen.height)
        screen2.clear(GlBuffer.COLOR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen2.assureSize(MainScreen.width, MainScreen.height)
        MainScreen.clear(GlBuffer.COLOR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        renderGraph.render(RenderContext())

        window.swapBuffers()
        window.pollEvents()

        uiFps.text = "Fps: ${fps.fps().format(2)}"
        uiDelta.text = "Delta: ${(fps.delta() * 1000).format(2)}"
        fps.update()
    }

    camera.delete()
    screen1.delete()
    screen2.delete()
    renderGraph.delete()
    window.destroy()
}

private fun createCubeMap() = CubeMapTextureBuilder()
    .positiveX("/assets/skybox/right.jpg")
    .negativeX("/assets/skybox/left.jpg")
    .positiveY("/assets/skybox/top.jpg")
    .negativeY("/assets/skybox/bottom.jpg")
    .positiveZ("/assets/skybox/front.jpg")
    .negativeZ("/assets/skybox/back.jpg")
    .create()

private fun buildCubeModel(): Model3D {
    val cubeInstance = R3D.instance()
    cubeInstance.transform.scale.set(10f, 10f, 10f)
    val cubeMesh = R3D.mesh(
        arrayOf(cubeInstance),
        ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices
    )
    return Model3D(cubeMesh)
}