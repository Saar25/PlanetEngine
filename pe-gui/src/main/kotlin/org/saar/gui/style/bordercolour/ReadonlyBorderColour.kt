package org.saar.gui.style.bordercolour

import org.jproperty.ObservableValue
import org.saar.gui.style.Colour
import org.saar.gui.style.StyleProperty

interface ReadonlyBorderColour : StyleProperty {

    val colour: ObservableValue<Colour>

    fun asInt() = this.colour.value.asInt()
}