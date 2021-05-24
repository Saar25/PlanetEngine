package org.saar.core.renderer.forward

class ForwardRenderNodeGroup(vararg children: ForwardRenderNode) : ForwardRenderParentNode {

    override val children: List<ForwardRenderNode> = children.toMutableList()

}