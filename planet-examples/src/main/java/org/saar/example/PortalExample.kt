package org.saar.example

import org.joml.Anglef.Companion.degrees
import org.joml.Vector2i
import org.lwjgl.glfw.GLFW
import org.saar.core.camera.Camera
import org.saar.core.camera.ReadonlyCamera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.MouseDragRotationComponent
import org.saar.core.common.portal.Portal
import org.saar.core.common.portal.PortalModel
import org.saar.core.common.portal.PortalNode
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D.instance
import org.saar.core.common.r3d.R3D.mesh
import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.colour.NormalColour
import org.saar.core.common.terrain.colour.NormalColourGenerator
import org.saar.core.common.terrain.height.NoiseHeightGenerator
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainFactory
import org.saar.core.common.terrain.lowpoly.LowPolyWorld
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator
import org.saar.core.light.DirectionalLight
import org.saar.core.mesh.Mesh
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.deferred.DeferredNodeRenderPass
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.DeferredScreenPrototype
import org.saar.core.renderer.deferred.asDeferredRenderPass
import org.saar.core.renderer.onto
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.clear
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.noise.Noise2f
import org.saar.maths.noise.layered
import org.saar.maths.noise.multiplied
import org.saar.maths.noise.spread
import org.saar.maths.transform.Position
import org.saar.maths.transform.RelativeTransform
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3

fun main() {
    val window = Window.create("Lwjgl", 1200, 700, true)
    ClearColour.set(0.53f, 0.81f, 0.92f)

    val camera = buildCamera(window.mouse, window.keyboard)

    val world = buildWorld()
    world.createTerrain(Vector2i(0))

    val cubeInstance = instance().also {
        it.transform.position.set(-5f, 5f, 5f)
    }
    val cubeMesh = mesh(arrayOf(cubeInstance), ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices)
    val cubeModel = Model3D(cubeMesh)
    val cube = Node3D(cubeModel)

    val light = DirectionalLight().also {
        it.direction.set(-1f, -1f, -1f)
        it.colour.set(1f, 1f, 1f)
    }

    val mesh = generatePortalMesh()
    val portal1 = PortalModel(mesh).also {
        it.transform.rotation.rotate(90f.degrees, 90f.degrees, 0f.degrees)
        it.transform.position.set(5f, 5f, 0f)
        it.transform.scale.set(5f)
    }.let(::PortalNode)
    val portal2 = PortalModel(mesh).apply {
        transform.rotation.rotate(90f.degrees, 0f.degrees, 0f.degrees)
        transform.position.set(-5f, 5f, 0f)
        transform.scale.set(5f)
    }.let(::PortalNode)

    val portal1CameraTransform = RelativeTransform(
        camera.transform, portal1.model.transform, portal2.model.transform
    )
    val portalCamera1 = ReadonlyCamera(camera.projection, portal1CameraTransform)

    val prototype1a = DeferredScreenPrototype()
    val screen1a = prototype1a.toScreen(Fbo.create(window.width, window.height))

    val prototype1b = DeferredScreenPrototype()
    val screen1b = prototype1b.toScreen(Fbo.create(window.width, window.height))

    val portalRenderGraph1 = RenderGraph(
        DeferredNodeRenderPass(camera, world, cube).onto(screen1b),
        /*LightRenderPass(
            albedoBuffer = prototype1a.albedoTexture,
            normalSpecularBuffer = prototype1a.normalSpecularTexture,
            depthBuffer = prototype1a.depthTexture,
            directionalLights = arrayOf(light)
        ).onto(screen1b)*/
    )

    val portal2CameraTransform = RelativeTransform(
        camera.transform, portal2.model.transform, portal1.model.transform
    )
    val portalCamera2 = ReadonlyCamera(camera.projection, portal2CameraTransform)

    val prototype2a = DeferredScreenPrototype()
    val screen2a = prototype2a.toScreen(Fbo.create(window.width, window.height))

    val prototype2b = DeferredScreenPrototype()
    val screen2b = prototype2b.toScreen(Fbo.create(window.width, window.height))

    // TODO: fix lights
    val portalRenderGraph2 = RenderGraph(
        DeferredNodeRenderPass(camera, world, cube).onto(screen2b),
        /*LightRenderPass(
            albedoBuffer = prototype2a.albedoTexture,
            normalSpecularBuffer = prototype2a.normalSpecularTexture,
            depthBuffer = prototype2a.depthTexture,
            directionalLights = arrayOf(light)
        ).onto(screen2b)*/
    )

    portal1.model.viewTexture = prototype1b.albedoTexture
    portal2.model.viewTexture = prototype2b.albedoTexture

    val prototype = DeferredScreenPrototype()
    val screen = prototype.toScreen(Fbo.create(window.width, window.height))

    val renderGraph = RenderGraph(
        DeferredRenderNodeGroup(portal1, portal2, world, cube)
            .asDeferredRenderPass(camera)
            .onto(MainScreen),
        /*LightRenderPass(
            albedoBuffer = prototype.albedoTexture,
            normalSpecularBuffer = prototype.normalSpecularTexture,
            depthBuffer = prototype.depthTexture,
            directionalLights = arrayOf(light)
        ).onto(MainScreen)*/
    )

    val keyboard = window.keyboard
    while (window.isOpen && !keyboard.allKeysPressed('Q'.code, GLFW.GLFW_KEY_LEFT_ALT)) {
        camera.update()

        screen1a.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen1b.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen2a.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen2b.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        MainScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)

        portalRenderGraph1.render(RenderContext())
        portalRenderGraph2.render(RenderContext())

        renderGraph.render(RenderContext())

        window.swapBuffers()
        window.pollEvents()
    }

    window.destroy()
}

private fun generatePortalMesh(): Mesh {
    val meshGenerator = DiamondMeshGenerator(2)

    val vertices = meshGenerator.generateVertices()
        .map { Portal.vertex(Vector3.of(it.x, 0f, it.y)) }
        .toTypedArray()

    val indices = meshGenerator.generateIndices().toIntArray()

    val mesh = Portal.mesh(vertices, indices)
    return mesh
}

private fun buildCamera(mouse: Mouse, keyboard: Keyboard): Camera {
    val projection = ScreenPerspectiveProjection(70f, .1f, 1000f)

    val components = NodeComponentGroup(
        MouseDragRotationComponent(mouse, -.3f),
        KeyboardMovementComponent(keyboard, Vector3.of(5f))
    )

    val camera = Camera(projection, components)

    camera.transform.position.set(0f, 10f, 10f)
    camera.transform.lookAt(Position.of(0f, 0f, 0f))
    return camera
}

private fun buildWorld(): LowPolyWorld {
    val heightGenerator = NoiseHeightGenerator(
        Noise2f.simplex.layered(5).spread(8f).multiplied(18f)
    )
    val colourGenerator: ColourGenerator = NormalColourGenerator(
        Vector3.upward(),
        NormalColour(0.90f, Vector3.of(.41f, .41f, .41f)),
        NormalColour(1.0f, Vector3.of(.07f, .52f, .06f))
    )
    val terrainFactory = LowPolyTerrainFactory(
        DiamondMeshGenerator(64), heightGenerator,
        colourGenerator, Vector2.of(64f, 64f)
    )
    return LowPolyWorld(terrainFactory)
}