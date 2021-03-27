package org.saar.gui.style.property

import org.joml.Vector4f
import org.saar.maths.utils.Vector4

interface IColour {

    val red: Float

    val green: Float

    val blue: Float

    val alpha: Float

    fun asInt(): Int {
        val r = (red * 255).toInt() shl 24
        val g = (green * 255).toInt() shl 16
        val b = (blue * 255).toInt() shl 8
        val a = (alpha * 255).toInt()
        return r + g + b + a
    }

    fun asVector4f(): Vector4f {
        return Vector4.of(this.red, this.green,
                this.blue, this.alpha)
    }

    fun asString(): String {
        return "Colour(%.3f, %.3f, %.3f, %.3f)".format(
                this.red, this.green, this.blue, this.alpha)
    }
}