package org.saar.core.renderer.deferred

class DeferredRenderNodeGroup(vararg children: DeferredRenderNode) : DeferredRenderParentNode {

    override val children: MutableList<DeferredRenderNode> = children.toMutableList()

}