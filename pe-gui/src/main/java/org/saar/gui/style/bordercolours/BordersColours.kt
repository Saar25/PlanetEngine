package org.saar.gui.style.bordercolours

import org.saar.gui.UIChildElement
import org.saar.gui.style.Colour
import org.saar.gui.style.property.Orientation
import org.saar.gui.style.value.StyleColourValue
import org.saar.gui.style.value.StyleColourValues

class BordersColours(private val container: UIChildElement) : ReadonlyBordersColours {

    var topLeftValue: StyleColourValue = StyleColourValues.inherit
    var topRightValue: StyleColourValue = StyleColourValues.inherit
    var bottomRightValue: StyleColourValue = StyleColourValues.inherit
    var bottomLeftValue: StyleColourValue = StyleColourValues.inherit

    override var topLeft: Colour
        get() = this.topLeftValue.compute(this.container.parent.style.borderColour.topLeft)
        set(value) {
            this.topLeftValue = StyleColourValues.of(value)
        }

    override var topRight: Colour
        get() = this.topRightValue.compute(this.container.parent.style.borderColour.topRight)
        set(value) {
            this.topRightValue = StyleColourValues.of(value)
        }

    override var bottomRight: Colour
        get() = this.bottomRightValue.compute(this.container.parent.style.borderColour.bottomRight)
        set(value) {
            this.bottomRightValue = StyleColourValues.of(value)
        }

    override var bottomLeft: Colour
        get() = this.bottomLeftValue.compute(this.container.parent.style.borderColour.bottomLeft)
        set(value) {
            this.bottomLeftValue = StyleColourValues.of(value)
        }


    operator fun set(topLeft: Colour, topRight: Colour,
                     bottomRight: Colour, bottomLeft: Colour) {
        this.topLeft = topLeft
        this.topRight = topRight
        this.bottomRight = bottomRight
        this.bottomLeft = bottomLeft
    }

    operator fun set(topLeft: Colour, sides: Colour, bottomRight: Colour) {
        set(topLeft, sides, bottomRight, sides)
    }

    operator fun set(topLeftBottomRight: Colour, topRightBottomLeft: Colour) {
        set(topLeftBottomRight, topRightBottomLeft, topLeftBottomRight, topRightBottomLeft)
    }

    operator fun set(a: Colour, b: Colour, orientation: Orientation) {
        when (orientation) {
            Orientation.VERTICAL -> set(a, a, b, b)
            Orientation.HORIZONTAL -> set(a, b, b, a)
        }
    }

    fun set(all: Colour) {
        set(all, all, all, all)
    }

    fun set(corners: BordersColours) {
        set(corners.topLeft, corners.topRight,
            corners.bottomRight, corners.bottomLeft)
    }

    fun get(): Colour {
        return topRight
    }

    @JvmOverloads
    fun set(r: Int, g: Int, b: Int, a: Int = 255) {
        this.topLeft.set(r, g, b, a)
        this.topRight.set(r, g, b, a)
        this.bottomRight.set(r, g, b, a)
        this.bottomLeft.set(r, g, b, a)
    }

    fun setNormalized(r: Float, g: Float, b: Float) {
        setNormalized(r, g, b, 1f)
    }

    fun setNormalized(r: Float, g: Float, b: Float, a: Float) {
        topLeft.setNormalized(r, g, b, a)
        topRight.setNormalized(r, g, b, a)
        bottomRight.setNormalized(r, g, b, a)
        bottomLeft.setNormalized(r, g, b, a)
    }

    override fun toString(): String {
        return String.format("[Corners: topLeft=%s, topRight=%s, bottomRight=%s, bottomLeft=%s]",
            topLeft, topRight, bottomRight, bottomLeft)
    }
}