package org.saar.core.renderer.deferred

class DeferredRenderNodeGroup(vararg children: DeferredRenderNode) : DeferredRenderParentNode {

    private val childrenList: MutableList<DeferredRenderNode> = mutableListOf()

    override val children: List<DeferredRenderNode> get() = this.childrenList

    init {
        this.childrenList.addAll(children)
    }

    fun add(child: DeferredRenderNode) {
        this.childrenList.add(child)
    }

}