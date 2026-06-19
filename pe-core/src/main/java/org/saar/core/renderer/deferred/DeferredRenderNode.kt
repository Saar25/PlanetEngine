package org.saar.core.renderer.deferred

import org.saar.core.node.Node

interface DeferredRenderNode : Node {

    fun renderDeferred(context: DeferredRenderContext)

}
