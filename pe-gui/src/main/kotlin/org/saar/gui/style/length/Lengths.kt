package org.saar.gui.style.length

import org.saar.gui.UIChildNode

object Lengths {

    class Width(private val container: UIChildNode) : Length() {

        override fun get(): Int = this.value.computeAxisX(
            this.container.parent.style, this.container.style)
    }

    class Height(private val container: UIChildNode) : Length() {

        override fun get(): Int = this.value.computeAxisY(
            this.container.parent.style, this.container.style)
    }
}