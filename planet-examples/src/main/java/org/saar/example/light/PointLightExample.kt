package org.saar.example.light

import org.joml.SimplexNoise
import org.saar.core.camera.Camera
import org.saar.core.camera.Projection
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.*
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D
import org.saar.core.common.terrain.colour.NormalColour
import org.saar.core.common.terrain.colour.NormalColourGenerator
import org.saar.core.common.terrain.height.NoiseHeightGenerator
import org.saar.core.common.terrain.lowpoly.LowPolyTerrain
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainConfiguration
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator
import org.saar.core.light.Attenuation
import org.saar.core.light.PointLight
import org.saar.core.node.NodeComponentGroup
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.postprocessing.processors.SkyboxPostProcessor
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.core.renderer.deferred.passes.LightRenderPass
import org.saar.core.util.Fps
import org.saar.example.ExamplesUtils
import org.saar.gui.UIContainer
import org.saar.gui.UIDisplay
import org.saar.gui.UIText
import org.saar.gui.style.Colours
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.texture.CubeMapTextureBuilder
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3

private const val WIDTH = 1200
private const val HEIGHT = 700

fun Number.format(digits: Int) = "%.${digits}f".format(this)

fun main() {
    val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)
    ClearColour.set(.0f, .7f, .8f)

    val keyboard = window.keyboard
    val mouse = window.mouse
    val projection: Projection = ScreenPerspectiveProjection(70f, 1f, 1000f)

    val components = NodeComponentGroup(
        KeyboardMovementComponent(keyboard, 50f, 50f, 50f),
        KeyboardMovementScrollVelocityComponent(mouse),
        MouseDragRotationComponent(mouse, -.3f)
    )

    val camera = Camera(projection, components).apply {
        transform.position.set(0f, 50f, 0f)
        transform.rotation.lookAlong(Vector3.of(0f, 0f, 1f))
    }

    val terrain = LowPolyTerrain(Vector2.create(), LowPolyTerrainConfiguration(
        DiamondMeshGenerator(256),
        NoiseHeightGenerator(SimplexNoise::noise),
        NormalColourGenerator(Vector3.upward(),
            NormalColour(0.5f, Vector3.of(.41f, .41f, .41f)),
            NormalColour(1.0f, Vector3.of(.07f, .52f, .06f))),
        Vector2.of(1024f, 1024f), 100f
    ))

    val lights = Array(100) {
        val lightComponents = NodeComponentGroup(
            TransformComponent().apply { transform.position.set(0f, 30f, 0f) },
            VelocityComponent(),
            AccelerationComponent(),
            RandomMovementComponent()
        )
        PointLight(lightComponents).apply {
            attenuation = Attenuation.DISTANCE_600
            Vector3.randomize(colour)
            update()
        }
    }

    val cubeModel = buildCubeModel().apply {
        transform.position.addX(-30f)
        transform.position.addY(10f)
    }
    val cube = Node3D(cubeModel)

    val cubeMap = createCubeMap()
    val pipeline = DeferredRenderingPipeline(
        DeferredGeometryPass(terrain, cube),
        SkyboxPostProcessor(cubeMap),
        LightRenderPass(pointLights = lights),
        FxaaPostProcessor()
    )
    val renderingPath = DeferredRenderingPath(camera, pipeline)

    val uiDisplay = UIDisplay(window)

    val uiTextGroup = UIContainer().apply {
        style.x.set(30)
        style.y.set(30)
        style.fontSize.set(32)
        style.fontColour.set(Colours.WHITE)
    }

    val uiFps = UIText("Fps: ???")
    uiTextGroup.add(uiFps)

    val uiDelta = UIText("Delta: ???").apply {
        style.y.set(32)
    }
    uiTextGroup.add(uiDelta)

    uiDisplay.add(uiTextGroup)

    val fps = Fps()

    while (window.isOpen && !keyboard.isKeyPressed('T'.code)) {
        camera.update()
        uiDisplay.update()
        lights.forEach { it.update() }

        renderingPath.render().toMainScreen()
        uiDisplay.render(RenderContext(camera))

        window.swapBuffers()
        window.pollEvents()

        uiFps.text = "Fps: ${fps.fps().format(2)}"
        uiDelta.text = "Delta: ${(fps.delta() * 1000).format(2)}"
        fps.update()
    }

    camera.delete()
    renderingPath.delete()
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
    val cubeMesh = R3D.mesh(arrayOf(cubeInstance),
        ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices)
    return Model3D(cubeMesh)
}