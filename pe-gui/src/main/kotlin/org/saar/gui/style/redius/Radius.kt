package org.saar.gui.style.redius

import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.UIChildNode

class Radius(private val container: UIChildNode) : ReadonlyRadius {

    var topRightValue: RadiusValue = RadiusValues.none
        set(value) {
            this.topRight = value.buildTopRight(this.container)
            field = value
        }

    var topLeftValue: RadiusValue = RadiusValues.none
        set(value) {
            this.topLeft = value.buildTopLeft(this.container)
            field = value
        }

    var bottomRightValue: RadiusValue = RadiusValues.none
        set(value) {
            this.bottomRight = value.buildBottomRight(this.container)
            field = value
        }

    var bottomLeftValue: RadiusValue = RadiusValues.none
        set(value) {
            this.bottomLeft = value.buildBottomLeft(this.container)
            field = value
        }

    override var topRight: ObservableIntegerValue = this.topRightValue.buildTopRight(this.container)

    override var topLeft: ObservableIntegerValue = this.topLeftValue.buildTopLeft(this.container)

    override var bottomRight: ObservableIntegerValue = this.bottomRightValue.buildBottomRight(this.container)

    override var bottomLeft: ObservableIntegerValue = this.bottomLeftValue.buildBottomLeft(this.container)

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