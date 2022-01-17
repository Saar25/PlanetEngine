package org.saar.gui.style.fontcolour

import org.jproperty.ObservableValue
import org.saar.gui.style.Colour
import org.saar.gui.style.StyleProperty

interface ReadonlyFontColour : StyleProperty {

    val colour: ObservableValue<Colour>

    fun asInt() = this.colour.value.asInt()

}