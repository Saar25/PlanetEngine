package org.saar.example

import org.lwjgl.glfw.GLFW
import org.saar.core.camera.Camera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.FocusRotationComponent
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.particles.Particles
import org.saar.core.common.particles.ParticlesModel
import org.saar.core.common.particles.ParticlesNode
import org.saar.core.common.particles.components.ParticlesModelComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.core.node.NodeComponentGroup
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.glfw.window.WindowHints
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector3
import java.lang.Math.random
import kotlin.math.sqrt

private const val WIDTH = 1200
private const val HEIGHT = 700
private const val PARTICLES = 100000
private const val RADIUS = 50f
private const val LIFETIME = 8000

fun main() {
    val window = Window.builder("Lwjgl", WIDTH, HEIGHT, true)
        .hint(WindowHints.maximized())
        .hint(WindowHints.decorated(false))
        .build()

    ClearColour.set(.2f, .2f, .2f)

    val cameraComponents = NodeComponentGroup(
        KeyboardMovementComponent(window.keyboard, 10f, 10f, 10f),
        FocusRotationComponent(Position.of(0f, 0f, 0f)),
    )

    val projection = ScreenPerspectiveProjection(70f, 1f, 1000f)
    val camera = Camera(projection, cameraComponents).apply {
        transform.position.set(0f, 0f, 100f)
    }

    val particlesComponents = NodeComponentGroup(MyParticlesSphereComponent(), IncreaseParticlesCountComponent(10, 10))
    val particles = ParticlesNode(buildParticlesModel(), particlesComponents)

    val pipeline = DeferredRenderingPipeline(
        DeferredGeometryPass(particles),
        FxaaPostProcessor(),
    )

    val renderingPath = DeferredRenderingPath(camera, pipeline)

    val keyboard = window.keyboard

    while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
        renderingPath.render().toMainScreen()

        particles.update()
        camera.update()

        window.swapBuffers()
        window.pollEvents()
    }

    renderingPath.delete()
    window.destroy()
}

private fun buildParticlesModel(): ParticlesModel {
    val now = System.currentTimeMillis().toInt()
    val mesh = Particles.mesh(generateSequence {
        val x = (Math.random() * 2 - 1).toFloat()
        val y = (Math.random() * 2 - 1).toFloat()
        val z = (Math.random() * 2 - 1).toFloat()
        Vector3.of(x, y, z).normalize(RADIUS)
    }.take(PARTICLES)
        .mapIndexed { i, it -> Particles.instance(it, now - i * LIFETIME / PARTICLES) }
        .toList().toTypedArray())

    val texture = Texture2D.of("/assets/particles.png")

    return ParticlesModel(mesh, texture, 4, LIFETIME)
}

private class IncreaseParticlesCountComponent(
    private val initialCount: Int,
    private val increaseBy: Int
) : NodeComponent {

    private lateinit var modelComponent: ParticlesModelComponent

    override fun start(node: ComposableNode) {
        this.modelComponent = node.components.get()
        this.modelComponent.instancesCount = this.initialCount
        initializeParticles(0, this.initialCount)
    }

    override fun update(node: ComposableNode) {
        val next = (this.modelComponent.instancesCount + this.increaseBy).coerceAtMost(PARTICLES)
        initializeParticles(this.modelComponent.instancesCount, next)
        this.modelComponent.instancesCount = next
    }

    private fun initializeParticles(from: Int, to: Int) {
        this.modelComponent.model.mesh.buffers.offset(from)
        for (i in from until to) {
            val v = Vector3.randomize(Vector3.create()).sub(.5f, .5f, .5f)
                .normalize((RADIUS * sqrt(random())).toFloat())
            this.modelComponent.model.mesh.buffers.writer.writeInstance(Particles.instance(v))
        }
    }
}

private class MyParticlesSphereComponent : NodeComponent {

    private lateinit var modelComponent: ParticlesModelComponent

    override fun start(node: ComposableNode) {
        this.modelComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val now = System.currentTimeMillis().toInt()

        for (i in 0 until this.modelComponent.instancesCount) {
            this.modelComponent.model.mesh.buffers.offset(i)
            val instance = this.modelComponent.model.mesh.buffers.reader.readInstance()

            if (now - instance.birth >= LIFETIME) {
                val v = Vector3.randomize(Vector3.create()).sub(.5f, .5f, .5f)
                    .normalize((RADIUS * sqrt(random())).toFloat())
                this.modelComponent.model.mesh.buffers.offset(i)
                this.modelComponent.model.mesh.buffers.writer.writeInstance(Particles.instance(v))
            } else {
                val v = Vector3.of(instance.position3f).mul(1.001f)
                this.modelComponent.model.mesh.buffers.offset(i)
                this.modelComponent.model.mesh.buffers.writer.writeInstance(Particles.instance(v, instance.birth))
            }
        }
    }
}