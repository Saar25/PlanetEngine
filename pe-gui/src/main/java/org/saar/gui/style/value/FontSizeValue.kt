package org.saar.gui.style.value

import org.saar.gui.style.IStyle

fun interface FontSizeValue {

    fun compute(parent: IStyle, style: IStyle): Int
}