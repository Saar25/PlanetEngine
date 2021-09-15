package org.saar.example.light

import org.joml.SimplexNoise
import org.saar.core.behavior.BehaviorGroup
import org.saar.core.camera.Camera
import org.saar.core.camera.Projection
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.behaviors.*
import org.saar.core.common.terrain.colour.NormalColour
import org.saar.core.common.terrain.colour.NormalColourGenerator
import org.saar.core.common.terrain.height.NoiseHeightGenerator
import org.saar.core.common.terrain.lowpoly.LowPolyTerrain
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainConfiguration
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator
import org.saar.core.light.Attenuation
import org.saar.core.light.PointLight
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.postprocessing.processors.SkyboxPostProcessor
import org.saar.core.renderer.RenderContextBase
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.DeferredRenderPassesPipeline
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.passes.LightRenderPass
import org.saar.core.screen.MainScreen
import org.saar.core.util.Fps
import org.saar.gui.UIContainer
import org.saar.gui.UIDisplay
import org.saar.gui.UITextElement
import org.saar.gui.style.Colours
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.textures.CubeMapTexture
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
    val projection: Projection = ScreenPerspectiveProjection(MainScreen, 70f, 1f, 1000f)

    val behaviors = BehaviorGroup(
        KeyboardMovementBehavior(keyboard, 50f, 50f, 50f),
        KeyboardMovementScrollVelocityBehavior(mouse),
        MouseRotationBehavior(mouse, -.3f)
    )

    val camera = Camera(projection, behaviors).apply {
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

    val lights = Array(200) {
        val lightBehaviors = BehaviorGroup(
            TransformBehavior().apply { transform.position.set(0f, 10f, 0f) },
            VelocityBehavior(),
            AccelerationBehavior(),
            RandomMovementBehavior()
        )
        PointLight(lightBehaviors).apply {
            attenuation = Attenuation.DISTANCE_600
            Vector3.randomize(colour)
        }
    }

    val cubeMap = createCubeMap()
    val renderNode = DeferredRenderNodeGroup(terrain)
    val pipeline = DeferredRenderPassesPipeline(
        SkyboxPostProcessor(cubeMap),
        FxaaPostProcessor(),
        LightRenderPass(lights, emptyArray())
    )
    val renderingPath = DeferredRenderingPath(camera, renderNode, pipeline)

    val uiDisplay = UIDisplay(window)

    val uiTextGroup = UIContainer().apply {
        style.x.set(30)
        style.y.set(30)
        style.fontSize.set(32)
        style.fontColour.set(Colours.WHITE)
    }

    val uiFps = UITextElement("Fps: ???")
    uiTextGroup.add(uiFps)

    val uiDelta = UITextElement("Delta: ???").apply {
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
        uiDisplay.render(RenderContextBase(camera))

        window.update(true)
        window.pollEvents()

        uiFps.uiText.text = "Fps: ${fps.fps().format(2)}"
        uiDelta.uiText.text = "Delta: ${(fps.delta() * 1000).format(2)}"
        fps.update()
    }

    camera.delete()
    renderingPath.delete()
    window.destroy()
}

private fun createCubeMap() = CubeMapTexture.builder()
    .positiveX("/assets/skybox/right.jpg")
    .negativeX("/assets/skybox/left.jpg")
    .positiveY("/assets/skybox/top.jpg")
    .negativeY("/assets/skybox/bottom.jpg")
    .positiveZ("/assets/skybox/front.jpg")
    .negativeZ("/assets/skybox/back.jpg")
    .create()