package org.saar.gui.style.length

import org.saar.gui.UIChildElement

object Lengths {

    class Width(private val container: UIChildElement) : Length() {

        override fun get(): Int = this.value.computeAxisX(
            this.container.parent.style, this.container.style)
    }

    class Height(private val container: UIChildElement) : Length() {

        override fun get(): Int = this.value.computeAxisY(
            this.container.parent.style, this.container.style)
    }

    class Pixels(private val container: UIChildElement) : PixelsLength() {

        override fun get(): Int = this.value.compute(
            this.container.parent.style, this.container.style)
    }
}