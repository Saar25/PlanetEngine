package org.saar.lwjgl.stb

data class TrueTypeBox(val x0: Int, val y0: Int,
                       val x1: Int, val y1: Int) {

    val xDifference: Int get() = this.x1 - this.x0

    val yDifference: Int get() = this.y1 - this.y0
}
