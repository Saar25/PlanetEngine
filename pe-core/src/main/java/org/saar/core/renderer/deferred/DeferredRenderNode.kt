package org.saar.core.renderer.deferred

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext

interface DeferredRenderNode : Node {

    fun renderDeferred(context: RenderContext)

}