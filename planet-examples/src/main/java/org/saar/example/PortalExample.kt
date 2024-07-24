package org.saar.example

import org.lwjgl.glfw.GLFW
import org.saar.core.camera.Camera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.SmoothMouseRotationComponent
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator
import org.saar.core.light.Attenuation
import org.saar.core.light.PointLight
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.core.renderer.deferred.passes.LightRenderPass
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.window.Window
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector3

fun main() {
    val window = Window.create("Lwjgl", 700, 500, true)

    val camera = buildCamera(window.mouse, window.keyboard)

    val cube = generateGrid()

    val renderingPipeline = DeferredRenderingPipeline(
        DeferredGeometryPass(cube),
        LightRenderPass(arrayOf(
            PointLight().also {
                it.colour.set(1f, 1f, 1f)
                it.position.set(5f, .1f, -5f)
                it.attenuation = Attenuation.DISTANCE_7
            },
            PointLight().also {
                it.colour.set(1f, 1f, 1f)
                it.position.set(-5f, .1f, 5f)
                it.attenuation = Attenuation.DISTANCE_7
            },
            PointLight().also {
                it.colour.set(1f, 1f, 1f)
                it.position.set(5f, .1f, 5f)
                it.attenuation = Attenuation.DISTANCE_7
            },
            PointLight().also {
                it.colour.set(1f, 1f, 1f)
                it.position.set(-5f, .1f, -5f)
                it.attenuation = Attenuation.DISTANCE_7
            }
        ))
    )
    val renderingPath = DeferredRenderingPath(camera, renderingPipeline)

    val keyboard = window.keyboard
    while (window.isOpen && !keyboard.allKeysPressed('Q'.code, GLFW.GLFW_KEY_LEFT_ALT)) {
        camera.update()
        renderingPath.render().toMainScreen()

        window.swapBuffers()
        window.pollEvents()
    }

    window.destroy()
}

private fun generateGrid(): Node3D {
    val meshGenerator = DiamondMeshGenerator(2)

    val instance = R3D.instance()
    instance.transform.scale.set(15f)
    val instances = arrayOf(instance)

    val vertices = meshGenerator.generateVertices()
        .map {
            R3D.vertex(
                Vector3.of(it.x, 0f, it.y),
                Vector3.of(0f, 1f, 0f),
                Vector3.of(1f, 1f, 1f))
        }.toTypedArray()

    val indices = meshGenerator.generateIndices().toIntArray()

    return Node3D(Model3D(R3D.mesh(instances, vertices, indices)))
}

private fun buildCamera(mouse: Mouse, keyboard: Keyboard): Camera {
    val projection = ScreenPerspectiveProjection(70f, .1f, 1000f)

    val components = NodeComponentGroup(
        SmoothMouseRotationComponent(mouse, -.3f),
        KeyboardMovementComponent(keyboard, Vector3.of(5f)))

    val camera = Camera(projection, components)

    camera.transform.position.set(0f, 10f, 10f)
    camera.transform.lookAt(Position.of(0f, 0f, 0f))
    return camera
}