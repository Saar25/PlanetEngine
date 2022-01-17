package org.saar.gui.style.font

import org.saar.gui.font.Font

fun interface ReadonlyFontFamily {

    fun get(): Font
}