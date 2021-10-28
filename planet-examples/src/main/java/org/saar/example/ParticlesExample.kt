package org.saar.example

import org.lwjgl.glfw.GLFW
import org.saar.core.behavior.BehaviorGroup
import org.saar.core.camera.Camera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.behaviors.KeyboardMovementBehavior
import org.saar.core.common.behaviors.KeyboardRotationBehavior
import org.saar.core.common.particles.Particles
import org.saar.core.common.particles.ParticlesModel
import org.saar.core.common.particles.ParticlesNode
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D
import org.saar.core.postprocessing.processors.ContrastPostProcessor
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector3

private const val WIDTH = 1200
private const val HEIGHT = 700

fun main() {
    val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

    ClearColour.set(.2f, .2f, .2f)

    val cameraBehaviors = BehaviorGroup(
        KeyboardMovementBehavior(window.keyboard, 10f, 10f, 10f),
        KeyboardRotationBehavior(window.keyboard, 50f)
    )

    // CHECK WHY FOV CANNOT GO ABOVE 72
    val camera = Camera(ScreenPerspectiveProjection(70f, 1f, 100f), cameraBehaviors).apply {
        transform.position.set(0f, 0f, 50f)
        transform.lookAt(Position.of(0f, 0f, 0f))
    }

    val particles = ParticlesNode(buildParticlesModel())

    val cube = Node3D(buildCubeModel().apply {
        transform.position.set(0f, 10f, 0f)
    })

    val pipeline = DeferredRenderingPipeline(
        DeferredGeometryPass(cube),
        ContrastPostProcessor(1.8f),
        DeferredGeometryPass(particles),
        FxaaPostProcessor(),
    )

    val renderingPath = DeferredRenderingPath(camera, pipeline)

    val keyboard = window.keyboard

    while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
        renderingPath.render().toMainScreen()

        camera.update()
        window.swapBuffers()
        window.pollEvents()
    }

    renderingPath.delete()
    window.destroy()
}

private fun buildParticlesModel(): ParticlesModel {
    val mesh = Particles.mesh((0 until 1000).map {
        val x = (Math.random() * 2 - 1).toFloat()
        val y = (Math.random() * 2 - 1).toFloat()
        val z = (Math.random() * 2 - 1).toFloat()
        val d = Math.random().toFloat() * 10
        val p = Vector3.of(x, y, z).normalize(d)
        Particles.instance(p, 2f / 16)
    }.toTypedArray())

    val texture = Texture2D.of("/assets/particles.png")

    return ParticlesModel(mesh, texture, 4)
}

private fun buildCubeModel(): Model3D {
    val cubeInstance = R3D.instance().apply {
        transform.scale.set(10f, 10f, 10f)
    }
    val cubeMesh = R3D.mesh(arrayOf(cubeInstance),
        ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices)
    return Model3D(cubeMesh)
}