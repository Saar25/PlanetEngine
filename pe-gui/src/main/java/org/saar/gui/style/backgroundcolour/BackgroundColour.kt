package org.saar.gui.style.backgroundcolour

import org.saar.gui.UIChildElement
import org.saar.gui.style.Colour
import org.saar.gui.style.value.StyleColourValue
import org.saar.gui.style.value.StyleColourValues

class BackgroundColour(private val container: UIChildElement) : ReadonlyBackgroundColour {

    var topRightValue: StyleColourValue = StyleColourValues.inherit
    var topLeftValue: StyleColourValue = StyleColourValues.inherit
    var bottomRightValue: StyleColourValue = StyleColourValues.inherit
    var bottomLeftValue: StyleColourValue = StyleColourValues.inherit

    override var topRight: Colour
        get() = this.topRightValue.compute(this.container.parent.style.backgroundColour.topRight)
        set(value) {
            this.topRightValue = StyleColourValues.of(value)
        }

    override var topLeft: Colour
        get() = this.topLeftValue.compute(this.container.parent.style.backgroundColour.topLeft)
        set(value) {
            this.topLeftValue = StyleColourValues.of(value)
        }

    override var bottomRight: Colour
        get() = this.bottomRightValue.compute(this.container.parent.style.backgroundColour.bottomRight)
        set(value) {
            this.bottomRightValue = StyleColourValues.of(value)
        }

    override var bottomLeft: Colour
        get() = this.bottomLeftValue.compute(this.container.parent.style.backgroundColour.bottomLeft)
        set(value) {
            this.bottomLeftValue = StyleColourValues.of(value)
        }

    fun set(colour: Colour) {
        this.topRight = colour
        this.topLeft = colour
        this.bottomRight = colour
        this.bottomLeft = colour
    }
}