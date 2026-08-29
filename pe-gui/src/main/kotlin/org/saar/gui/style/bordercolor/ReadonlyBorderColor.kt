package org.saar.gui.style.bordercolor

import org.saar.gui.style.Color
import org.saar.gui.style.StyleProperty

interface ReadonlyBorderColor : StyleProperty {

    val color: Color

    fun asInt() = this.color.asInt()
}