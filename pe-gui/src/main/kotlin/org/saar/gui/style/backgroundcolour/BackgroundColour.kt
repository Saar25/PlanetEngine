package org.saar.gui.style.backgroundcolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

class BackgroundColour(private val container: UIChildNode) : ReadonlyBackgroundColour {

    var topRightValue: BackgroundColourValue = BackgroundColourValues.inherit
    var topLeftValue: BackgroundColourValue = BackgroundColourValues.inherit
    var bottomRightValue: BackgroundColourValue = BackgroundColourValues.inherit
    var bottomLeftValue: BackgroundColourValue = BackgroundColourValues.inherit

    override var topRight: Colour
        get() = this.topRightValue.computeTopRight(this.container)
        set(value) {
            this.topRightValue = BackgroundColourValues.of(value)
        }

    override var topLeft: Colour
        get() = this.topLeftValue.computeTopLeft(this.container)
        set(value) {
            this.topLeftValue = BackgroundColourValues.of(value)
        }

    override var bottomRight: Colour
        get() = this.bottomRightValue.computeBottomRight(this.container)
        set(value) {
            this.bottomRightValue = BackgroundColourValues.of(value)
        }

    override var bottomLeft: Colour
        get() = this.bottomLeftValue.computeBottomLeft(this.container)
        set(value) {
            this.bottomLeftValue = BackgroundColourValues.of(value)
        }

    fun set(colour: Colour) {
        this.topRight = colour
        this.topLeft = colour
        this.bottomRight = colour
        this.bottomLeft = colour
    }

    fun set(colourValue: BackgroundColourValue) {
        this.topRightValue = colourValue
        this.topLeftValue = colourValue
        this.bottomRightValue = colourValue
        this.bottomLeftValue = colourValue
    }
}