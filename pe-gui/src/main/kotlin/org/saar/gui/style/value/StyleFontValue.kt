package org.saar.gui.style.value

import org.saar.gui.font.Font
import org.saar.gui.style.Style

fun interface StyleFontValue {

    fun compute(parent: Style, style: Style): Font
}