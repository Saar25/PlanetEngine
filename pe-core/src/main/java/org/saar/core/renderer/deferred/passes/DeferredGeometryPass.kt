package org.saar.core.renderer.deferred.passes

import org.saar.core.renderer.RenderContextBase
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.stencil.*

class DeferredGeometryPass(private vararg val children: DeferredRenderNode) : DeferredRenderPass {

    private val stencilState = StencilState(
        StencilOperation.REPLACE_ON_PASS,
        StencilFunction(Comparator.ALWAYS, 1, 0xFF),
        StencilMask.UNCHANGED
    )

    override fun prepare(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        StencilTest.apply(this.stencilState)
    }

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        this.children.forEach { it.renderDeferred(RenderContextBase(context.camera)) }
    }

    override fun delete() = this.children.forEach { it.delete() }
}