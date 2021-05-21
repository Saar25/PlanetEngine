package org.saar.lwjgl.stb

import org.saar.utils.Box2i

data class TrueTypeCharacter(
    val char: Char,
    val bitmapBox: Box2i,
    val localBox: Box2i,
    val xAdvance: Float
)