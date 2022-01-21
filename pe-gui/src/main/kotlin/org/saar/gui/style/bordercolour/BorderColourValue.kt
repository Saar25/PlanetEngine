package org.saar.gui.style.bordercolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

fun interface BorderColourValue {

    fun compute(container: UIChildNode): Colour

}