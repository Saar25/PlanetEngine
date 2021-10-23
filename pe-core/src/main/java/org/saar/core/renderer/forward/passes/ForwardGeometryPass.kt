package org.saar.core.renderer.forward.passes

import org.saar.core.renderer.RenderContextBase
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.forward.ForwardRenderPass
import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.stencil.*

class ForwardGeometryPass(private vararg val children: ForwardRenderNode) : ForwardRenderPass {

    private val stencilState = StencilState(
        StencilOperation.REPLACE_ON_PASS,
        StencilFunction(Comparator.ALWAYS, 1, 0xFF),
        StencilMask.UNCHANGED
    )

    override fun prepare(context: RenderPassContext, buffers: ForwardRenderingBuffers) {
        StencilTest.apply(this.stencilState)
    }

    override fun render(context: RenderPassContext, buffers: ForwardRenderingBuffers) {
        this.children.forEach { it.renderForward(RenderContextBase(context.camera)) }
    }

    override fun delete() = this.children.forEach { it.delete() }
}