package org.saar.gui.style.length

import org.saar.gui.style.value.LengthValue
import org.saar.gui.style.value.LengthValues

abstract class Length : ReadonlyLength {

    var value: LengthValue = LengthValues.fit

    fun set(value: LengthValue) {
        this.value = value
    }

    fun set(pixels: Int) {
        this.value = LengthValues.pixels(pixels)
    }

    fun add(value: LengthValue) {
        this.value = LengthValues.add(this.value, value)
    }

    fun sub(value: LengthValue) {
        this.value = LengthValues.sub(this.value, value)
    }
}