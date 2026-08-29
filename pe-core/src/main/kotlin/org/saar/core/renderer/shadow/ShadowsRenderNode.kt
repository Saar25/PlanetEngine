package org.saar.core.renderer.shadow

import org.saar.core.node.Node

interface ShadowsRenderNode : Node {

    fun renderShadows(context: ShadowsRenderContext)

}
