package org.saar.core.renderer

import org.saar.core.camera.ICamera
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.depth.DepthMask
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.stencil.*
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class SimpleRenderingPath<T : RenderPassBuffers>(
    private val camera: ICamera,
    private val pipeline: RenderingPathPipeline<T>,
    private val prototype: RenderingPathScreenPrototype<T>,
) : RenderingPath<T> {

    private val stencilState = StencilState(StencilOperation.ALWAYS_KEEP,
        StencilFunction(Comparator.NOT_EQUAL, 0, 0xFF), StencilMask.UNCHANGED)

    private val screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0))

    override fun render(): RenderingOutput<T> {
        this.screen.setAsDraw()
        this.screen.resizeToMainScreen()

        DepthTest.apply(mask = DepthMask.WRITE)
        StencilTest.apply(mask = StencilMask.UNCHANGED)
        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)

        val context = RenderContext(camera)
        this.pipeline.passes.forEach {
            StencilTest.apply(this.stencilState)
            DepthTest.disable()
            BlendTest.disable()

            it.prepare(context, this.prototype.buffers)
            this.screen.setAsDraw()
            it.render(context, this.prototype.buffers)
        }

        return RenderingOutput(this.screen, this.prototype.buffers)
    }

    override fun delete() {
        this.screen.delete()
        this.pipeline.passes.forEach { it.delete() }
    }
}