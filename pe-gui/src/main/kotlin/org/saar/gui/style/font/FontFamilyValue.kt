package org.saar.gui.style.font

import org.saar.gui.UIChildNode
import org.saar.gui.font.Font

fun interface FontFamilyValue {

    fun compute(container: UIChildNode): Font

}