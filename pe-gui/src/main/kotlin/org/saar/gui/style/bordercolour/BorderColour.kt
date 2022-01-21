package org.saar.gui.style.bordercolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

class BorderColour(private val container: UIChildNode) : ReadonlyBorderColour {

    var value: BorderColourValue = BorderColourValues.inherit

    override val colour get() = this.value.compute(this.container)

    fun set(colour: Colour) {
        this.value = BorderColourValues.of(colour)
    }

    fun set(value: BorderColourValue) {
        this.value = value
    }
}