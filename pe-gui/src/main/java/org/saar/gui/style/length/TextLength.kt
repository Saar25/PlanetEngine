package org.saar.gui.style.length

import org.saar.gui.style.value.TextLengthValue
import org.saar.gui.style.value.TextLengthValues

abstract class TextLength : ReadonlyTextLength {

    var value: TextLengthValue = TextLengthValues.inherit

    fun set(value: TextLengthValue) {
        this.value = value
    }

    fun set(pixels: Int) {
        this.value = TextLengthValues.pixels(pixels)
    }

    abstract fun getMax(): Int
}