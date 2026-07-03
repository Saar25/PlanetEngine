package org.saar.core.renderer.forward

import org.saar.core.node.Node

interface ForwardRenderNode : Node {

    fun renderForward(context: ForwardRenderContext)

}
