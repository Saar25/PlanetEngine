package org.saar.gui.style.length

import org.saar.gui.style.value.LengthValue
import org.saar.gui.style.value.LengthValues

abstract class PixelsLength : ReadonlyLength {

    var value: LengthValue.Simple = LengthValues.zero
        private set

    fun set(pixels: Int) {
        this.value = LengthValues.pixels(pixels)
    }
}