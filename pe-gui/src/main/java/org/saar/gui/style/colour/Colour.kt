package org.saar.gui.style.colour

import org.saar.gui.UIChildElement
import org.saar.gui.style.StyleProperty
import org.saar.gui.style.value.StyleFloatValue
import org.saar.gui.style.value.StyleFloatValues

class Colour(private val container: UIChildElement) : StyleProperty, ReadonlyColour {

    var redValue: StyleFloatValue = StyleFloatValues.zero
    var greenValue: StyleFloatValue = StyleFloatValues.zero
    var blueValue: StyleFloatValue = StyleFloatValues.zero
    var alphaValue: StyleFloatValue = StyleFloatValues.one

    override var red: Float
        get() = this.redValue.compute(this.container.parent.style.backgroundColour.bottomLeft.red)
        set(value) {
            this.redValue = StyleFloatValues.of(value)
        }

    override var green: Float
        get() = this.greenValue.compute(this.container.parent.style.backgroundColour.bottomLeft.green)
        set(value) {
            this.greenValue = StyleFloatValues.of(value)
        }

    override var blue: Float
        get() = this.blueValue.compute(this.container.parent.style.backgroundColour.bottomLeft.blue)
        set(value) {
            this.blueValue = StyleFloatValues.of(value)
        }

    override var alpha: Float
        get() = this.alphaValue.compute(this.container.parent.style.backgroundColour.bottomLeft.alpha)
        set(value) {
            this.alphaValue = StyleFloatValues.of(value)
        }

    fun set(value: String) {
        val hex = if (value.startsWith("#")) value.substring(1) else value
        when (hex.length) {
            6 -> setPackedRGB(value.toInt(16))
            8 -> setPackedRGBA(value.toInt(16))
            else -> throw IllegalArgumentException("Cannot parse colour $value")
        }
    }

    private fun setPackedRGB(rgb: Int) {
        val r = (rgb shr 0x10 and 255) / 255f
        val g = (rgb shr 0x08 and 255) / 255f
        val b = (rgb and 0xFF) / 255f
        setNormalized(r, g, b, 1f)
    }

    private fun setPackedRGBA(rgba: Int) {
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

    fun set(colour: ReadonlyColour) {
        setNormalized(colour.red, colour.green,
            colour.blue, colour.alpha)
    }

    override fun toString() = asString()
}