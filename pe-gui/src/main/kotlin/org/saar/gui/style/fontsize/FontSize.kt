package org.saar.gui.style.fontsize

import org.saar.gui.UIChildNode

class FontSize(private val container: UIChildNode) : ReadonlyFontSize {

    var value: FontSizeValue = FontSizeValues.inherit

    override fun get() = this.value.compute(this.container)

    fun set(value: FontSizeValue) {
        this.value = value
    }

    fun set(pixels: Int) = set(FontSizeValues.pixels(pixels))
}