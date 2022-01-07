package org.saar.gui

import org.saar.gui.block.UIBlock
import org.saar.gui.style.ElementStyle

open class UIContainer : UIElement {

    final override var parent: UIParentNode = UINullNode

    final override val children = mutableListOf<UINode>()

    final override val style = ElementStyle(this)

    final override val uiBlock = UIBlock(this.style)

    fun add(uiNode: UIChildNode) {
        this.children.add(uiNode)
        uiNode.parent = this
    }
}