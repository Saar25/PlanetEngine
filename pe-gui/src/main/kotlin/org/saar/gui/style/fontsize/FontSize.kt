package org.saar.gui.style.fontsize

import org.saar.gui.UIChildNode

class FontSize(private val container: UIChildNode, default: FontSizeValue = FontSizeValues.inherit) : ReadonlyFontSize {

    var value: FontSizeValue = default

    override val size get() = this.value.compute(this.container)

    fun set(value: FontSizeValue) {
        this.value = value
    }

    fun set(pixels: Int) {
        this.value = FontSizeValues.pixels(pixels)
    }
}