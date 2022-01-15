package org.saar.gui.style.fontcolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

class FontColour(private val container: UIChildNode) : ReadonlyFontColour {

    private var colourValue: FontColourValue = FontColourValues.inherit

    override var colour: Colour
        get() = this.colourValue.compute(this.container)
        set(value) {
            this.colourValue = FontColourValues.of(value)
        }

    fun set(colour: Colour) {
        this.colour = colour
    }

    fun set(colourValue: FontColourValue) {
        this.colourValue = colourValue
    }
}