package org.saar.gui.style.bordercolour

import org.jproperty.ObservableValue
import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

class BorderColour(private val container: UIChildNode) : ReadonlyBorderColour {

    private var value: BorderColourValue = BorderColourValues.inherit
        set(value) {
            this.colour = value.build(this.container)
            field = value
        }

    override var colour: ObservableValue<Colour> = this.value.build(this.container)

    fun set(colour: Colour) {
        this.value = BorderColourValues.of(colour)
    }

    fun set(value: BorderColourValue) {
        this.value = value
    }
}