package org.saar.minecraft

import org.joml.SimplexNoise
import org.jproperty.ChangeListener
import org.jproperty.property.SimpleFloatProperty
import org.saar.core.camera.Camera
import org.saar.core.camera.Projection
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.MouseRotationComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.node.NodeComponentGroup
import org.saar.core.screen.MainScreen
import org.saar.core.util.Fps
import org.saar.gui.*
import org.saar.gui.style.Colour
import org.saar.gui.style.Colours
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.coordinate.CoordinateValues
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.length.LengthValues.ratio
import org.saar.gui.style.position.PositionValues
import org.saar.lwjgl.glfw.input.mouse.MouseCursor
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.constants.Face
import org.saar.lwjgl.opengl.polygonmode.PolygonMode
import org.saar.lwjgl.opengl.polygonmode.PolygonModeValue
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.lwjgl.opengl.texture.parameter.TextureAnisotropicFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue
import org.saar.maths.JomlOperators.component1
import org.saar.maths.JomlOperators.component2
import org.saar.maths.JomlOperators.component3
import org.saar.maths.noise.Noise3f
import org.saar.maths.transform.Position
import org.saar.minecraft.chunk.ChunkRenderer
import org.saar.minecraft.chunk.WaterRenderer
import org.saar.minecraft.components.*
import org.saar.minecraft.entity.HitBoxes.player
import org.saar.minecraft.generator.*
import org.saar.minecraft.threading.GlThreadQueue
import kotlin.concurrent.fixedRateTimer

private const val WIDTH = 1200
private const val HEIGHT = 700

private const val SPEED = 5f
private const val MOUSE_DELAY = 200
private const val MOUSE_SENSITIVITY = .2f

private const val WORLD_RADIUS = 5
private const val THREAD_COUNT = 5

private const val HIGH_QUALITY = false

private const val FLY_MODE = true

private const val TEXTURE_ATLAS_PATH = "/minecraft/atlas.png"

private var uiMode = false

private val fpsProperty = SimpleFloatProperty(0f)

private lateinit var window: Window
private lateinit var camera: Camera
private lateinit var world: World

fun main() {
    window = Window.create("Lwjgl", WIDTH, HEIGHT, true)
    ClearColour.set(.0f, .5f, .7f)

    world = buildWorld()
    generateWorld()

    camera = buildCamera(window, world).apply {
        val height = world.getHeight(0, 0) + 10f
        transform.position.set(0f, height, 0f)
    }

    val uiDisplay = buildDisplay()
    val inventory = buildInventory()

    val rendering =
        if (HIGH_QUALITY) MinecraftDeferredRendering(uiDisplay, world, camera, WORLD_RADIUS)
        else MinecraftForwardRendering(uiDisplay, world, camera, WORLD_RADIUS)
    val renderingPath = rendering.buildRenderingPath()

    val atlas = createAtlas().also {
        ChunkRenderer.atlas = it
        WaterRenderer.atlas = it
    }

    window.mouse.hide()

    window.keyboard.onKeyPress('R').perform {
        PolygonMode.set(Face.FRONT_AND_BACK, PolygonModeValue.LINE)
    }
    window.keyboard.onKeyRelease('R'.code).perform {
        PolygonMode.set(Face.FRONT_AND_BACK, PolygonModeValue.FILL)
    }
    window.keyboard.onKeyPress('E').perform {
        uiMode = !uiMode
        if (uiMode) {
            window.mouse.cursor = MouseCursor.NORMAL
            uiDisplay.add(inventory)
        } else {
            window.mouse.cursor = MouseCursor.DISABLED
            uiDisplay.remove(inventory)
        }
    }

    val fps = Fps()
    fixedRateTimer(daemon = true, period = 1000) {
        fpsProperty.value = fps.fps()
    }

    while (window.isOpen && !window.keyboard.isKeyPressed('T'.code)) {
        GlThreadQueue.getInstance().run()

        camera.update()
        uiDisplay.update()
        rendering.update()

        renderingPath.render().toMainScreen()

        window.swapBuffers()
        window.pollEvents()

        fps.update()
    }

    atlas.delete()
    world.delete()
    renderingPath.delete()
    window.destroy()
}

