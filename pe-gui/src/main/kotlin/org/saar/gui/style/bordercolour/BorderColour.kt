package org.saar.gui.style.bordercolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

class BorderColour(private val container: UIChildNode) : ReadonlyBorderColour {

    private var colourValue: BorderColourValue = BorderColourValues.inherit

    override var colour: Colour
        get() = this.colourValue.compute(this.container)
        set(value) {
            this.colourValue = BorderColourValues.of(value)
        }

    fun set(colour: Colour) {
        this.colour = colour
    }

    fun set(colourValue: BorderColourValue) {
        this.colourValue = colourValue
    }
}