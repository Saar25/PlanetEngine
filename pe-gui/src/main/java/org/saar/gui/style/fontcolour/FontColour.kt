package org.saar.gui.style.fontcolour

import org.saar.gui.UIChildElement
import org.saar.gui.style.Colour
import org.saar.gui.style.value.StyleColourValue
import org.saar.gui.style.value.StyleColourValues

class FontColour(private val container: UIChildElement) : ReadonlyFontColour {

    private var colourValue: StyleColourValue = StyleColourValues.inherit

    override var colour: Colour
        get() = this.colourValue.compute(this.container.parent.style.fontColour.colour)
        set(value) {
            this.colourValue = StyleColourValues.of(value)
        }

    fun set(colour: Colour) {
        this.colour = colour
    }

    fun set(colourValue: StyleColourValue) {
        this.colourValue = colourValue
    }
}