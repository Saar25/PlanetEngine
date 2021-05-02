package org.saar.core.renderer.forward

class ForwardRenderNodeGroup : ForwardRenderParentNode {

    private val childrenList: MutableList<ForwardRenderNode> = mutableListOf()

    override val children: List<ForwardRenderNode> get() = this.childrenList

    fun add(child: ForwardRenderNode) {
        this.childrenList.add(child)
    }

}