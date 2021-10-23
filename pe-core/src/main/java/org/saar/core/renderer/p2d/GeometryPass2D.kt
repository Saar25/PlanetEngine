package org.saar.core.renderer.p2d

import org.saar.core.renderer.RenderContextBase
import org.saar.core.renderer.renderpass.RenderPassContext

class GeometryPass2D(private vararg val children: RenderNode2D) : RenderPass2D {

    override fun render(context: RenderPassContext, buffers: RenderingBuffers2D) {
        this.children.forEach { it.render2D(RenderContextBase(context.camera)) }
    }

    override fun delete() = this.children.forEach { it.delete() }
}