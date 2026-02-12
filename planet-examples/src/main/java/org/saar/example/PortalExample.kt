package org.saar.example

import org.joml.SimplexNoise
import org.joml.Vector2i
import org.lwjgl.glfw.GLFW
import org.saar.core.camera.Camera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.MouseDragRotationComponent
import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.colour.NormalColour
import org.saar.core.common.terrain.colour.NormalColourGenerator
import org.saar.core.common.terrain.height.HeightGenerator
import org.saar.core.common.terrain.height.NoiseHeightGenerator
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainFactory
import org.saar.core.common.terrain.lowpoly.LowPolyWorld
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
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.noise.LayeredNoise2f
import org.saar.maths.noise.MultipliedNoise2f
import org.saar.maths.noise.SpreadNoise2f
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3

fun main() {
    val window = Window.create("Lwjgl", 1200, 700, true)
    ClearColour.set(0.53f, 0.81f, 0.92f)

    val camera = buildCamera(window.mouse, window.keyboard)

    val portal = generatePortal()

    val world = buildWorld()
    world.createTerrain(Vector2i(0))

    val renderingPipeline = DeferredRenderingPipeline(
        DeferredGeometryPass(portal, world),
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

private fun generatePortal(): Texture3DNode {
    val meshGenerator = DiamondMeshGenerator(2)

    val vertices = meshGenerator.generateVertices()
        .map {
            Texture3D.vertex(
                Vector3.of(it.x, 0f, it.y),
                Vector2.of(it.x + .5f, it.y + .5f))
        }.toTypedArray()

    val indices = meshGenerator.generateIndices().toIntArray()

    val texture = Texture2D.of("/assets/tree/tree.diffuse.png")

    val model = Texture3DModel(Texture3D.mesh(vertices, indices), texture)
    model.transform.rotation.rotateDegrees(90f, 0f, 0f)
    model.transform.position.set(5f, 5f, 0f)
    model.transform.scale.set(5f)
    return Texture3DNode(model)
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

private fun buildWorld(): LowPolyWorld {
    val heightGenerator: HeightGenerator = NoiseHeightGenerator(
        MultipliedNoise2f(10, SpreadNoise2f(10,
            LayeredNoise2f({ x: Float, y: Float -> SimplexNoise.noise(x, y) }, 5)))
    )
    val colourGenerator: ColourGenerator = NormalColourGenerator(Vector3.upward(),
        NormalColour(0.9f, Vector3.of(.41f, .41f, .41f)),
        NormalColour(1.0f, Vector3.of(.07f, .52f, .06f)))
    val terrainFactory = LowPolyTerrainFactory(
        DiamondMeshGenerator(32), heightGenerator,
        colourGenerator, Vector2.of(32f, 32f)
    )
    return LowPolyWorld(terrainFactory)
}