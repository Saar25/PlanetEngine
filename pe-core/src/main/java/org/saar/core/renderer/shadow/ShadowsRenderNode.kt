package org.saar.core.renderer.shadow

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext

interface ShadowsRenderNode : Node {

    fun renderShadows(context: RenderContext)

}