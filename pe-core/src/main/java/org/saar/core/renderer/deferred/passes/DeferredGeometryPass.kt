package org.saar.core.renderer.deferred.passes

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.cullface.CullFaceValue
import org.saar.lwjgl.opengl.depth.DepthFunction
import org.saar.lwjgl.opengl.depth.DepthMask
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.stencil.*

class DeferredGeometryPass(private vararg val children: DeferredRenderNode) : DeferredRenderPass {

    private val stencilState = StencilState(
        StencilOperation.REPLACE_ON_PASS,
        StencilFunction(Comparator.ALWAYS, 1, 0xFF),
        StencilMask.UNCHANGED
    )

    private val depthState = DepthState(DepthFunction(Comparator.LESS), DepthMask.WRITE)

    override fun prepare(context: RenderContext, buffers: DeferredRenderingBuffers) {
        CullFace.set(true, CullFaceValue.BACK)
        StencilTest.apply(this.stencilState)
        DepthTest.apply(this.depthState)
    }

    override fun render(context: RenderContext, buffers: DeferredRenderingBuffers) {
        this.children.forEach { it.renderDeferred(RenderContext(context.camera)) }
    }

    override fun delete() = this.children.forEach { it.delete() }
}