package org.saar.gui.style.length

abstract class Length(default: LengthValue = LengthValues.fit) : ReadonlyLength {

    var value: LengthValue = default

    fun set(value: LengthValue) {
        this.value = value
    }

    fun set(pixels: Int) {
        this.value = LengthValues.pixels(pixels)
    }
}