package org.saar.gui.style.font

import org.jproperty.ObservableValue
import org.saar.gui.UIChildNode
import org.saar.gui.font.Font

class FontFamily(private val container: UIChildNode) : ReadonlyFontFamily {

    var value: FontFamilyValue = FontFamilyValues.inherit
        set(value) {
            this.family = value.build(this.container)
            field = value
        }

    override var family: ObservableValue<Font> = this.value.build(this.container)

    fun set(value: FontFamilyValue) {
        this.value = value
    }

    fun set(value: Font) = set(FontFamilyValues.of(value))
}