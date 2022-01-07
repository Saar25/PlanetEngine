package org.saar.gui.style.font

import org.saar.gui.UIChildNode
import org.saar.gui.font.Font
import org.saar.gui.style.value.StyleFontValue
import org.saar.gui.style.value.StyleFontValues

class StyleFont(private val container: UIChildNode) : ReadonlyStyleFont {

    var value: StyleFontValue = StyleFontValues.inherit

    override fun get() = this.value.compute(
        this.container.parent.style, this.container.style)

    fun set(value: StyleFontValue) {
        this.value = value
    }

    fun set(value: Font) = set(StyleFontValues.of(value))
}