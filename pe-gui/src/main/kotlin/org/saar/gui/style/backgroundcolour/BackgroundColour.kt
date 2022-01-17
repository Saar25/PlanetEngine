package org.saar.gui.style.backgroundcolour

import org.jproperty.ObservableValue
import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

class BackgroundColour(private val container: UIChildNode) : ReadonlyBackgroundColour {

    var topRightValue: BackgroundColourValue = BackgroundColourValues.inherit
        set(value) {
            this.topRight = value.buildTopRight(this.container)
            field = value
        }

    var topLeftValue: BackgroundColourValue = BackgroundColourValues.inherit
        set(value) {
            this.topLeft = value.buildTopLeft(this.container)
            field = value
        }

    var bottomRightValue: BackgroundColourValue = BackgroundColourValues.inherit
        set(value) {
            this.bottomRight = value.buildBottomRight(this.container)
            field = value
        }

    var bottomLeftValue: BackgroundColourValue = BackgroundColourValues.inherit
        set(value) {
            this.bottomLeft = value.buildBottomLeft(this.container)
            field = value
        }

    override var topRight: ObservableValue<Colour> = this.topRightValue.buildTopRight(this.container)

    override var topLeft: ObservableValue<Colour> = this.topLeftValue.buildTopLeft(this.container)

    override var bottomRight: ObservableValue<Colour> = this.bottomRightValue.buildBottomRight(this.container)

    override var bottomLeft: ObservableValue<Colour> = this.bottomLeftValue.buildBottomLeft(this.container)

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