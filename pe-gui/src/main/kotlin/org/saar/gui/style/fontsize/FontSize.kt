package org.saar.gui.style.fontsize

import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.UIChildNode

class FontSize(private val container: UIChildNode) : ReadonlyFontSize {

    var value: FontSizeValue = FontSizeValues.inherit
        set(value) {
            this.size = value.build(this.container)
            field = value
        }

    override var size: ObservableIntegerValue = this.value.build(this.container)

    fun set(value: FontSizeValue) {
        this.value = value
    }

    fun set(pixels: Int) = set(FontSizeValues.pixels(pixels))
}