package org.saar.example.shadow

import org.saar.core.camera.Camera
import org.saar.core.camera.projection.OrthographicProjection
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.camera.projection.SimpleOrthographicProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.KeyboardMovementScrollVelocityComponent
import org.saar.core.common.components.MouseDragRotationComponent
import org.saar.core.common.obj.Obj.mesh
import org.saar.core.common.obj.ObjModel
import org.saar.core.common.obj.ObjNode
import org.saar.core.common.obj.ObjNodeBatch
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.NodeBatch3D
import org.saar.core.common.r3d.R3D
import org.saar.core.common.renderpass.ShadowsRenderPass
import org.saar.core.light.DirectionalLight
import org.saar.core.node.plusAssign
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.deferred.DeferredNodeRenderPass
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.DeferredScreenPrototype
import org.saar.core.renderer.onto
import org.saar.core.renderer.shadow.*
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.clear
import org.saar.core.util.Fps
import org.saar.example.ExamplesUtils
import org.saar.lwjgl.glfw.window.Window.Companion.create
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.texture.ColorTexture.Companion.of
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.transform.Position.Companion.of

object ShadowExample {
    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = create("Lwjgl", WIDTH, HEIGHT, false)

        val projection = ScreenPerspectiveProjection(70f, 1f, 1000f)

        val cameraMovementComponent = KeyboardMovementComponent(window.keyboard, 50f, 50f, 50f)

        val camera = Camera(projection).apply {
            components += cameraMovementComponent
            components += KeyboardMovementScrollVelocityComponent(window.mouse)
            components += MouseDragRotationComponent(window.mouse, -.3f)

            transform.position.set(0f, 0f, 200f)
            transform.lookAt(of(0f, 0f, 0f))
        }

        val nodeBatch3D = buildNodeBatch3D()

        val objNodeBatch = buildObjNodeBatch()

        val light = DirectionalLight().apply {
            direction.set(-1f, -1f, -1f)
            color.set(1f, 1f, 1f)
        }

        val shadowProjection: OrthographicProjection = SimpleOrthographicProjection(
            -100f, 100f, -100f, 100f, -100f, 100f
        )
        val shadowsPrototype = ShadowsScreenPrototype()
        val shadowsScreen = shadowsPrototype.toScreen(
            Fbo.create(), ShadowsQuality.MEDIUM.imageSize, ShadowsQuality.MEDIUM.imageSize,
        )
        val shadowsCamera = ShadowsCamera(shadowProjection, light)

        val shadowMap = shadowsPrototype.depthTexture

        val shadowsRenderGraph = RenderGraph(
            ShadowsRenderNodeGroup(nodeBatch3D, objNodeBatch)
                .asShadowsRenderPass(shadowsCamera).onto(shadowsScreen)
        )
        shadowsScreen.clear(GlBuffer.COLOR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        shadowsRenderGraph.render(RenderContext())

        val renderNode = DeferredRenderNodeGroup(nodeBatch3D, objNodeBatch)

        val prototype = DeferredScreenPrototype()
        val screen = prototype.toScreen(
            Fbo.create(),
            window.width,
            window.height,
        )

        val renderGraph = RenderGraph(
            DeferredNodeRenderPass(camera, renderNode).onto(screen),
            ShadowsRenderPass(
                prototype.albedoTexture,
                prototype.normalSpecularTexture,
                prototype.depthTexture,
                shadowsCamera,
                camera,
                shadowMap,
                light
            ).onto(MainScreen)
        )

        val fps = Fps()
        while (window.isOpen && !window.keyboard.isKeyPressed('T'.code)) {
            camera.update()

            screen.clear(GlBuffer.COLOR, GlBuffer.DEPTH, GlBuffer.STENCIL)
            MainScreen.clear(GlBuffer.COLOR, GlBuffer.DEPTH, GlBuffer.STENCIL)
            renderGraph.render(RenderContext())

            window.swapBuffers()
            window.pollEvents()

            val delta = fps.delta() * 1000

            print(
                "\r --> " +
                        "Speed: " + String.format("%.2f", cameraMovementComponent.velocity.x()) +
                        ", Fps: " + String.format("%.2f", fps.fps()) +
                        ", Delta: " + delta
            )
            fps.update()
        }

        camera.delete()
        shadowsScreen.delete()
        shadowsRenderGraph.delete()
        screen.delete()
        renderGraph.delete()
        window.destroy()
    }

    private fun buildNodeBatch3D(): NodeBatch3D {
        val cubeInstance = R3D.instance().apply {
            transform.scale.set(10f, 10f, 10f)
            transform.position.set(0f, 0f, 50f)
        }
        val cubeMesh = R3D.mesh(
            arrayOf(cubeInstance),
            ExamplesUtils.cubeVertices,
            ExamplesUtils.cubeIndices
        )
        val cubeModel = Model3D(cubeMesh)
        val cube = Node3D(cubeModel)

        return NodeBatch3D(cube)
    }

    private fun buildObjNodeBatch(): ObjNodeBatch {
        val cottageModel = loadCottage()
        val cottage = ObjNode(cottageModel)

        val dragonModel = loadDragon().apply {
            transform.position.set(50f, 0f, 0f)
        }
        val dragon = ObjNode(dragonModel)

        val stallModel = loadStall().apply {
            transform.position.set(-50f, 0f, 0f)
            transform.rotation.rotateDegrees(0f, 180f, 0f)
        }
        val stall = ObjNode(stallModel)

        return ObjNodeBatch(cottage, dragon, stall)
    }

    private fun loadCottage(): ObjModel {
        val mesh = mesh("/assets/cottage/cottage.obj")
        val texture = Texture2D.of("/assets/cottage/cottage_diffuse.png")
        return ObjModel(mesh, texture)
    }

    private fun loadStall(): ObjModel {
        val mesh = mesh("/assets/stall/stall.model.obj")
        val texture = Texture2D.of("/assets/stall/stall.diffuse.png")
        return ObjModel(mesh, texture)
    }

    private fun loadDragon(): ObjModel {
        val mesh = mesh("/assets/dragon/dragon.model.obj")
        val texture: ReadOnlyTexture = of(255, 215, 0, 255)
        return ObjModel(mesh, texture)
    }
}
