package org.saar.gui

abstract class UIMutableParent : UIParentNode {

    private val _children = mutableListOf<UINode>()
    override val children: List<UINode> = this._children

    fun add(uiNode: UIChildNode) {
        if (this._children.add(uiNode)) {
            uiNode.parent = this
        }
    }

    fun remove(uiNode: UIChildNode) {
        if (this._children.remove(uiNode)) {
            uiNode.parent = UINullNode
        }
    }

    operator fun UIChildNode.unaryPlus() = this@UIMutableParent.add(this@unaryPlus)
}