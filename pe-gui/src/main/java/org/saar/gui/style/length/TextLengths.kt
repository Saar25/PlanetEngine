package org.saar.gui.style.length

import org.saar.gui.UITextElement

object TextLengths {

    class Width(private val container: UITextElement) : TextLength() {
        override fun get(): Int = this.value.computeAxisX(
            this.container.parent.style, this.container.style)

        override fun getMax(): Int = this.value.computeMaxAxisX(
            this.container.parent.style, this.container.style)
    }

    class Height(private val container: UITextElement) : TextLength() {
        override fun get(): Int = this.value.computeAxisY(
            this.container.parent.style, this.container.style)

        override fun getMax(): Int = this.value.computeMaxAxisY(
            this.container.parent.style, this.container.style)
    }
}