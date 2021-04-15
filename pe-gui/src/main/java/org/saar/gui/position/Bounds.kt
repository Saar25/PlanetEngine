package org.saar.gui.position

import org.joml.Vector4i
import org.joml.Vector4ic
import org.saar.gui.style.StyleProperty
import org.saar.maths.utils.Maths

class Bounds(private val positioner: Positioner) : StyleProperty {

    private val vector = Vector4i()

    fun set(bounds: Bounds) {
        this.positioner.x.value = bounds.positioner.x.value
        this.positioner.y.value = bounds.positioner.y.value
        this.positioner.width.value = bounds.positioner.width.value
        this.positioner.height.value = bounds.positioner.height.value
    }

    fun xMax(): Int {
        val x = this.positioner.x.get()
        val width = this.positioner.width.get()
        return x + width
    }

    fun yMax(): Int {
        val y = this.positioner.y.get()
        val height = this.positioner.height.get()
        return y + height
    }

    fun xMin(): Int {
        return this.positioner.x.get()
    }

    fun yMin(): Int {
        return this.positioner.y.get()
    }

    fun w(): Int {
        return this.positioner.width.get()
    }

    fun h(): Int {
        return this.positioner.height.get()
    }

    fun xCenter(): Int {
        val x = this.positioner.x.get()
        val width = this.positioner.width.get()
        return x + width / 2
    }

    fun yCenter(): Int {
        val y = this.positioner.y.get()
        val height = this.positioner.height.get()
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