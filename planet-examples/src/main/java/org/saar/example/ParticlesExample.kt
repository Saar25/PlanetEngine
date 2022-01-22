package org.saar.example

import org.lwjgl.glfw.GLFW
import org.saar.core.camera.Camera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.SmoothMouseRotationComponent
import org.saar.core.common.particles.Particles
import org.saar.core.common.particles.ParticlesInstance
import org.saar.core.common.particles.ParticlesModel
import org.saar.core.common.particles.ParticlesNode
import org.saar.core.common.particles.components.ParticlesControlComponent
import org.saar.core.common.particles.components.ParticlesModelComponent
import org.saar.core.mesh.prototype.setInstancePosition
import org.saar.core.mesh.prototype.writeInstance
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.core.node.NodeComponentGroup
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.core.util.Fps
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.style.Colours
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.length.LengthValues
import org.saar.gui.style.position.PositionValues.relative
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector3
import kotlin.system.measureTimeMillis

private const val WIDTH = 1200
private const val HEIGHT = 700
private const val PARTICLES = 500000

fun Number.format(digits: Int) = "%.${digits}f".format(this)

fun main() {
    val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

    ClearColour.set(.2f, .2f, .2f)

    val cameraComponents = NodeComponentGroup(
        KeyboardMovementComponent(window.keyboard, 10f, 10f, 10f),
        SmoothMouseRotationComponent(window.mouse, .3f)
    )

    val camera = Camera(ScreenPerspectiveProjection(70f, 1f, 1000f), cameraComponents).apply {
        transform.position.set(0f, 0f, 50f)
        transform.lookAt(Position.of(0f, 0f, 0f))
    }

    val uiDisplay = UIDisplay(window)

    val uiContainer = UIElement().apply {
        style.alignment.value = AlignmentValues.vertical
        style.width.value = LengthValues.fill
    }

    uiDisplay.add(uiContainer)

    val uiFps = UIText("Fps: ???").apply {
        style.fontSize.set(30)
        style.fontColour.set(Colours.WHITE)
        style.backgroundColour.set(Colours.BLACK)
    }
    uiContainer.add(uiFps)

    val uiTime = UIText("Time: ???").apply {
        style.fontSize.set(30)
        style.fontColour.set(Colours.WHITE)
        style.backgroundColour.set(Colours.BLACK)
    }
    uiContainer.add(uiTime)

    val particlesComponents = NodeComponentGroup(MyParticlesComponent())
    val particles = ParticlesNode(buildParticlesModel(), particlesComponents)

    val pipeline = DeferredRenderingPipeline(
        DeferredGeometryPass(particles, uiDisplay),
        FxaaPostProcessor(),
    )

    val renderingPath = DeferredRenderingPath(camera, pipeline)

    val fps = Fps()

    val keyboard = window.keyboard

    while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
        val time = measureTimeMillis {
            renderingPath.render().toMainScreen()

            particles.update()
            uiDisplay.update()
            camera.update()
        }

        window.swapBuffers()
        window.pollEvents()

        uiFps.text = "Fps: ${fps.fps().format(2)}"
        uiTime.text = "Time: $time"
        fps.update()
    }

    renderingPath.delete()
    window.destroy()
}

private fun buildParticlesModel(): ParticlesModel {
    val radius = 100f
    val mesh = Particles.mesh(generateSequence {
        val x = (Math.random() * 2 - 1).toFloat()
        val y = (Math.random() * 2 - 1).toFloat()
        val z = (Math.random() * 2 - 1).toFloat()
        Vector3.of(x, y, z).mul(radius)
    }
        .filter { it.lengthSquared() < radius * radius }
        .map { Particles.instance(it) }
        .take(PARTICLES).toList().toTypedArray())

    val texture = Texture2D.of("/assets/particles.png")

    return ParticlesModel(mesh, texture, 4, 32000)
}

private class MyParticlesControlComponent : ParticlesControlComponent() {
    override fun createParticles(): Collection<ParticlesInstance> {
        return emptyList()
    }

    override fun mapInstance(index: Int, instance: ParticlesInstance): ParticlesInstance {
        val v = Vector3.randomize(Vector3.create()).sub(.5f, .5f, .5f).mul(2f).mul(10f)
        val passed = Math.random() < .01 && v.lengthSquared() < 100
        return if (passed) Particles.instance(v) else instance
    }
}

private class MyParticlesComponent : NodeComponent {

    private lateinit var modelComponent: ParticlesModelComponent

    override fun start(node: ComposableNode) {
        this.modelComponent = node.components.get()
        modelComponent.instancesCount = 10
    }

    override fun update(node: ComposableNode) {
        modelComponent.model.mesh.prototype.setInstancePosition(0)
        for (i in 0 until modelComponent.instancesCount) {
            val v = Vector3.randomize(Vector3.create()).sub(.5f, .5f, .5f).mul(2f).mul(10f)
            if (Math.random() < .01 && v.lengthSquared() < 100) {
                modelComponent.model.mesh.prototype.writeInstance(i, Particles.instance(v))
            }
        }

        modelComponent.instancesCount = (modelComponent.instancesCount + 1).coerceAtMost(PARTICLES)
    }
}