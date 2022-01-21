package org.saar.gui.style.bordercolour

import org.saar.gui.style.Colour
import org.saar.gui.style.StyleProperty

interface ReadonlyBorderColour : StyleProperty {

    val colour: Colour

    fun asInt() = this.colour.asInt()
}