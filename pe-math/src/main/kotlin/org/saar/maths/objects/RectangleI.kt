package org.saar.maths.objects

import org.joml.Vector2ic
import org.saar.maths.utils.Maths.isBetween

class RectangleI {
    @JvmField
    var x: Int
    @JvmField
    var y: Int
    @JvmField
    var w: Int
    @JvmField
    var h: Int

    constructor(x: Int, y: Int, w: Int, h: Int) {
        this.x = x
        this.y = y
        this.w = w
        this.h = h
    }

    constructor(position: Vector2ic, dimensions: Vector2ic) {
        this.x = position.x()
        this.y = position.y()
        this.w = dimensions.x()
        this.h = dimensions.y()
    }

    constructor(rectangle: RectangleI) {
        this.x = rectangle.x
        this.y = rectangle.y
        this.w = rectangle.w
        this.h = rectangle.h
    }

    fun centerX(): Int {
        return x + w / 2
    }

    fun centerY(): Int {
        return y + h / 2
    }

    fun contains(x: Int, y: Int): Boolean {
        return isBetween(x.toFloat(), this.x.toFloat(), (this.x + this.w).toFloat()) && isBetween(y.toFloat(),
            this.y.toFloat(),
            (this.y + this.h).toFloat())
    }

    override fun toString(): String {
        return String.format("[Rectangle: x=%d, y=%d, w=%d, h=%d]", x, y, w, h)
    }

    companion object {
        fun ofPosition(x: Int, y: Int): RectangleI {
            return RectangleI(x, y, 0, 0)
        }

        fun ofDimensions(w: Int, h: Int): RectangleI {
            return RectangleI(0, 0, w, h)
        }

        fun fromPoints(x1: Int, y1: Int, x2: Int, y2: Int): RectangleI {
            return RectangleI(x1, y1, x2 - x1, y2 - y1)
        }
    }
}
