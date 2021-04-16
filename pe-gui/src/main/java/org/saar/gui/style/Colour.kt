package org.saar.gui.style

class Colour(val red: Int, val green: Int, val blue: Int, val alpha: Float) {

    fun asInt(): Int = (this.red shl 0x18) +
            (this.green shl 0x10) +
            (this.blue shl 0x08) +
            (this.alpha * 255).toInt()

    override fun toString(): String {
        return "Colour(${this.red}, ${this.green}, ${this.blue}, ${this.alpha})"
    }
}