package org.saar.gui.position.coordinate

import org.saar.gui.UIComponent

object Coordinates {

    class X(private val container: UIComponent) : Coordinate() {

        override fun get(): Int {
            val x = this.container.parent.positioner.x
            val w = this.container.parent.positioner.width
            return this.value.compute(x, w, this.container.positioner.width)
        }
    }

    class Y(private val container: UIComponent) : Coordinate() {

        override fun get(): Int {
            val y = this.container.parent.positioner.y
            val h = this.container.parent.positioner.height
            return this.value.compute(y, h, this.container.positioner.height)
        }
    }

    object Zero : Coordinate() {
        override fun get() = 0
    }
}