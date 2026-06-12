package org.saar.core.renderer.forward.passes

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.forward.ForwardRenderPass
import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.lwjgl.opengl.constants.Face
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.stencil.StencilTest

class ForwardGeometryPass(private vararg val children: ForwardRenderNode) : ForwardRenderPass {

    private val stencilState = StencilState.ALWAYS_WRITE

    override fun prepare(context: RenderContext, buffers: ForwardRenderingBuffers) {
        CullFace.set(true, Face.BACK)
        StencilTest.apply(this.stencilState)
    }

    override fun render(context: RenderContext, buffers: ForwardRenderingBuffers) {
        this.children.forEach { it.renderForward(RenderContext(context.camera)) }
    }

    override fun delete() = this.children.forEach { it.delete() }
}