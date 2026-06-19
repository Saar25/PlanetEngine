package org.saar.example.postprocessing

import org.saar.core.camera.Camera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.r2d.Model2D
import org.saar.core.common.r2d.Node2D
import org.saar.core.common.r2d.R2D
import org.saar.core.common.r2d.R2D.vertex
import org.saar.core.common.renderpass.FBMRenderPass
import org.saar.core.common.renderpass.GaussianBlurRenderPass
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.onto
import org.saar.core.renderer.p2d.ScreenPrototype2D
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour.set
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector2.of
import org.saar.maths.utils.Vector3

object PostProcessingExample {
    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        set(.2f, .2f, .2f)

        val camera = Camera(ScreenPerspectiveProjection(70f, .1f, 1000f))
        camera.transform.position.set(0f, 0f, 10f)
        camera.transform.lookAt(Position.of(0f, 0f, 0f))

        val model = buildModel2D()
        val node = Node2D(model)

        val prototype1 = ScreenPrototype2D()
        val screen1 = prototype1.toScreen(Fbo.create(WIDTH, HEIGHT))
        val dsPrototype1 = ScreenPrototype2D()
        val dsScreen1 = dsPrototype1.toScreen(Fbo.create(WIDTH / 8, HEIGHT / 8))
        val dsPrototype2 = ScreenPrototype2D()
        val dsScreen2 = dsPrototype2.toScreen(Fbo.create(WIDTH / 8, HEIGHT / 8))

        val gaussianBlurRenderPass = GaussianBlurRenderPass(21)
        val renderGraph = RenderGraph(
            FBMRenderPass().onto(screen1),
            node.onto(screen1),
            gaussianBlurRenderPass.Vertical(prototype1.albedoTexture).onto(dsScreen1),
            gaussianBlurRenderPass.Horizontal(dsPrototype1.albedoTexture).onto(dsScreen2),
        )

        val keyboard = window.keyboard
        while (window.isOpen && !keyboard.isKeyPressed('E'.code)) {
            renderGraph.render(RenderContext())
            dsScreen2.copyTo(MainScreen)

            window.swapBuffers()
            window.pollEvents()
        }

        screen1.delete()
        dsScreen1.delete()
        dsScreen2.delete()
        renderGraph.delete()
        window.destroy()
    }

    private fun buildModel2D(): Model2D {
        val s = 0.7f
        val vertices = arrayOf(
            vertex(of(-s, -s), Vector3.of(+0.0f, +0.0f, +0.5f)),
            vertex(of(-s, +s), Vector3.of(+0.0f, +1.0f, +0.5f)),
            vertex(of(+s, +s), Vector3.of(+1.0f, +1.0f, +0.5f)),
            vertex(of(+s, -s), Vector3.of(+1.0f, +0.0f, +0.5f))
        )
        val indices = intArrayOf(0, 1, 2, 0, 2, 3)

        val mesh = R2D.mesh(vertices, indices)
        return Model2D(mesh)
    }
}
