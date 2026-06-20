package org.saar.example.reflected

import org.joml.primitives.Planef
import org.saar.core.camera.Camera
import org.saar.core.camera.Projection
import org.saar.core.camera.ReadonlyCamera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.camera.projection.SimpleOrthographicProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.KeyboardMovementScrollVelocityComponent
import org.saar.core.common.components.MouseDragRotationComponent
import org.saar.core.common.flatreflected.FlatReflected
import org.saar.core.common.flatreflected.FlatReflected.vertex
import org.saar.core.common.flatreflected.FlatReflectedModel
import org.saar.core.common.flatreflected.FlatReflectedNode
import org.saar.core.common.obj.Obj.mesh
import org.saar.core.common.obj.ObjModel
import org.saar.core.common.obj.ObjNode
import org.saar.core.common.obj.ObjNodeBatch
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.NodeBatch3D
import org.saar.core.common.r3d.R3D
import org.saar.core.common.renderpass.ContrastPostProcessor
import org.saar.core.common.renderpass.LightRenderPass
import org.saar.core.common.renderpass.ShadowsRenderPass
import org.saar.core.light.DirectionalLight
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.DeferredScreenPrototype
import org.saar.core.renderer.deferred.asDeferredRenderPass
import org.saar.core.renderer.onto
import org.saar.core.renderer.shadow.*
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.clear
import org.saar.core.util.Fps
import org.saar.example.ExamplesUtils
import org.saar.gui.UIBlock
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.font.FontLoader
import org.saar.gui.font.FontLoader.loadFont
import org.saar.gui.style.Colours
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.arrangement.ArrangementValues
import org.saar.gui.style.axisalignment.AxisAlignmentValues
import org.saar.gui.style.coordinate.CoordinateValues.pixelsEnd
import org.saar.gui.style.length.LengthValues.ratio
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.texture.ColourTexture.Companion.of
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.Angle.Companion.degrees
import org.saar.maths.transform.Position
import org.saar.maths.transform.ReflectedTransform
import org.saar.maths.utils.Vector3
import java.util.*

private const val WIDTH = 1200
private const val HEIGHT = 700

