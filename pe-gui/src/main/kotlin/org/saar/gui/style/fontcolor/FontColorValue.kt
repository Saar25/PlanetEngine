package org.saar.gui.style.fontcolor

import org.saar.gui.UIChildNode
import org.saar.gui.style.Color

fun interface FontColorValue {

    fun compute(container: UIChildNode): Color

}