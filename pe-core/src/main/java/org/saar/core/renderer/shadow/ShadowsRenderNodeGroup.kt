package org.saar.core.renderer.shadow

class ShadowsRenderNodeGroup(vararg children: ShadowsRenderNode) : ShadowsRenderParentNode {

    private val childrenList: MutableList<ShadowsRenderNode> = mutableListOf()

    override val children: List<ShadowsRenderNode> get() = this.childrenList

    init {
        this.childrenList.addAll(children)
    }

    fun add(child: ShadowsRenderNode) {
        this.childrenList.add(child)
    }

}