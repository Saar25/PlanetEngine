package org.saar.gui.style.value

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

fun interface FontSizeValue {

    fun compute(parent: UIParentNode, container: UIChildNode): Int
}