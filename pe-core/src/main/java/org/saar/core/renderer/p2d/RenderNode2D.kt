package org.saar.core.renderer.p2d

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderNode

interface RenderNode2D : Node {
    fun render2D(context: RenderContext)
}

fun RenderNode2D.asRenderNode2D() = object : RenderNode {
    override fun render(context: RenderContext) = this@asRenderNode2D.render2D(context)

    override fun delete() = this@asRenderNode2D.delete()
}