private fun buildDisplay(): UIDisplay {
    return UIDisplay(window).apply {
        +UIElement().apply {
            style.alignment.value = AlignmentValues.vertical
            style.fontSize.set(24)
            style.backgroundColour.set(Colour(255, 255, 255, .5f))
            style.borderColour.set(Colours.BLACK)
            style.borders.set(2)
            style.radius.set(5)
            style.margin.set(10)
            style.padding.set(10)

            +UIText("Fps: ???").apply {
                fpsProperty.addListener(ChangeListener {
                    text = String.format("Fps: %.3f", it.newValue)
                })
            }

            +UIText("Position: ???").apply {
                camera.transform.position.addListener(ChangeListener {
                    val (x, y, z) = camera.transform.position.value
                    text = String.format("Position: (%.3f,%.3f,%.3f)", x, y, z)
                })
            }

            +UIText("Chunk: ???").apply {
                camera.transform.position.addListener(ChangeListener {
                    val x = World.worldToChunkCoordinate(camera.transform.position.x.toInt())
                    val z = World.worldToChunkCoordinate(camera.transform.position.z.toInt())
                    text = String.format("Chunk (%d, %d)", x, z)
                })
            }
        }

        +UIBlock().apply {
            style.borderColour.set(Colours.DARK_GRAY)
            style.borders.set(2)
            style.width.set(6)
            style.height.set(6)
            style.backgroundColour.set(Colour(255, 255, 255, .2f))
            style.x.set(CoordinateValues.center)
            style.y.set(CoordinateValues.center)
            style.position.value = PositionValues.absolute
        }
    }
}

private fun buildInventory(): UIChildNode {
    return UIElement().apply {
        style.position.value = PositionValues.absolute
        style.x.set(CoordinateValues.center)
        style.y.set(CoordinateValues.center)
        style.width.set(percent(50f))
        style.height.set(ratio(.8f))
        style.alignment.value = AlignmentValues.vertical
        style.radius.set(10)
        style.padding.set(10)
        style.borderColour.set(Colours.TRANSPARENT)
        style.backgroundColour.set(Colours.GRAY)

        +UIElement().apply {
            style.height.set(percent(40f))
        }

        +UIElement().apply {
            style.alignment.value = AlignmentValues.vertical

            repeat(4) { row ->
                +UIElement().apply {
                    style.width.set(percent(100f))
                    if (row == 3) style.margin.set(10, 0, 0, 0)

                    repeat(9) {
                        +UIBlock().apply {
                            style.width.value = percent(10f)
                            style.height.value = ratio(1f)
                            style.borderColour.set(Colours.DARK_GRAY)
                            style.borders.set(3)
                        }
                    }
                }
            }
        }
    }
}

private fun buildWorld(): World {
    val noise3f = Noise3f { x: Float, y: Float, z: Float -> SimplexNoise.noise(x / 32f, y / 32f, z / 32f) }
    val generator: WorldGenerator = WorldGenerationPipeline
        .pipe(BedrockGenerator())
        .then(Terrain3DGenerator(60, 140, noise3f))
        .then(WaterGenerator(100))
        .then(TreesGenerator { x: Float, y: Float -> SimplexNoise.noise(x, y) })
    return World(generator, THREAD_COUNT)
}

private fun generateWorld() {
    val worldGeneratingFuture = world.generateAround(
        Position.of(0f, 0f, 0f), WORLD_RADIUS)
    while (!worldGeneratingFuture.isDone) {
        window.swapBuffers()
        window.pollEvents()
        Thread.sleep(100)
    }
}

private fun buildCamera(window: Window, world: World): Camera {
    val projection: Projection = ScreenPerspectiveProjection(MainScreen, 70f, .20f, 500f)
    val cameraComponents = if (FLY_MODE) NodeComponentGroup(
        GenerateAroundComponent(world, WORLD_RADIUS),
        MouseRotationComponent(window.mouse, -MOUSE_SENSITIVITY),
        PlayerBuildingComponent(window.mouse, world, MOUSE_DELAY.toLong()),
        PlayerWalkingComponent(window.keyboard, SPEED),
        PlayerFlyingComponent(window.keyboard, SPEED),
        CollisionComponent(world, player),
        VelocityComponent()
    ) else NodeComponentGroup(
        GenerateAroundComponent(world, WORLD_RADIUS),
        MouseRotationComponent(window.mouse, -MOUSE_SENSITIVITY),
        PlayerBuildingComponent(window.mouse, world, MOUSE_DELAY.toLong()),
        PlayerWalkingComponent(window.keyboard, SPEED),
        GravityComponent(),
        PlayerJumpComponent(window.keyboard),
        WaterFloatingComponent(),
        CollisionComponent(world, player),
        VelocityComponent()
    )
    return Camera(projection, cameraComponents)
}

private fun createAtlas(): Texture2D {
    return Texture2D.of(TEXTURE_ATLAS_PATH).apply {
        applyParameters(arrayOf(
            TextureMagFilterParameter(MagFilterValue.NEAREST),
            TextureMinFilterParameter(MinFilterValue.NEAREST_MIPMAP_LINEAR),
            TextureAnisotropicFilterParameter(8f)
        ))
        generateMipmap()
    }
}