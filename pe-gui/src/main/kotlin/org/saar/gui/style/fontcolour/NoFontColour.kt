package org.saar.gui.style.fontcolour

import org.jproperty.constant.ConstantObjectProperty
import org.jproperty.value.ObservableObjectValue
import org.saar.gui.style.Colour
import org.saar.gui.style.Colours

object NoFontColour : ReadonlyFontColour {

    override val colour: ObservableObjectValue<Colour> = ConstantObjectProperty(Colours.BLACK)

    override fun toString() = "[FontColour: ${this.colour.get()}]"
}