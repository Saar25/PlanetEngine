package org.saar.gui.style

class Colour(val red: Int,
             val green: Int,
             val blue: Int,
             val alpha: Float) {

    fun asInt(): Int = (this.red shl 0x18) or
            (this.green shl 0x10) or
            (this.blue shl 0x08) or
            (this.alpha * 255).toInt()

    override fun toString(): String {
        return "Colour(%.3f, %.3f, %.3f, %.3f)".format(
            this.red, this.green, this.blue, this.alpha)
    }
}