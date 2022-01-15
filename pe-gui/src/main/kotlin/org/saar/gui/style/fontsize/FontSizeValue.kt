package org.saar.gui.style.fontsize

import org.saar.gui.UIChildNode

fun interface FontSizeValue {

    fun compute(container: UIChildNode): Int
}