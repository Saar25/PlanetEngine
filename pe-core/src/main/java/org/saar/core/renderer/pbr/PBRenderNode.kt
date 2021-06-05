package org.saar.core.renderer.pbr

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext

interface PBRenderNode : Node {

    fun renderPBR(context: RenderContext)

}