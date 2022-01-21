package org.saar.gui.style.length

abstract class Length : ReadonlyLength {

    var value: LengthValue = LengthValues.fit

    fun set(value: LengthValue) {
        this.value = value
    }

    fun set(pixels: Int) {
        this.value = LengthValues.pixels(pixels)
    }
}