package org.saar.gui

import org.jproperty.property.SimpleObjectProperty
import org.saar.gui.style.ElementStyle

class UIElement : UIChildNode, UIParentNode {

    override val parentProperty = SimpleObjectProperty<UIParentNode>(UINullNode)

    override val style = ElementStyle(this)

    private val _children = mutableListOf<UINode>()
    override val children: List<UINode> = this._children

    fun add(uiNode: UIChildNode) {
        this._children.add(uiNode)
        uiNode.parent = this
    }
}