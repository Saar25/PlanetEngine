package org.saar.gui.style.redius

import org.jproperty.binding.IntegerWrapper
import org.saar.gui.UIChildNode

class Radius(private val container: UIChildNode) : ReadonlyRadius {

    var topRightValue: RadiusValue = RadiusValues.none
        set(value) {
            this.topRight.set(value.buildTopRight(this.container))
            field = value
        }

    var topLeftValue: RadiusValue = RadiusValues.none
        set(value) {
            this.topLeft.set(value.buildTopLeft(this.container))
            field = value
        }

    var bottomRightValue: RadiusValue = RadiusValues.none
        set(value) {
            this.bottomRight.set(value.buildBottomRight(this.container))
            field = value
        }

    var bottomLeftValue: RadiusValue = RadiusValues.none
        set(value) {
            this.bottomLeft.set(value.buildBottomLeft(this.container))
            field = value
        }

    override val topRight = IntegerWrapper(this.topRightValue.buildTopRight(this.container))

    override val topLeft = IntegerWrapper(this.topLeftValue.buildTopLeft(this.container))

    override val bottomRight = IntegerWrapper(this.bottomRightValue.buildBottomRight(this.container))

    override val bottomLeft = IntegerWrapper(this.bottomLeftValue.buildBottomLeft(this.container))

    fun set(topRight: Int, topLeft: Int, bottomRight: Int, bottomLeft: Int) {
        this.topRightValue = RadiusValues.pixels(topRight)
        this.topLeftValue = RadiusValues.pixels(topLeft)
        this.bottomRightValue = RadiusValues.pixels(bottomRight)
        this.bottomLeftValue = RadiusValues.pixels(bottomLeft)
    }

    fun set(all: Int) {
        set(all, all, all, all)
    }

    fun set(borders: Radius) {
        this.topRightValue = borders.topRightValue
        this.topLeftValue = borders.topLeftValue
        this.bottomRightValue = borders.bottomRightValue
        this.bottomLeftValue = borders.bottomLeftValue
    }

    fun set(radius: RadiusValue) {
        this.topRightValue = radius
        this.topLeftValue = radius
        this.bottomRightValue = radius
        this.bottomLeftValue = radius
    }
}