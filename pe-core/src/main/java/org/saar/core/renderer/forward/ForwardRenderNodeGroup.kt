package org.saar.core.renderer.forward

class ForwardRenderNodeGroup(vararg nodes: ForwardRenderNode) : ForwardRenderParentNode {

    private val childrenList: MutableList<ForwardRenderNode> = nodes.toMutableList()

    override val children: List<ForwardRenderNode> get() = this.childrenList

    fun add(child: ForwardRenderNode) {
        this.childrenList.add(child)
    }

}