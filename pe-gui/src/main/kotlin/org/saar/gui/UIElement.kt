package org.saar.gui

import org.saar.gui.style.ElementStyle

class UIElement : UIChildNode, UIParentNode {

    override val style = ElementStyle(this)

    override var parent: UIParentNode = UINullNode

    private val _children = mutableListOf<UINode>()
    override val children: List<UINode> = this._children

    fun add(uiNode: UIChildNode) {
        this._children.add(uiNode)
        uiNode.parent = this
    }
}