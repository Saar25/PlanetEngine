package org.saar.core.renderer.deferred.passes

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.stencil.*
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils

class DeferredGeometryPass(private vararg val children: DeferredRenderNode) : DeferredRenderPass {

    private val stencilState = StencilState(
        StencilOperation.REPLACE_ON_PASS,
        StencilFunction(Comparator.ALWAYS, 1, 0xFF),
        StencilMask.UNCHANGED
    )

    override fun prepare(context: RenderContext, buffers: DeferredRenderingBuffers) {
        StencilTest.apply(this.stencilState)
        GlUtils.setCullFace(GlCullFace.BACK)
    }

    override fun render(context: RenderContext, buffers: DeferredRenderingBuffers) {
        this.children.forEach { it.renderDeferred(RenderContext(context.camera)) }
    }

    override fun delete() = this.children.forEach { it.delete() }
}