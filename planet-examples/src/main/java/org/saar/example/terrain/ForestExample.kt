package org.saar.example.terrain

import org.joml.Vector2i
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL43C
import org.saar.core.camera.Camera
import org.saar.core.camera.projection.OrthographicProjection
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.camera.projection.SimpleOrthographicProjection
import org.saar.core.common.components.*
import org.saar.core.common.obj.Obj.mesh
import org.saar.core.common.obj.ObjModel
import org.saar.core.common.obj.ObjNode
import org.saar.core.common.obj.ObjNodeBatch
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D
import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.colour.NormalColour
import org.saar.core.common.terrain.colour.NormalColourGenerator
import org.saar.core.common.terrain.components.TerrainGravityComponent
import org.saar.core.common.terrain.components.TerrainJumpingComponent
import org.saar.core.common.terrain.components.TerrainWalkingComponent
import org.saar.core.common.terrain.height.NoiseHeightGenerator
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainFactory
import org.saar.core.common.terrain.lowpoly.LowPolyWorld
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator
import org.saar.core.fog.Fog
import org.saar.core.fog.FogDistance
import org.saar.core.light.DirectionalLight
import org.saar.core.node.NodeComponentGroup
import org.saar.core.postprocessing.FxaaPostProcessor
import org.saar.core.postprocessing.GaussianBlurRenderPass
import org.saar.core.postprocessing.MultiplyPostProcessor
import org.saar.core.postprocessing.SkyboxPostProcessor
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.deferred.DeferredScreenPrototype
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.core.renderer.deferred.passes.SSAOMapGenerator
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass
import org.saar.core.renderer.forward.passes.FogRenderPass
import org.saar.core.renderer.onto
import org.saar.core.renderer.p2d.ScreenPrototype2D
import org.saar.core.renderer.shadow.ShadowsCamera
import org.saar.core.renderer.shadow.ShadowsQuality
import org.saar.core.renderer.shadow.ShadowsRenderNodeGroup
import org.saar.core.renderer.shadow.ShadowsScreenPrototype
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.clear
import org.saar.example.ExamplesUtils
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.texture.CubeMapTexture
import org.saar.lwjgl.opengl.texture.CubeMapTextureBuilder
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.noise.Noise2f
import org.saar.maths.noise.layered
import org.saar.maths.noise.multiplied
import org.saar.maths.noise.spread
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3
import java.io.IOException

private const val WIDTH = 1200
private const val HEIGHT = 700

