package org.saar.gui.style.coordinate

import org.saar.gui.UIChildElement

object Coordinates {

    class X(private val container: UIChildElement) : Coordinate() {

        override fun get(): Int = this.value.computeAxisX(
            this.container.parent.style, this.container.style)
    }

    class Y(private val container: UIChildElement) : Coordinate() {

        override fun get(): Int = this.value.computeAxisY(
            this.container.parent.style, this.container.style)
    }
}