package org.saar.gui

interface UIChildNode : UINode {

    var parent: UIParentNode

    override var activeElement: UINode
        get() = this.parent.activeElement
        set(value) {
            this.parent.activeElement = value
        }

}