fun main() {
    val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)
    GL20.glDisable(GL43C.GL_DEBUG_OUTPUT)

    ClearColour.set(.0f, .7f, .8f)

    val projection = ScreenPerspectiveProjection(70f, 1f, 1000f)

    val world = buildWorld()
    for (x in -5..5) {
        for (z in -5..5) {
            world.createTerrain(Vector2i(x, z))
        }
    }

    val playerComponents = NodeComponentGroup(
        VelocityComponent(),
        AccelerationComponent(),
        TerrainGravityComponent(world),
        TerrainJumpingComponent(world, window.keyboard, 5f),
        TerrainWalkingComponent(world, window.keyboard, 5f, 5f)
    )
    val playerModel = buildCubeModel()
    val player = Node3D(playerModel, playerComponents)

    val components = NodeComponentGroup(
        MouseDragRotationComponent(window.mouse, -.3f),
        ThirdPersonViewComponent(player.model.transform, 5f)
    )
    val camera = Camera(projection, components)

    player.components.add(BackFaceComponent(camera.transform, .1f))

    val mesh = mesh("/assets/tree/tree.model.obj")
    val texture = Texture2D.of("/assets/tree/tree.diffuse.png")
    val treesNodeBatch = ObjNodeBatch(*(1..1000).map {
        val treeModel = ObjModel(mesh, texture)
        val tree = ObjNode(treeModel)
        val x = (Math.random() * 200 - 100).toFloat()
        val z = (Math.random() * 200 - 100).toFloat()
        treeModel.transform.position.set(x, world.getHeight(x, 0f, z) + 2, z)
        tree
    }.toTypedArray())

    val cubeModel = buildCubeModel()
    val cube = Node3D(cubeModel)

    val cubeModel2 = buildCubeModel()
    cubeModel2.transform.position.addX(-5f)
    cubeModel2.transform.position.addY(5f)
    val cube2 = Node3D(cubeModel2)

    val light = DirectionalLight().apply {
        direction.set(-1.0, -.6, -1.0)
        colour.set(1f, 1f, 1f)
    }

    val shadowProjection: OrthographicProjection = SimpleOrthographicProjection(
        -100f, 100f, -100f, 100f, -100f, 100f
    )
    val shadowsPrototype = ShadowsScreenPrototype()
    val shadowsScreen = shadowsPrototype.toScreen(
        Fbo.create(ShadowsQuality.MEDIUM.imageSize, ShadowsQuality.MEDIUM.imageSize),
    )
    val shadowsCamera = ShadowsCamera(shadowProjection, light)

    val shadowMap = shadowsPrototype.depthTexture

    val shadowsRenderGraph = RenderGraph(
        ShadowsRenderNodeGroup(cube, cube2, treesNodeBatch, world, player).onto(shadowsScreen)
    )


    val renderNode = DeferredGeometryPass(cube, cube2, player, treesNodeBatch, world)

    val cubeMap = createCubeMap()
    val fog = Fog(Vector3.of(0f), 700f, 1000f)

    val depthTexture = MutableTexture2D.create()
    val prototype1 = DeferredScreenPrototype(depthTexture = depthTexture)
    val screen1 = prototype1.toScreen(Fbo.create(WIDTH, HEIGHT))

    val prototype2 = DeferredScreenPrototype(depthTexture = depthTexture)
    val screen2 = prototype2.toScreen(Fbo.create(WIDTH, HEIGHT))

    val prototype3 = ScreenPrototype2D()
    val screen3 = prototype3.toScreen(Fbo.create(WIDTH / 4, HEIGHT / 4))

    val prototype4 = ScreenPrototype2D()
    val screen4 = prototype4.toScreen(Fbo.create(WIDTH / 4, HEIGHT / 4))

    val prototype5 = ScreenPrototype2D()
    val screen5 = prototype5.toScreen(Fbo.create(WIDTH, HEIGHT))

    val gaussianBlur = GaussianBlurRenderPass()

    val renderGraph = RenderGraph(
        renderNode.onto(screen1),
        ShadowsRenderPass(
            prototype1.albedoTexture,
            prototype1.normalSpecularTexture,
            depthTexture,
            shadowsCamera,
            shadowMap,
            light
        ).onto(screen2),
        SSAOMapGenerator(
            prototype1.normalSpecularTexture,
            depthTexture,
            radius = 1f,
            kernelSamplesSize = 128
        ).onto(screen4),
        gaussianBlur.Vertical(prototype4.albedoTexture).onto(screen3),
        gaussianBlur.Horizontal(prototype3.albedoTexture).onto(screen4),
        MultiplyPostProcessor(prototype4.albedoTexture, prototype2.albedoTexture).onto(screen5),
        FogRenderPass(
            prototype5.albedoTexture,
            depthTexture,
            fog,
            FogDistance.XZ
        ).onto(screen1),
        SkyboxPostProcessor(cubeMap).onto(screen1),
        FxaaPostProcessor(prototype1.albedoTexture).onto(MainScreen)
    )

    var last = System.currentTimeMillis()

    while (window.isOpen && !window.keyboard.isKeyPressed('T'.code)) {
        val delta = System.currentTimeMillis() - last
        last = System.currentTimeMillis()
        print("\r --->$delta")

        player.update()
        camera.update()

        shadowsScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        shadowsRenderGraph.render(RenderContext(shadowsCamera))

        screen1.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen2.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        MainScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        renderGraph.render(RenderContext(camera))

        window.swapBuffers()
        window.pollEvents()
    }

    camera.delete()
    screen1.delete()
    screen2.delete()
    renderGraph.delete()
    window.destroy()
}

private fun buildWorld(): LowPolyWorld {
    val heightGenerator = NoiseHeightGenerator(
        Noise2f.simplex.layered(5).spread(5f).multiplied(50f)
    )
    val colourGenerator: ColourGenerator = NormalColourGenerator(
        Vector3.upward(),
        NormalColour(0.2f, Vector3.of(.41f, .41f, .41f)),
        NormalColour(0.3f, Vector3.of(.07f, .52f, .06f))
    )

    val terrainFactory = LowPolyTerrainFactory(
        DiamondMeshGenerator(64), heightGenerator,
        colourGenerator, Vector2.of(32f, 32f)
    )

    return LowPolyWorld(terrainFactory)
}

private fun buildCubeModel(): Model3D {
    val cubeInstance = R3D.instance()
    val cubeMesh = R3D.mesh(
        arrayOf(cubeInstance),
        ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices
    )
    return Model3D(cubeMesh)
}

@Throws(IOException::class)
private fun createCubeMap(): CubeMapTexture {
    return CubeMapTextureBuilder()
        .positiveX("/assets/skybox/right.jpg")
        .negativeX("/assets/skybox/left.jpg")
        .positiveY("/assets/skybox/top.jpg")
        .negativeY("/assets/skybox/bottom.jpg")
        .positiveZ("/assets/skybox/front.jpg")
        .negativeZ("/assets/skybox/back.jpg")
        .create()
}
