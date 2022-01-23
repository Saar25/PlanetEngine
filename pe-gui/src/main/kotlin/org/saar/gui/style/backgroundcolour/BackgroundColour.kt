package org.saar.gui.style.backgroundcolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

class BackgroundColour(private val container: UIChildNode) : ReadonlyBackgroundColour {

    var topRightValue: BackgroundColourValue = BackgroundColourValues.inherit

    var topLeftValue: BackgroundColourValue = BackgroundColourValues.inherit

    var bottomRightValue: BackgroundColourValue = BackgroundColourValues.inherit

    var bottomLeftValue: BackgroundColourValue = BackgroundColourValues.inherit

    override val topRight get() = this.topRightValue.computeTopRight(this.container)

    override val topLeft get() = this.topLeftValue.computeTopLeft(this.container)

    override val bottomRight get() = this.bottomRightValue.computeBottomRight(this.container)

    override val bottomLeft get() = this.bottomLeftValue.computeBottomLeft(this.container)

    fun set(colour: Colour) {
        this.topRightValue = BackgroundColourValues.of(colour)
        this.topLeftValue = BackgroundColourValues.of(colour)
        this.bottomRightValue = BackgroundColourValues.of(colour)
        this.bottomLeftValue = BackgroundColourValues.of(colour)
    }

    fun set(colourValue: BackgroundColourValue) {
        this.topRightValue = colourValue
        this.topLeftValue = colourValue
        this.bottomRightValue = colourValue
        this.bottomLeftValue = colourValue
    }
}