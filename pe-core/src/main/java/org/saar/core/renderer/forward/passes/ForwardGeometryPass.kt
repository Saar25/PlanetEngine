package org.saar.core.renderer.forward.passes

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.forward.ForwardRenderPass
import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.stencil.*
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils

class ForwardGeometryPass(private vararg val children: ForwardRenderNode) : ForwardRenderPass {

    private val stencilState = StencilState(
        StencilOperation.REPLACE_ON_PASS,
        StencilFunction(Comparator.ALWAYS, 1, 0xFF),
        StencilMask.UNCHANGED
    )

    override fun prepare(context: RenderContext, buffers: ForwardRenderingBuffers) {
        StencilTest.apply(this.stencilState)
        GlUtils.setCullFace(GlCullFace.BACK)
    }

    override fun render(context: RenderContext, buffers: ForwardRenderingBuffers) {
        this.children.forEach { it.renderForward(RenderContext(context.camera)) }
    }

    override fun delete() = this.children.forEach { it.delete() }
}