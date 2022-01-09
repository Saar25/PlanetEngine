package org.saar.gui.style.length

import org.saar.gui.UIChildNode

object Lengths {

    class Width(private val container: UIChildNode) : Length() {

        override fun get(): Int = this.value.computeAxisX(
            this.container.parent, this.container)

        override fun getMin(): Int = this.value.computeMinAxisX(
            this.container.parent, this.container)
    }

    class Height(private val container: UIChildNode) : Length() {

        override fun get(): Int = this.value.computeAxisY(
            this.container.parent, this.container)

        override fun getMin(): Int = this.value.computeMinAxisY(
            this.container.parent, this.container)
    }
}