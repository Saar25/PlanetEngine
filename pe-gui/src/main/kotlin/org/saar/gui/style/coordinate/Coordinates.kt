package org.saar.gui.style.coordinate

import org.saar.gui.UIChildElement

object Coordinates {

    class X(private val container: UIChildElement) : Coordinate() {

        override fun get(): Int = this.value.computeAxisX(
            this.container.parent, this.container)
    }

    class Y(private val container: UIChildElement) : Coordinate() {

        override fun get(): Int = this.value.computeAxisY(
            this.container.parent, this.container)
    }
}