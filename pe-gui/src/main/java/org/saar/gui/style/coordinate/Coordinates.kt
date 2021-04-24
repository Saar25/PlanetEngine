package org.saar.gui.style.coordinate

import org.saar.gui.UIChildElement

object Coordinates {

    class X(private val container: UIChildElement) : Coordinate() {

        override fun get(): Int {
            val x = this.container.parent.style.x
            val w = this.container.parent.style.width
            return this.value.compute(x, w, this.container.style.width)
        }
    }

    class Y(private val container: UIChildElement) : Coordinate() {

        override fun get(): Int {
            val y = this.container.parent.style.y
            val h = this.container.parent.style.height
            return this.value.compute(y, h, this.container.style.height)
        }
    }
}