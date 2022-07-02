package org.saar.gui.style.redius

import org.saar.gui.UIChildNode

class Radius(private val container: UIChildNode, default: RadiusValue = RadiusValues.none) : ReadonlyRadius {

    var topRightValue: RadiusValue = default

    var topLeftValue: RadiusValue = default

    var bottomRightValue: RadiusValue = default

    var bottomLeftValue: RadiusValue = default

    override val topRight get() = this.topRightValue.computeTopRight(this.container)

    override val topLeft get() = this.topLeftValue.computeTopLeft(this.container)

    override val bottomRight get() = this.bottomRightValue.computeBottomRight(this.container)

    override val bottomLeft get() = this.bottomLeftValue.computeBottomLeft(this.container)

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