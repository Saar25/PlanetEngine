package org.saar.lwjgl.stb

import org.lwjgl.stb.STBTTBakedChar

data class TrueTypeCharacter(val char: Char, val x0: Short, val y0: Short, val x1: Short, val y1: Short,
                             val xOffset: Float, val yOffset: Float, val xAdvance: Float) {
    companion object {
        fun of(char: Char, bakedChar: STBTTBakedChar) = TrueTypeCharacter(char,
            bakedChar.x0(), bakedChar.y0(),
            bakedChar.x1(), bakedChar.y1(),
            bakedChar.xoff(), bakedChar.yoff(),
            bakedChar.xadvance())
    }
}