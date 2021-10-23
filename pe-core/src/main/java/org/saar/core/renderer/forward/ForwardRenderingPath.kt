package org.saar.core.renderer.forward

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderingOutput
import org.saar.core.renderer.RenderingPath
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassesPipelineHelper
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class ForwardRenderingPath(
    private val camera: ICamera,
    pipeline: ForwardRenderPassesPipeline
) : RenderingPath<ForwardRenderingBuffers> {

    private val prototype = ForwardScreenPrototype()

    private val screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0))

    private val helper = RenderPassesPipelineHelper(pipeline.renderPasses)

    override fun render(): RenderingOutput<ForwardRenderingBuffers> {
        this.screen.setAsDraw()
        this.screen.resizeToMainScreen()

        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)

        val context = RenderPassContext(this.camera)
        this.helper.process {
            it.prepare(context, this.prototype.buffers)
            this.screen.setAsDraw()
            it.render(context, this.prototype.buffers)
        }

        return RenderingOutput(this.screen, this.prototype.buffers)
    }

    override fun delete() {
        this.screen.delete()
        this.helper.delete()
    }
}