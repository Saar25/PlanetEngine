package org.saar.gui.style.colour

import org.joml.Vector4f

interface ReadonlyColour {

    val red: Float
    val green: Float
    val blue: Float
    val alpha: Float

    fun asInt(): Int {
        val r = (this.red * 255).toInt() shl 24
        val g = (this.green * 255).toInt() shl 16
        val b = (this.blue * 255).toInt() shl 8
        val a = (this.alpha * 255).toInt()
        return r + g + b + a
    }

    fun asVector4f(vector4f: Vector4f): Vector4f = vector4f.set(
        this.red, this.green, this.blue, this.alpha)

    fun asString(): String {
        return "Colour(%.3f, %.3f, %.3f, %.3f)".format(
            this.red, this.green, this.blue, this.alpha)
    }
}