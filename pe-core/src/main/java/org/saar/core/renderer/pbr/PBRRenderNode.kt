package org.saar.core.renderer.pbr

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext

interface PBRRenderNode : Node {

    fun renderPBR(context: RenderContext)

}