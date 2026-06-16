package org.saar.example.terrain

import org.joml.SimplexNoise
import org.joml.Vector2i
import org.saar.core.camera.Camera
import org.saar.core.camera.Projection
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.KeyboardMovementScrollVelocityComponent
import org.saar.core.common.components.MouseDragRotationComponent
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D.instance
import org.saar.core.common.r3d.R3D.mesh
import org.saar.core.common.terrain.World
import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.colour.NormalColour
import org.saar.core.common.terrain.colour.NormalColourGenerator
import org.saar.core.common.terrain.height.NoiseHeightGenerator
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainFactory
import org.saar.core.common.terrain.lowpoly.LowPolyWorld
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator
import org.saar.core.engine.Application
import org.saar.core.engine.PlanetEngine
import org.saar.core.fog.Fog
import org.saar.core.fog.FogDistance
import org.saar.core.light.DirectionalLight
import org.saar.core.node.NodeComponentGroup
import org.saar.core.postprocessing.FxaaPostProcessor
import org.saar.core.postprocessing.SkyboxPostProcessor
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.asDeferredRenderNode
import org.saar.core.renderer.deferred.passes.LightRenderPass
import org.saar.core.renderer.forward.passes.FogRenderPass
import org.saar.core.renderer.onto
import org.saar.core.screen.MainScreen
import org.saar.core.screen.OffScreen
import org.saar.core.screen.ScreenBuilder
import org.saar.core.screen.clear
import org.saar.core.util.Fps
import org.saar.example.ExamplesUtils
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.texture.CubeMapTexture
import org.saar.lwjgl.opengl.texture.CubeMapTextureBuilder
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.noise.LayeredNoise2f
import org.saar.maths.noise.multiplied
import org.saar.maths.noise.spread
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3

private const val WIDTH = 1200
private const val HEIGHT = 700
private const val MAX_DISTANCE_CLIP = 1_000f

fun main() {
    val application = TerrainApplication()
    val engine = PlanetEngine()
    engine.run(application)
}

private class TerrainApplication : Application {

    private lateinit var fps: Fps
    private lateinit var camera: Camera
    private lateinit var renderGraph: RenderGraph
    private lateinit var cameraMovementComponent: KeyboardMovementComponent
    private lateinit var screenA: OffScreen
    private lateinit var screenB: OffScreen

    override fun initialize(window: Window) {
        window.width = WIDTH
        window.height = HEIGHT

        ClearColour.set(.0f, .7f, .8f)

        val keyboard = window.keyboard
        val mouse = window.mouse

        val projection: Projection = ScreenPerspectiveProjection(70f, 1f, MAX_DISTANCE_CLIP)
        this.cameraMovementComponent = KeyboardMovementComponent(keyboard, 50f, 50f, 50f)
        val components = NodeComponentGroup(
            cameraMovementComponent,
            KeyboardMovementScrollVelocityComponent(mouse),
            MouseDragRotationComponent(mouse, -.3f)
        )

        this.camera = Camera(projection, components)

        camera.transform.position.set(0f, 0f, 200f)
        camera.transform.lookAt(Position.of(0f, 0f, 0f))

        val world = buildWorld()
        for (x in -5..5) {
            for (z in -5..5) {
                world.createTerrain(Vector2i(x, z))
            }
        }

        val cubeModel = buildCubeModel(world)
        val cube = Node3D(cubeModel)
        val cubeModel2 = buildCubeModel(world)
        cubeModel2.transform.position.addX(-5f)
        cubeModel2.transform.position.addY(5f)
        val cube2 = Node3D(cubeModel2)
        val light = buildDirectionalLight()
        val cubeMap = createCubeMap()
        val renderNode = DeferredRenderNodeGroup(cube, cube2, world)
        this.renderGraph = buildRenderGraph(renderNode, light, cubeMap)
        this.fps = Fps()
    }

