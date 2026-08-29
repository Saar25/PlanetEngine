package org.saar.gui.style.bordercolor

import org.saar.gui.UIChildNode
import org.saar.gui.style.Color

fun interface BorderColorValue {

    fun compute(container: UIChildNode): Color

}