fun main() {
    val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

    ClearColour.set(0.392f, 0.584f, 0.929f)

    val uiDisplay = UIDisplay(window).apply {
        style.alignment.value = AlignmentValues.horizontal
        style.arrangement.value = ArrangementValues.spaceAround
        style.axisAlignment.value = AxisAlignmentValues.center
    }

    val reflectionUiBlock = UIBlock().apply {
        style.x.set(pixelsEnd((30)))
        style.y.set(30)
        style.width.set(300)
        style.height.set(
            ratio(HEIGHT.toFloat() / WIDTH)
        )
        style.borders.set(1)
        style.borderColour.set(Colours.PURPLE)
    }

    uiDisplay.add(reflectionUiBlock)

    val font = loadFont(
        FontLoader.DEFAULT_FONT_FAMILY, 22f, 512, 512, "? .FpsDeltaSpeed:0123456789"
    )

    val uiTextGroup = UIElement().apply {
        style.font.set(font)
        style.fontSize.set(22)
        style.fontColour.set(Colours.WHITE)
        style.alignment.value = AlignmentValues.vertical
    }

    val uiFps = UIText("Fps: ???")
    uiTextGroup.add(uiFps)

    val uiSpeed = UIText("Speed: ???")
    uiTextGroup.add(uiSpeed)

    val uiDelta = UIText("Delta: ???")
    uiTextGroup.add(uiDelta)

    uiDisplay.add(uiTextGroup)

    val projection: Projection = ScreenPerspectiveProjection(70f, 1f, 300f)

    val cameraMovementComponent = KeyboardMovementComponent(window.keyboard, 50f, 50f, 50f)
    val components = NodeComponentGroup(
        cameraMovementComponent,
        KeyboardMovementScrollVelocityComponent(window.mouse),
        MouseDragRotationComponent(window.mouse, -.3f)
    )

    val camera = Camera(projection, components).apply {
        transform.position.set(0f, 25f, 100f)
        transform.lookAt(Position.of(0f, 0f, 0f))
    }

    val objNodeBatch = buildObjNodeBatch()

    val nodeBatch3D = buildNodeBatch3D()

    val reflectionRenderNode = DeferredRenderNodeGroup(objNodeBatch, nodeBatch3D)
    val shadowsRenderNode = ShadowsRenderNodeGroup(objNodeBatch, nodeBatch3D)

    val light = buildDirectionalLight()

    val reflectedTransform = ReflectedTransform(camera.transform, Planef(0f, 1f, 0f, 1f).normalize())
    val reflectionCamera = ReadonlyCamera(camera.projection, reflectedTransform)

    val reflectionDepthTexture = MutableTexture2D.create()
    val reflectionPrototype1 = DeferredScreenPrototype(depthTexture = reflectionDepthTexture)
    val reflectionScreen1 = reflectionPrototype1.toScreen(Fbo.create(WIDTH, HEIGHT))

    val reflectionPrototype2 = DeferredScreenPrototype(depthTexture = reflectionDepthTexture)
    val reflectionScreen2 = reflectionPrototype2.toScreen(Fbo.create(WIDTH, HEIGHT))

    val reflectionRenderGraph = RenderGraph(
        reflectionRenderNode.asDeferredRenderPass(reflectionCamera).onto(reflectionScreen2),
        LightRenderPass(
            albedoBuffer = reflectionPrototype1.albedoTexture,
            normalSpecularBuffer = reflectionPrototype1.normalSpecularTexture,
            depthBuffer = reflectionPrototype1.depthTexture,
            camera = reflectionCamera,
            directionalLights = arrayOf(light)
        ).onto(reflectionScreen2)
    )
    val reflectionMap = reflectionPrototype2.albedoTexture
    reflectionUiBlock.style.backgroundImage.set(reflectionMap)

    val mirrorModel = buildMirrorModel(reflectionMap)
    val mirror = FlatReflectedNode(mirrorModel)

    val shadowsProjection = 100f.let { SimpleOrthographicProjection(-it, it, -it, it, -it, it) }
    val shadowsCamera = ShadowsCamera(shadowsProjection, light)
    val shadowsPrototype = ShadowsScreenPrototype()
    val shadowsScreen =
        shadowsPrototype.toScreen(Fbo.create(ShadowsQuality.HIGH.imageSize, ShadowsQuality.HIGH.imageSize))
    val shadowsRenderGraph = RenderGraph(
        shadowsRenderNode.asShadowsRenderPass(camera).onto(shadowsScreen)
    )

    val renderNode = DeferredRenderNodeGroup(
        objNodeBatch, nodeBatch3D, mirror
    )

    val shadowMap = shadowsPrototype.depthTexture
//    val fog = Fog(Vector3.of(.2f), 1000f, 2000f)

    val prototype1 = DeferredScreenPrototype()
    val screen1 = prototype1.toScreen(Fbo.create(WIDTH, HEIGHT))

    val prototype2 = DeferredScreenPrototype()
    val screen2 = prototype2.toScreen(Fbo.create(WIDTH, HEIGHT))

    val renderGraph = RenderGraph(
        renderNode.asDeferredRenderPass(camera).onto(screen1),
        ShadowsRenderPass(
            albedoBuffer = prototype1.albedoTexture,
            normalSpecularBuffer = prototype1.normalSpecularTexture,
            depthBuffer = prototype1.depthTexture,
            shadowsCamera = shadowsCamera,
            camera = camera,
            shadowMap,
            light
        ).onto(screen2),
        ContrastPostProcessor(prototype2.albedoTexture, 1.3f).onto(MainScreen),
//        FogRenderPass(fog, FogDistance.XYZ).asRenderNode(prototype1.buffers).onto(MainScreen),
        uiDisplay.onto(MainScreen)
    )

    val fps = Fps()
    while (window.isOpen && !window.keyboard.isKeyPressed('T'.code)) {
        renderNode.update()
        camera.update()
        uiDisplay.update()

        reflectionScreen1.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        reflectionScreen2.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        shadowsScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen1.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen2.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        MainScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)

        reflectionRenderGraph.render(RenderContext())
        shadowsRenderGraph.render(RenderContext())
        renderGraph.render(RenderContext())

        window.swapBuffers()
        window.pollEvents()

        uiFps.text = String.format("Fps: %.2f", fps.fps())
        uiSpeed.text = String.format("Speed: %.2f", cameraMovementComponent.velocity.x())
        uiDelta.text = String.format("Delta: %d", (fps.delta() * 1000).toLong())

        fps.update()
    }

    camera.delete()
    uiDisplay.delete()
    reflectionScreen1.delete()
    reflectionScreen2.delete()
    reflectionRenderGraph.delete()
    shadowsScreen.delete()
    shadowsRenderNode.delete()
    screen1.delete()
    screen2.delete()
    renderGraph.delete()
    window.destroy()
}

