package org.saar.core.renderer.forward

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext

interface ForwardRenderNode : Node {

    fun renderForward(context: RenderContext)

}