package org.saar.core.renderer.deferred

class DeferredRenderNodeGroup : DeferredRenderParentNode {

    private val childrenList: MutableList<DeferredRenderNode> = mutableListOf()

    override val children: List<DeferredRenderNode> get() = this.childrenList

    fun add(child: DeferredRenderNode) {
        this.childrenList.add(child)
    }

}