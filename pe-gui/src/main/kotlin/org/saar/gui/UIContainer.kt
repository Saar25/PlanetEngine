package org.saar.gui

import org.saar.gui.block.UIBlock
import org.saar.gui.style.Style

open class UIContainer : UIParentElement, UIChildElement {

    final override var parent: UIElement = UINullElement

    final override val children = mutableListOf<UIElement>()

    final override val style = Style(this)

    final override val uiBlock = UIBlock(this.style)

    fun add(uiNode: UIChildElement) {
        this.children.add(uiNode)
        uiNode.parent = this
    }
}