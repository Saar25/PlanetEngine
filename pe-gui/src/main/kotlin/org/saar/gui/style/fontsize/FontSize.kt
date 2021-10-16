package org.saar.gui.style.fontsize

import org.saar.gui.UIChildElement
import org.saar.gui.style.value.FontSizeValue
import org.saar.gui.style.value.FontSizeValues

class FontSize(private val container: UIChildElement) : ReadonlyFontSize {

    var value: FontSizeValue = FontSizeValues.inherit

    override fun get() = this.value.compute(
        this.container.parent.style, this.container.style)

    fun set(value: FontSizeValue) {
        this.value = value
    }

    fun set(pixels: Int) = set(FontSizeValues.pixels(pixels))
}