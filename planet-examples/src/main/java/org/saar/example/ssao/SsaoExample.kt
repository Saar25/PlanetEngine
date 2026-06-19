package org.saar.example.ssao

import org.saar.core.camera.Camera
import org.saar.core.camera.ICamera
import org.saar.core.camera.Projection
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.SmoothMouseRotationComponent
import org.saar.core.common.components.ThirdPersonViewComponent
import org.saar.core.common.obj.Obj.mesh
import org.saar.core.common.obj.ObjModel
import org.saar.core.common.obj.ObjNode
import org.saar.core.common.obj.ObjNodeBatch
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.NodeBatch3D
import org.saar.core.common.r3d.R3D.instance
import org.saar.core.common.r3d.R3D.mesh
import org.saar.core.common.renderpass.FxaaPostProcessor
import org.saar.core.common.renderpass.MultiplyPostProcessor
import org.saar.core.common.renderpass.SSAOMapGenerator
import org.saar.core.light.DirectionalLight
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.deferred.DeferredNodeRenderPass
import org.saar.core.renderer.deferred.DeferredScreenPrototype
import org.saar.core.renderer.onto
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.clear
import org.saar.example.ExamplesUtils
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour.set
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.transform.Position.Companion.of
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform
import java.util.*

private const val WIDTH = 1200
private const val HEIGHT = 700

fun main() {
    val window = Window.create("SSAO Example - press R to toggle SSAO pass", WIDTH, HEIGHT, true)

    set(.42f, .42f, .42f)

    val camera = buildCamera(window.mouse)

    val geometryPass = buildGeometryPass(camera)

    val light = DirectionalLight()
    light.direction.set(-50f, -50f, -50f)
    light.colour.set(1.0f, 1.0f, 1.0f)

    val depthTexture = MutableTexture2D.create()
    val prototype1 = DeferredScreenPrototype(depthTexture = depthTexture)
    val screen1 = prototype1.toScreen(Fbo.create(window.width, window.height))
    val prototype2 = DeferredScreenPrototype(depthTexture = depthTexture)
    val screen2 = prototype2.toScreen(Fbo.create(window.width, window.height))
    val prototype3 = DeferredScreenPrototype(depthTexture = depthTexture)
    val screen3 = prototype3.toScreen(Fbo.create(window.width, window.height))

    val ssaoRenderGraph = RenderGraph(
        geometryPass.onto(screen1),
        SSAOMapGenerator(
            normalSpecularBuffer = prototype1.normalSpecularTexture,
            depthBuffer = prototype1.depthTexture,
            camera = camera,
            radius = 10f
        ).onto(screen2),
        MultiplyPostProcessor(prototype1.albedoTexture, prototype2.albedoTexture).onto(screen3),
        FxaaPostProcessor(prototype3.albedoTexture).onto(MainScreen)
    )

    val noSsaoRenderGraph = RenderGraph(
        geometryPass.onto(screen1),
        FxaaPostProcessor(prototype1.albedoTexture).onto(MainScreen)
    )

    val ref = object {
        var value = ssaoRenderGraph
    }

    window.keyboard.onKeyPress('R')
        .perform { ref.value = if (ref.value == ssaoRenderGraph) noSsaoRenderGraph else ssaoRenderGraph }

    var current = System.currentTimeMillis()

    while (window.isOpen && !window.keyboard.isKeyPressed('T'.code)) {
        camera.update()

        screen1.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        screen2.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        MainScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        ref.value.render(RenderContext())

        window.swapBuffers()
        window.pollEvents()

        print("\rFps: " + 1000f / (-current + (System.currentTimeMillis().also { current = it })))
    }

    screen1.delete()
    ssaoRenderGraph.delete()
    window.destroy()
}

private fun buildCamera(mouse: Mouse): Camera {
    val projection: Projection = ScreenPerspectiveProjection(70f, 1f, 1000f)

    val center: Transform = SimpleTransform()

    val components = NodeComponentGroup(
        SmoothMouseRotationComponent(mouse, -.3f),
        ThirdPersonViewComponent(center, 80f)
    )

    val camera = Camera(projection, components)

    camera.transform.position.set(0f, 0f, 200f)
    camera.transform.lookAt(of(0f, 0f, 0f))
    return camera
}

private fun buildGeometryPass(camera: ICamera): RenderPass {
    val nodeBatch3D = buildNodeBatch3D()
    val objNodeBatch = buildObjNodeBatch()
    return DeferredNodeRenderPass(camera, nodeBatch3D, objNodeBatch)
}

private fun buildObjNodeBatch(): ObjNodeBatch {
    val cottageModel = Objects.requireNonNull<ObjModel>(loadCottage())
    val cottage = ObjNode(cottageModel)
    return ObjNodeBatch(cottage)
}

private fun buildNodeBatch3D(): NodeBatch3D {
    val cubeInstance = instance()
    cubeInstance.transform.scale.set(10f, 10f, 10f)
    cubeInstance.transform.position.set(0f, 0f, 50f)
    val cubeMesh = mesh(
        arrayOf(cubeInstance),
        ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices
    )
    val cubeModel = Model3D(cubeMesh)
    val cube = Node3D(cubeModel)

    return NodeBatch3D(cube)
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
