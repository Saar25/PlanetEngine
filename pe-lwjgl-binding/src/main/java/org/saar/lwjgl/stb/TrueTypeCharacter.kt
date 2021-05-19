package org.saar.lwjgl.stb

import org.lwjgl.stb.STBTTBakedChar

data class TrueTypeCharacter(val char: Char,
                             val x0: Short, val y0: Short,
                             val x1: Short, val y1: Short,
                             val xAdvance: Float) {
    companion object {
        fun of(char: Char, bakedChar: STBTTBakedChar) = TrueTypeCharacter(char,
            bakedChar.x0(), bakedChar.y0(),
            bakedChar.x1(), bakedChar.y1(),
            bakedChar.xadvance())
    }

    val width: Int get() = this.x1 - this.x0

    val height: Int get() = this.y1 - this.y0
}