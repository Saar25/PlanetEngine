package org.saar.gui.style.fontcolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

fun interface FontColourValue {

    fun compute(container: UIChildNode): Colour

}