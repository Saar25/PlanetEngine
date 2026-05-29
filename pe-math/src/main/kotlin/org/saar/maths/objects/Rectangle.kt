package org.saar.maths.objects

import org.joml.Vector2fc
import org.saar.maths.utils.Maths.isBetween

class Rectangle {
    var x: Float
    var y: Float
    var w: Float
    var h: Float

    constructor(x: Float, y: Float, w: Float, h: Float) {
        this.x = x
        this.y = y
        this.w = w
        this.h = h
    }

    constructor(position: Vector2fc, dimensions: Vector2fc) {
        this.x = position.x()
        this.y = position.y()
        this.w = dimensions.x()
        this.h = dimensions.y()
    }

    constructor(rectangle: Rectangle) {
        this.x = rectangle.x
        this.y = rectangle.y
        this.w = rectangle.w
        this.h = rectangle.h
    }

    fun centerX(): Float {
        return x + w / 2
    }

    fun centerY(): Float {
        return y + h / 2
    }

    fun contains(x: Float, y: Float): Boolean {
        return isBetween(x, this.x, this.x + this.w) && isBetween(y, this.y, this.y + this.h)
    }

    override fun toString(): String {
        return String.format("[Rectangle: x=%f, y=%f, w=%f, h=%f]", x, y, w, h)
    }

    companion object {
        fun ofPosition(x: Float, y: Float): Rectangle {
            return Rectangle(x, y, 0f, 0f)
        }

        fun ofDimensions(w: Float, h: Float): Rectangle {
            return Rectangle(0f, 0f, w, h)
        }

        fun fromPoints(x1: Float, y1: Float, x2: Float, y2: Float): Rectangle {
            return Rectangle(x1, y1, x2 - x1, y2 - y1)
        }
    }
}
