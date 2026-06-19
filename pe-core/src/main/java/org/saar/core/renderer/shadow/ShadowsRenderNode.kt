package org.saar.core.renderer.shadow

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass

interface ShadowsRenderNode : Node {

    fun renderShadows(context: RenderContext)

}
