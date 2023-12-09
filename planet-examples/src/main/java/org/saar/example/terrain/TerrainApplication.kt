package org.saar.example.terrain

import org.joml.SimplexNoise
import org.joml.Vector2i
import org.saar.core.camera.Camera
import org.saar.core.camera.ICamera
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
import org.saar.core.common.terrain.height.HeightGenerator
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
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.postprocessing.processors.SkyboxPostProcessor
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.core.renderer.deferred.passes.LightRenderPass
import org.saar.core.renderer.deferred.passes.SsaoRenderPass
import org.saar.core.renderer.forward.passes.FogRenderPass
import org.saar.core.util.Fps
import org.saar.example.ExamplesUtils
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.texture.CubeMapTexture
import org.saar.lwjgl.opengl.texture.CubeMapTextureBuilder
import org.saar.maths.noise.LayeredNoise2f
import org.saar.maths.noise.MultipliedNoise2f
import org.saar.maths.noise.SpreadNoise2f
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
    private lateinit var renderingPath: DeferredRenderingPath
    private lateinit var cameraMovementComponent: KeyboardMovementComponent

    override fun initialize(window: Window) {
        window.width = WIDTH
        window.height = HEIGHT

        ClearColour.set(.0f, .7f, .8f)

        val keyboard = window.keyboard
        val mouse = window.mouse

        val projection: Projection = ScreenPerspectiveProjection(70f, 1f, MAX_DISTANCE_CLIP)
        this.cameraMovementComponent = KeyboardMovementComponent(keyboard, 50f, 50f, 50f)
        val components = NodeComponentGroup(cameraMovementComponent,
            KeyboardMovementScrollVelocityComponent(mouse),
            MouseDragRotationComponent(mouse, -.3f))

        this.camera = Camera(projection, components)

        camera.transform.position[0f, 0f] = 200f
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
        this.renderingPath = buildRenderingPath(camera, renderNode, light, cubeMap)
        this.fps = Fps()
    }

    override fun update(window: Window) {
        camera.update()

        val delta = fps.delta() * 1000
        print("\r --> " +
                "Speed: " + String.format("%.2f", cameraMovementComponent.velocity.x()) +
                ", Fps: " + String.format("%.2f", fps.fps()) +
                ", Delta: " + delta)
        fps.update()
    }

    override fun render(window: Window) {
        renderingPath.render().toMainScreen()
    }

    override fun close(window: Window) {
        camera.delete()
        renderingPath.delete()
    }

    private fun buildWorld(): LowPolyWorld {
        val heightGenerator: HeightGenerator = NoiseHeightGenerator(
            MultipliedNoise2f(200, SpreadNoise2f(50,
                LayeredNoise2f({ x: Float, y: Float -> SimplexNoise.noise(x, y) }, 5)))
        )
        val colourGenerator: ColourGenerator = NormalColourGenerator(Vector3.upward(),
            NormalColour(0.5f, Vector3.of(.41f, .41f, .41f)),
            NormalColour(1.0f, Vector3.of(.07f, .52f, .06f)))
        val terrainFactory = LowPolyTerrainFactory(
            DiamondMeshGenerator(64), heightGenerator,
            colourGenerator, Vector2.of(256f, 256f)
        )
        return LowPolyWorld(terrainFactory)
    }

    private fun buildCubeModel(world: World): Model3D {
        val cubeInstance = instance()
        cubeInstance.transform.scale[10f, 10f] = 10f
        cubeInstance.transform.position[101f, world.getHeight(101f, 0f, 50f)] = 50f
        val cubeMesh = mesh(arrayOf(cubeInstance),
            ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices)
        return Model3D(cubeMesh)
    }

    private fun buildDirectionalLight(): DirectionalLight {
        val light = DirectionalLight()
        light.direction[-1f, -1f] = -1f
        light.colour[1f, 1f] = 1f
        return light
    }

    private fun buildRenderingPath(camera: ICamera, renderNode: DeferredRenderNode,
                                   light: DirectionalLight, cubeMap: CubeMapTexture): DeferredRenderingPath {
        val fog = Fog(Vector3.of(0f), MAX_DISTANCE_CLIP * .7f, MAX_DISTANCE_CLIP)
        val renderPassesPipeline = DeferredRenderingPipeline(
            DeferredGeometryPass(renderNode),
            LightRenderPass(light),
            SsaoRenderPass(),
            FogRenderPass(fog, FogDistance.XZ),
            SkyboxPostProcessor(cubeMap),
            FxaaPostProcessor()
        )
        return DeferredRenderingPath(camera, renderPassesPipeline)
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
