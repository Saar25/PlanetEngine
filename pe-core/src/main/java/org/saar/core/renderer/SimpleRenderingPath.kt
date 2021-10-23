package org.saar.core.renderer

import org.saar.core.camera.ICamera
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.core.renderer.renderpass.RenderPassesPipelineHelper
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class SimpleRenderingPath<T : RenderPassBuffers>(
    private val camera: ICamera,
    private val pipeline: RenderingPathPipeline<T>
) : RenderingPath<T> {

    private val screen = Screens.fromPrototype(this.pipeline.prototype, Fbo.create(0, 0))

    private val helper = RenderPassesPipelineHelper(pipeline.passes)

    override fun render(): RenderingOutput<T> {
        this.screen.setAsDraw()
        this.screen.resizeToMainScreen()

        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)

        val context = RenderContext(camera)
        this.helper.process {
            it.prepare(context, this.pipeline.prototype.buffers)
            this.screen.setAsDraw()
            it.render(context, this.pipeline.prototype.buffers)
        }

        return RenderingOutput(this.screen, this.pipeline.prototype.buffers)
    }

    override fun delete() {
        this.screen.delete()
        this.helper.delete()
    }
}