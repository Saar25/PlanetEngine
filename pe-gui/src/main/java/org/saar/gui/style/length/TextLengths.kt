package org.saar.gui.style.length

import org.saar.gui.UIText

object TextLengths {

    class Width(private val container: UIText) : TextLength() {

        override fun get(): Int = this.value.computeAxisX(
            this.container.parent.style, this.container.style)
    }

    class Height(private val container: UIText) : TextLength() {

        override fun get(): Int = this.value.computeAxisY(
            this.container.parent.style, this.container.style)
    }
}