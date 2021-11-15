package org.saar.gui.style.value

import org.saar.gui.font.Font
import org.saar.gui.style.IStyle

fun interface StyleFontValue {

    fun compute(parent: IStyle, style: IStyle): Font
}