package org.saar.utils

data class Box2D(val x0: Int, val y0: Int,
                 val x1: Int, val y1: Int) {

    val width: Int get() = this.x1 - this.x0

    val height: Int get() = this.y1 - this.y0

}