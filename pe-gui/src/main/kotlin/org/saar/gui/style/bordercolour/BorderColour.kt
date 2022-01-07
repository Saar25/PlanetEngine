package org.saar.gui.style.bordercolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour
import org.saar.gui.style.value.StyleColourValue
import org.saar.gui.style.value.StyleColourValues

class BorderColour(private val container: UIChildNode) : ReadonlyBorderColour {

    private var colourValue: StyleColourValue = StyleColourValues.inherit

    override var colour: Colour
        get() = this.colourValue.compute(this.container.parent.style.borderColour.colour)
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