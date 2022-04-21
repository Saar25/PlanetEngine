package org.saar.gui.style.fontcolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour
import org.saar.gui.style.fontcolour.FontColourValues.of

class FontColour(private val container: UIChildNode, default: FontColourValue = FontColourValues.inherit) : ReadonlyFontColour {

    var value: FontColourValue = default

    override val colour get() = this.value.compute(this.container)

    fun set(colour: Colour) {
        this.value = of(colour)
    }

    fun set(value: FontColourValue) {
        this.value = value
    }
}