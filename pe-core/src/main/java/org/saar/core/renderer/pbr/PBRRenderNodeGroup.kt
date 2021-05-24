package org.saar.core.renderer.pbr

class PBRRenderNodeGroup(vararg children: PBRRenderNode) : PBRRenderParentNode {

    override val children: List<PBRRenderNode> = children.toMutableList()

}