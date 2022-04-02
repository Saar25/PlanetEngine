package org.saar.gui

import org.saar.gui.style.ElementStyle

class UIElement : UIChildNode, UIMutableParent() {

    override var parent: UIParentNode = UINullNode

    override val style = ElementStyle(this)

    override var activeElement: UINode
        get() = super.activeElement
        set(value) {
            super.activeElement = value
        }

}