package org.saar.gui.style

import org.joml.Vector4i
import org.joml.Vector4ic
import org.saar.maths.utils.Maths

class Bounds(private val style: IStyle) : StyleProperty {

    private val vector = Vector4i()

    fun xMax(): Int {
        val x = this.style.x.get()
        val width = this.style.width.get()
        return x + width
    }

    fun yMax(): Int {
        val y = this.style.y.get()
        val height = this.style.height.get()
        return y + height
    }

    fun xMin(): Int {
        return this.style.x.get()
    }

    fun yMin(): Int {
        return this.style.y.get()
    }

    fun w(): Int {
        return this.style.width.get()
    }

    fun h(): Int {
        return this.style.height.get()
    }

    fun xCenter(): Int {
        val x = this.style.x.get()
        val width = this.style.width.get()
        return x + width / 2
    }

    fun yCenter(): Int {
        val y = this.style.y.get()
        val height = this.style.height.get()
        return y + height / 2
    }

    fun contains(x: Float, y: Float): Boolean {
        return Maths.isBetween(x, xMin().toFloat(), xMax().toFloat()) &&
                Maths.isBetween(y, yMin().toFloat(), yMax().toFloat())
    }

    fun asVector4i(): Vector4ic {
        return this.vector.set(xMin(), yMin(), w(), h())
    }

    override fun toString(): String {
        return String.format("[Rectangle: x=%d, y=%d, w=%d, h=%d]", xMin(), yMin(), w(), h())
    }
}