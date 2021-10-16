package org.saar.lwjgl.stb

import org.saar.maths.Box2i

data class TrueTypeCharacter(
    val char: Char,
    val bitmapBox: Box2i,
    val localBox: Box2i,
    val xAdvance: Float
)