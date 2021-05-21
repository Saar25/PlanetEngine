package org.saar.gui.font

data class FontCharacter(val char: Char,
                         val x0: Short, val y0: Short,
                         val x1: Short, val y1: Short,
                         val xAdvance: Float) {

    val width: Int get() = this.x1 - this.x0

    val height: Int get() = this.y1 - this.y0
}