    override fun update(window: Window) {
        camera.update()

        val delta = fps.delta() * 1000
        print(
            "\r --> " +
                    "Speed: " + String.format("%.2f", cameraMovementComponent.velocity.x()) +
                    ", Fps: " + String.format("%.2f", fps.fps()) +
                    ", Delta: " + delta
        )
        fps.update()
    }

    override fun render(window: Window) {
        this.screenA.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        this.screenB.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        MainScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        this.renderGraph.render(RenderContext(camera))
    }

    override fun close(window: Window) {
        camera.delete()
        this.renderGraph.delete()
    }

    private fun buildWorld(): LowPolyWorld {
        val heightGenerator = NoiseHeightGenerator(
            LayeredNoise2f(SimplexNoise::noise, 5).spread(50f).multiplied(200f)
        )
        val colourGenerator: ColourGenerator = NormalColourGenerator(
            Vector3.upward(),
            NormalColour(0.5f, Vector3.of(.41f, .41f, .41f)),
            NormalColour(1.0f, Vector3.of(.07f, .52f, .06f))
        )
        val terrainFactory = LowPolyTerrainFactory(
            DiamondMeshGenerator(64), heightGenerator,
            colourGenerator, Vector2.of(256f, 256f)
        )
        return LowPolyWorld(terrainFactory)
    }

    private fun buildCubeModel(world: World): Model3D {
        val cubeInstance = instance()
        cubeInstance.transform.scale.set(10f, 10f, 10f)
        cubeInstance.transform.position.set(101f, world.getHeight(101f, 0f, 50f), 50f)
        val cubeMesh = mesh(
            arrayOf(cubeInstance),
            ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices
        )
        return Model3D(cubeMesh)
    }

    private fun buildDirectionalLight(): DirectionalLight {
        val light = DirectionalLight()
        light.direction.set(-1f, -1f, -1f)
        light.colour.set(1f, 1f, 1f)
        return light
    }

    private fun buildRenderGraph(
        renderNode: DeferredRenderNode,
        light: DirectionalLight,
        cubeMap: CubeMapTexture
    ): RenderGraph {
        val fog = Fog(Vector3.of(0f), MAX_DISTANCE_CLIP * .7f, MAX_DISTANCE_CLIP)

        val screenAAlbedo = MutableTexture2D.create()
        val screenANormalSpecular = MutableTexture2D.create()
        val screenBAlbedo = MutableTexture2D.create()
        val screenBNormalSpecular = MutableTexture2D.create()
        val depthTexture = MutableTexture2D.create()

        this.screenA = ScreenBuilder(Fbo.create(WIDTH, HEIGHT))
            .addColorTexture(screenAAlbedo, InternalFormat.RGBA16F)
            .addColorTexture(screenANormalSpecular, InternalFormat.RGBA16F)
            .addDepthAttachment(TextureAttachmentBuffer(depthTexture, InternalFormat.DEPTH24))
            .build()

        this.screenB = ScreenBuilder(Fbo.create(WIDTH, HEIGHT))
            .addColorTexture(screenBAlbedo, InternalFormat.RGBA16F)
            .addColorTexture(screenBNormalSpecular, InternalFormat.RGBA16F)
            .addDepthAttachment(TextureAttachmentBuffer(depthTexture, InternalFormat.DEPTH24))
            .build()

        return RenderGraph(
            renderNode
                .asDeferredRenderNode()
                .onto(screenA),
            LightRenderPass(
                albedoBuffer = screenAAlbedo,
                normalSpecularBuffer = screenANormalSpecular,
                depthBuffer = depthTexture,
                directionalLights = arrayOf(light),
            ).onto(screenB),
            /*
            // TODO: fix ssao
            SsaoRenderPass()
                .asRenderNode(screenBBuffers)
                .onto(MainScreen),*/
            FogRenderPass(
                albedoBuffer = screenBAlbedo,
                depthBuffer = depthTexture,
                fog,
                FogDistance.XZ
            ).onto(screenA),
            SkyboxPostProcessor(cubeMap)
                .onto(screenA),
            FxaaPostProcessor(screenAAlbedo)
                .onto(MainScreen),
        )
    }

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
}
