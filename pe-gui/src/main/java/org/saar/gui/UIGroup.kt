package org.saar.gui

import org.saar.gui.style.Style

class UIGroup : UIContainer, UIChildNode {

    override val children = mutableListOf<UINode>()

    override val style = Style(this)

    override var parent: UIElement = UINullElement

    fun add(uiNode: UIChildNode) {
        this.children.add(uiNode)
        uiNode.parent = this
    }
}