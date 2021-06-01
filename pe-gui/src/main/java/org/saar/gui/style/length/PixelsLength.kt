package org.saar.gui.style.length

import org.saar.gui.UITextElement

class PixelsLength(var value: Int = 0) : ReadonlyLength {

    constructor(uiText: UITextElement) : this(uiText.font.size.toInt())

    fun set(pixels: Int) {
        this.value = pixels
    }

    override fun get() = this.value
}