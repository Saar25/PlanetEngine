package org.saar.maths

data class Box2f(val x0: Float, val y0: Float,
                 val x1: Float, val y1: Float) {

    val width: Float get() = this.x1 - this.x0

    val height: Float get() = this.y1 - this.y0

    fun offset(x: Float, y: Float): Box2f = Box2f(
        x + this.x0, y + this.y0,
        x + this.x1, y + this.y1)

}