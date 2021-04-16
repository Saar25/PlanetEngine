package org.saar.gui.style.length

import org.saar.gui.UIChildElement

object Lengths {

    class Width(private val container: UIChildElement) : Length() {

        override fun get(): Int {
            val x = this.container.parent.style.x
            val w = this.container.parent.style.width
            return this.value.compute(x, w)
        }
    }

    class Height(private val container: UIChildElement) : Length() {

        override fun get(): Int {
            val y = this.container.parent.style.y
            val h = this.container.parent.style.height
            return this.value.compute(y, h)
        }
    }
}