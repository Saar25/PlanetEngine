package org.saar.gui.style.redius

import org.jproperty.constant.ConstantIntegerProperty
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

    override var topRight: ObservableIntegerValue = ConstantIntegerProperty(0)

    override var topLeft: ObservableIntegerValue = ConstantIntegerProperty(0)

    override var bottomRight: ObservableIntegerValue = ConstantIntegerProperty(0)

    override var bottomLeft: ObservableIntegerValue = ConstantIntegerProperty(0)

    init {
        set(RadiusValues.inherit)
    }

    fun set(topRight: Int, topLeft: Int, bottomRight: Int, bottomLeft: Int) {
        this.topRight = ConstantIntegerProperty(topRight)
        this.topLeft = ConstantIntegerProperty(topLeft)
        this.bottomRight = ConstantIntegerProperty(bottomRight)
        this.bottomLeft = ConstantIntegerProperty(bottomLeft)
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