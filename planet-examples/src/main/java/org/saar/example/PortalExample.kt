package org.saar.example

import org.lwjgl.glfw.GLFW
import org.saar.core.camera.Camera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.MouseDragRotationComponent
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator
import org.saar.core.common.texture3d.Texture3D
import org.saar.core.common.texture3d.Texture3DModel
import org.saar.core.common.texture3d.Texture3DNode
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3

fun main() {
    val window = Window.create("Lwjgl", 700, 500, true)

    val camera = buildCamera(window.mouse, window.keyboard)

    val cube = generateGrid()

    val renderingPipeline = DeferredRenderingPipeline(
        DeferredGeometryPass(cube),
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

private fun generateGrid(): Texture3DNode {
    val meshGenerator = DiamondMeshGenerator(2)

    val vertices = meshGenerator.generateVertices()
        .map {
            Texture3D.vertex(
                Vector3.of(it.x, 0f, it.y),
                Vector2.of(it.x + .5f, it.y + .5f))
        }.toTypedArray()

    val indices = meshGenerator.generateIndices().toIntArray()


    val texture = Texture2D.of("/assets/tree/tree.diffuse.png")

    return Texture3DNode(Texture3DModel(Texture3D.mesh(vertices, indices), texture))
}

private fun buildCamera(mouse: Mouse, keyboard: Keyboard): Camera {
    val projection = ScreenPerspectiveProjection(70f, .1f, 1000f)

    val components = NodeComponentGroup(
        MouseDragRotationComponent(mouse, -.3f),
        KeyboardMovementComponent(keyboard, Vector3.of(5f)))

    val camera = Camera(projection, components)

    camera.transform.position.set(0f, 10f, 10f)
    camera.transform.lookAt(Position.of(0f, 0f, 0f))
    return camera
}