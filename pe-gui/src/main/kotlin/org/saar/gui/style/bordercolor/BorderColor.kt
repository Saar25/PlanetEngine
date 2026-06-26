package org.saar.gui.style.bordercolor

import org.saar.gui.UIChildNode
import org.saar.gui.style.Color

class BorderColor(private val container: UIChildNode, default: BorderColorValue = BorderColorValues.inherit) : ReadonlyBorderColor {

    var value: BorderColorValue = default

    override val color get() = this.value.compute(this.container)

    fun set(color: Color) {
        this.value = BorderColorValues.of(color)
    }

    fun set(value: BorderColorValue) {
        this.value = value
    }
}