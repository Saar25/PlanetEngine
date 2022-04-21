package org.saar.gui

import org.saar.gui.style.ElementStyle

open class UIElement : UIChildNode, UIMutableParent {

    override var activeElement: UINode
        get() = super.activeElement
        set(value) {
            super.activeElement = value
        }

    override var parent: UIParentNode = UINullNode

    override val style = ElementStyle(this)

    private val _children = mutableListOf<UINode>()
    override val children: List<UINode> = this._children

    override fun add(uiNode: UIChildNode) {
        if (this._children.add(uiNode)) {
            uiNode.parent = this
        }
    }

    override fun remove(uiNode: UIChildNode) {
        if (this._children.remove(uiNode)) {
            uiNode.parent = UINullNode
        }
    }
}