package org.saar.gui.style.property

import org.saar.gui.style.StyleProperty

class Colour() : StyleProperty, IColour {

    override var red = 0f

    override var green = 0f

    override var blue = 0f

    override var alpha = 1f

    constructor(r: Int, g: Int, b: Int) : this() {
        set(r, g, b)
    }

    constructor(r: Int, g: Int, b: Int, a: Int) : this() {
        set(r, g, b, a)
    }

    fun set(value: String) {
        val hex = if (value.startsWith("#")) value.substring(1) else value
        when (hex.length) {
            6 -> setPackedRGB(value.toInt(16))
            8 -> setPackedRGBA(value.toInt(16))
            else -> throw IllegalArgumentException("Cannot parse colour $value")
        }
    }

    fun setPackedRGB(rgb: Int) {
        val r = (rgb shr 0x10 and 255) / 255f
        val g = (rgb shr 0x08 and 255) / 255f
        val b = (rgb and 0xFF) / 255f
        setNormalized(r, g, b, 1f)
    }

    fun setPackedRGBA(rgba: Int) {
        val r = (rgba shr 0x18 and 0xFF) / 255f
        val g = (rgba shr 0x10 and 0xFF) / 255f
        val b = (rgba shr 0x08 and 0xFF) / 255f
        val a = (rgba and 0xFF) / 255f
        setNormalized(r, g, b, a)
    }

    @JvmOverloads
    fun set(r: Int, g: Int, b: Int, a: Int = 255) {
        setNormalized(r / 255f, g / 255f, b / 255f, a / 255f)
    }

    @JvmOverloads
    fun setNormalized(r: Float, g: Float, b: Float, a: Float = 1f) {
        this.red = r
        this.green = g
        this.blue = b
        this.alpha = a
    }

    fun set(colour: IColour) {
        setNormalized(colour.red, colour.green,
                colour.blue, colour.alpha)
    }

    override fun toString() = asString()
}