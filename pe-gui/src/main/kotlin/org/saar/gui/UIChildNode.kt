package org.saar.gui

import org.jproperty.type.ObjectProperty

interface UIChildNode : UINode {

    val parentProperty: ObjectProperty<UIParentNode>

    var parent: UIParentNode
        get() = this.parentProperty.value
        set(value) {
            this.parentProperty.value = value
        }

}