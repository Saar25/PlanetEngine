package org.saar.example.bloom

import org.saar.core.common.r2d.Model2D
import org.saar.core.common.r2d.Node2D
import org.saar.core.common.r2d.R2D
import org.saar.core.common.r2d.R2D.vertex
import org.saar.core.common.renderpass.AdditiveBlendPostProcessor
import org.saar.core.common.renderpass.BrightPassRenderPass
import org.saar.core.common.renderpass.FBMRenderPass
import org.saar.core.common.renderpass.GaussianBlurRenderPass
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.onto
import org.saar.core.renderer.p2d.ScreenPrototype2D
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColor
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3

object BloomExample {
    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Bloom", WIDTH, HEIGHT, true)

        ClearColor.set(.1f, .1f, .1f)

        val model = buildModel2D()
        val node = Node2D(model)

        val prototype1 = ScreenPrototype2D()
        val screen1 = prototype1.toScreen(Fbo.create(WIDTH, HEIGHT))

        val prototypeBright = ScreenPrototype2D()
        val screenBright = prototypeBright.toScreen(Fbo.create(WIDTH / 4, HEIGHT / 4))

        val prototypeBlurV = ScreenPrototype2D()
        val screenBlurV = prototypeBlurV.toScreen(Fbo.create(WIDTH / 4, HEIGHT / 4))

        val prototypeBlurH = ScreenPrototype2D()
        val screenBlurH = prototypeBlurH.toScreen(Fbo.create(WIDTH / 4, HEIGHT / 4))

        val gaussianBlur = GaussianBlurRenderPass(15)
        val keyboard = window.keyboard

        val renderGraph = RenderGraph(
            FBMRenderPass().onto(screen1),
            node.onto(screen1),
            BrightPassRenderPass(prototype1.albedoTexture).onto(screenBright),
            gaussianBlur.Vertical(prototypeBright.albedoTexture).onto(screenBlurV),
            gaussianBlur.Horizontal(prototypeBlurV.albedoTexture).onto(screenBlurH),
            AdditiveBlendPostProcessor(prototype1.albedoTexture, prototypeBlurH.albedoTexture).onto(MainScreen),
        )

        while (window.isOpen && !keyboard.isKeyPressed('E'.code)) {
            renderGraph.render(RenderContext())

            window.swapBuffers()
            window.pollEvents()
        }

        renderGraph.delete()
        screen1.delete()
        screenBright.delete()
        screenBlurV.delete()
        screenBlurH.delete()
        window.destroy()
    }

    private fun buildModel2D(): Model2D {
        val s = 0.9f
        val vertices = arrayOf(
            vertex(Vector2.of(-s * .35f, -s * .35f), Vector3.of(+2.0f, +2.0f, +1.5f)),
            vertex(Vector2.of(-s * .35f, +s * .35f), Vector3.of(+2.0f, +2.0f, +1.5f)),
            vertex(Vector2.of(+s * .35f, +s * .35f), Vector3.of(+2.0f, +2.0f, +1.5f)),
            vertex(Vector2.of(+s * .35f, -s * .35f), Vector3.of(+2.0f, +2.0f, +1.5f)),
//            vertex(Vector2.of(-s, -s), Vector3.of(+0.3f, +0.5f, +0.7f)),
//            vertex(Vector2.of(-s, +s), Vector3.of(+0.3f, +0.5f, +0.7f)),
//            vertex(Vector2.of(+s, +s), Vector3.of(+0.3f, +0.5f, +0.7f)),
//            vertex(Vector2.of(+s, -s), Vector3.of(+0.3f, +0.5f, +0.7f)),
        )
        val indices = intArrayOf(
//            4, 5, 6, 4, 6, 7,
            0, 1, 2, 0, 2, 3,
        )

        val mesh = R2D.mesh(vertices, indices)
        return Model2D(mesh)
    }
}
