package org.saar.core.renderer.pbr

class PBRRenderNodeGroup(vararg children: PBRRenderNode) : PBRRenderParentNode {

    private val childrenList: MutableList<PBRRenderNode> = mutableListOf()

    override val children: List<PBRRenderNode> get() = this.childrenList

    init {
        this.childrenList.addAll(children)
    }

    fun add(child: PBRRenderNode) {
        this.childrenList.add(child)
    }

}