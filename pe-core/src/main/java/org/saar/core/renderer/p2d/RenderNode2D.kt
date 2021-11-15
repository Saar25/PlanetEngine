package org.saar.core.renderer.p2d

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext

interface RenderNode2D : Node {
    fun render2D(context: RenderContext)
}