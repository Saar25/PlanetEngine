package org.saar.lwjgl.stb

data class TrueTypeCharacter(
    val char: Char,
    val x0: Short, val y0: Short,
    val x1: Short, val y1: Short,
    val xAdvance: Float
)