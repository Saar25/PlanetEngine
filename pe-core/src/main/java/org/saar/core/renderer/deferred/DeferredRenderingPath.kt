package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContextBase
import org.saar.core.renderer.RenderingOutput
import org.saar.core.renderer.RenderingPath
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.stencil.*
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class DeferredRenderingPath(
    private val camera: ICamera,
    private val renderNode: DeferredRenderNode,
    private val pipeline: DeferredRenderPassesPipeline) : RenderingPath<DeferredRenderingBuffers> {

    private val prototype = DeferredScreenPrototype()

    private val screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0))

    private val stencilState = StencilState(StencilOperation.REPLACE_ON_PASS,
        StencilFunction(Comparator.ALWAYS, 1, 0xFF), StencilMask.UNCHANGED)

    override fun render(): RenderingOutput<DeferredRenderingBuffers> {
        this.screen.setAsDraw()
        this.screen.resizeToMainScreen()

        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        StencilTest.apply(this.stencilState)

        this.renderNode.renderDeferred(RenderContextBase(this.camera))

        val context = RenderPassContext(this.camera)

        this.pipeline.prepare(context, this.prototype.asBuffers())
        this.screen.setAsDraw()
        this.pipeline.process(context, this.prototype.asBuffers())

        return RenderingOutput(this.screen, this.prototype.asBuffers())
    }

    override fun delete() {
        this.screen.delete()
        this.renderNode.delete()
        this.pipeline.delete()
    }
}