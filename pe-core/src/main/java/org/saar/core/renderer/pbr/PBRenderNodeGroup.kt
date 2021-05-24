package org.saar.core.renderer.pbr

class PBRenderNodeGroup(vararg children: PBRenderNode) : PBRenderParentNode {

    override val children: List<PBRenderNode> = children.toMutableList()

}