private fun buildNodeBatch3D(): NodeBatch3D {
    val cubeModel = buildCubeModel()
    val cube = Node3D(cubeModel)

    return NodeBatch3D(cube)
}

private fun buildObjNodeBatch(): ObjNodeBatch {
    val cottageModel = buildCottageModel()
    val cottage = ObjNode(cottageModel)

    val dragonModel = buildDragonModel()
    val dragon = ObjNode(dragonModel)

    val stallModel = buildStallModel()
    val stall = ObjNode(stallModel)

    return ObjNodeBatch(cottage, dragon, stall)
}

private fun buildMirrorModel(reflectionTexture: ReadOnlyTexture2D): FlatReflectedModel {
    val vertices = arrayOf(
        vertex(Vector3.of(-0.5f, +0.5f, -0.5f)),  // 0
        vertex(Vector3.of(-0.5f, +0.5f, +0.5f)),  // 1
        vertex(Vector3.of(+0.5f, +0.5f, +0.5f)),  // 2
        vertex(Vector3.of(+0.5f, +0.5f, -0.5f)),  // 3
    )
    val mesh = FlatReflected.mesh(vertices, intArrayOf(0, 1, 2, 0, 2, 3))

    val mirror = FlatReflectedModel(mesh, Vector3.upward()).apply {
        transform.position.set(0f, .1f, 0f)
        transform.scale.scale(100f, 0f, 100f)
        reflectionMap = reflectionTexture
    }
    return mirror
}

private fun buildDragonModel(): ObjModel {
    val dragonModel = Objects.requireNonNull<ObjModel>(loadDragon())
    dragonModel.transform.position.set(50f, 0f, 0f)
    return dragonModel
}

private fun buildCottageModel(): ObjModel {
    return Objects.requireNonNull<ObjModel>(loadCottage())
}

private fun buildStallModel(): ObjModel {
    val stallModel = Objects.requireNonNull<ObjModel>(loadStall()).apply {
        transform.position.set(-50f, 0f, 0f)
        transform.rotation.rotate(0f.degrees, 180f.degrees, 0f.degrees)
    }
    return stallModel
}

private fun buildCubeModel(): Model3D {
    val cubeInstance = R3D.instance().apply {
        transform.scale.set(10f, 10f, 10f)
        transform.position.set(0f, 0f, 50f)
    }
    val cubeMesh = R3D.mesh(
        arrayOf(cubeInstance), ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices
    )
    return Model3D(cubeMesh)
}

private fun buildDirectionalLight(): DirectionalLight {
    val light = DirectionalLight()
    light.direction.set(-1f, -1f, -1f)
    light.colour.set(1f, 1f, 1f)
    return light
}

private fun loadCottage(): ObjModel? {
    try {
        val mesh = mesh("/assets/cottage/cottage.obj")
        val texture = Texture2D.of("/assets/cottage/cottage_diffuse.png")
        return ObjModel(mesh, texture)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

private fun loadStall(): ObjModel? {
    try {
        val mesh = mesh("/assets/stall/stall.model.obj")
        val texture = Texture2D.of("/assets/stall/stall.diffuse.png")
        return ObjModel(mesh, texture)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

private fun loadDragon(): ObjModel? {
    try {
        val mesh = mesh("/assets/dragon/dragon.model.obj")
        val texture: ReadOnlyTexture = of(255, 215, 0, 255)
        return ObjModel(mesh, texture)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}