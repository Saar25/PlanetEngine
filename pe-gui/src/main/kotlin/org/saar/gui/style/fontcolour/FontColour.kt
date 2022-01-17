package org.saar.gui.style.fontcolour

import org.jproperty.ObservableValue
import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour
import org.saar.gui.style.fontcolour.FontColourValues.of

class FontColour(private val container: UIChildNode) : ReadonlyFontColour {

    var value: FontColourValue = FontColourValues.inherit
        set(value) {
            this.colour = value.build(this.container)
            field = value
        }

    override var colour: ObservableValue<Colour> = this.value.build(this.container)

    fun set(colour: Colour) {
        this.value = of(colour)
    }

    fun set(value: FontColourValue) {
        this.value = value
    }
}