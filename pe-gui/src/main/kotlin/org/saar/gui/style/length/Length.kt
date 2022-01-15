package org.saar.gui.style.length

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