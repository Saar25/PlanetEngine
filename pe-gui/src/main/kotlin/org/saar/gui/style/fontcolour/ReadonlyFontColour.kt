package org.saar.gui.style.fontcolour

import org.saar.gui.style.Colour
import org.saar.gui.style.StyleProperty

interface ReadonlyFontColour : StyleProperty {

    val colour: Colour

    fun asInt() = this.colour.asInt()

}