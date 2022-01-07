package org.saar.gui

import org.saar.gui.block.UIBlock
import org.saar.gui.style.ContainerStyle

open class UIContainer : UIParentElement, UIChildElement {

    final override var parent: UIParentElement = UINullElement

    final override val children = mutableListOf<UIElement>()

    final override val style = ContainerStyle(this)

    final override val uiBlock = UIBlock(this.style)

    fun add(uiNode: UIChildElement) {
        this.children.add(uiNode)
        uiNode.parent = this
    }
}