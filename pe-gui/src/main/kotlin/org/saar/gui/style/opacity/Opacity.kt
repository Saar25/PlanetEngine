package org.saar.gui.style.opacity

import org.saar.gui.UIChildNode

class Opacity(private val container: UIChildNode, default: OpacityValue = OpacityValues.inherit) : ReadonlyOpacity {

    var value: OpacityValue = default

    override val opacity get() = this.value.compute(this.container)

    fun set(opacity: Float) {
        this.value = OpacityValues.of(opacity)
    }
}