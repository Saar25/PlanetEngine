package org.saar.gui.style.fontcolor

import org.saar.gui.UIChildNode
import org.saar.gui.style.Color
import org.saar.gui.style.fontcolor.FontColorValues.of

class FontColor(private val container: UIChildNode, default: FontColorValue = FontColorValues.inherit) : ReadonlyFontColor {

    var value: FontColorValue = default

    override val color get() = this.value.compute(this.container)

    fun set(color: Color) {
        this.value = of(color)
    }

    fun set(value: FontColorValue) {
        this.value = value
    }
}