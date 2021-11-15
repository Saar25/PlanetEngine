package org.saar.core.renderer.p2d

import org.saar.core.renderer.RenderContext

class GeometryPass2D(private vararg val children: RenderNode2D) : RenderPass2D {

    override fun render(context: RenderContext, buffers: RenderingBuffers2D) {
        this.children.forEach { it.render2D(RenderContext(context.camera)) }
    }

    override fun delete() = this.children.forEach { it.delete() }
}