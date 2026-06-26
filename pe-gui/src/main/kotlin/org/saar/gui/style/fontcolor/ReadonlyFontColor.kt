package org.saar.gui.style.fontcolor

import org.saar.gui.style.Color
import org.saar.gui.style.StyleProperty

interface ReadonlyFontColor : StyleProperty {

    val color: Color

    fun asInt() = this.color.asInt()

}