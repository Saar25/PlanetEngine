package org.saar.gui.style.coordinate

import org.saar.gui.UIChildNode

object Coordinates {

    class X(private val container: UIChildNode, default: CoordinateValue = CoordinateValues.zero) : Coordinate(default) {

        override fun get(): Int = this.value.computeAxisX(this.container)
    }

    class Y(private val container: UIChildNode, default: CoordinateValue = CoordinateValues.zero) : Coordinate(default) {

        override fun get(): Int = this.value.computeAxisY(this.container)
    }
}