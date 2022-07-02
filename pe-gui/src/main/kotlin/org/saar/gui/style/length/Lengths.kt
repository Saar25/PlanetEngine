package org.saar.gui.style.length

import org.saar.gui.UIChildNode

object Lengths {

    class Width(private val container: UIChildNode, default: LengthValue = LengthValues.fit) : Length(default) {

        override fun get(): Int = this.value.computeAxisX(this.container)

        override fun getMin(): Int = this.value.computeMinAxisX(this.container)

        override fun getMax(): Int = this.value.computeMaxAxisX(this.container)
    }

    class Height(private val container: UIChildNode, default: LengthValue = LengthValues.fit) : Length(default) {

        override fun get(): Int = this.value.computeAxisY(this.container)

        override fun getMin(): Int = this.value.computeMinAxisY(this.container)

        override fun getMax(): Int = this.value.computeMaxAxisY(this.container)
    }
}