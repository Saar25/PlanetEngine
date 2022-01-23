package org.saar.gui.style.font

import org.saar.gui.UIChildNode
import org.saar.gui.font.Font

class FontFamily(private val container: UIChildNode) : ReadonlyFontFamily {

    var value: FontFamilyValue = FontFamilyValues.inherit

    override val family get() = this.value.compute(this.container)

    fun set(value: FontFamilyValue) {
        this.value = value
    }

    fun set(value: Font) {
        this.value = FontFamilyValues.of(value)
    }
}