package org.saar.gui.style.length

import org.saar.gui.UIText

class PixelsLength(var value: Int = 0) : ReadonlyLength {

    constructor(uiText: UIText) : this(uiText.font.size.toInt())

    fun set(pixels: Int) {
        this.value = pixels
    }

    override fun get() = this.value
}