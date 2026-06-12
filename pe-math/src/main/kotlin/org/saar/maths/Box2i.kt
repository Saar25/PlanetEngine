package org.saar.maths

import org.joml.Vector4f
import org.joml.Vector4i

data class Box2i(val x0: Int, val y0: Int,
                 val x1: Int, val y1: Int) {

    val width: Int get() = this.x1 - this.x0

    val height: Int get() = this.y1 - this.y0

    fun offset(x: Int, y: Int): Box2i = Box2i(
        x + this.x0, y + this.y0,
        x + this.x1, y + this.y1)

    fun offset(x: Float, y: Float): Box2f = Box2f(
        x + this.x0, y + this.y0,
        x + this.x1, y + this.y1)

    fun toVector4i(): Vector4i = Vector4i(
        this.x0, this.y0, this.width, this.height)

    fun toVector4f(): Vector4f = Vector4f(
        this.x0.toFloat(), this.y0.toFloat(),
        this.width.toFloat(), this.height.toFloat())
}