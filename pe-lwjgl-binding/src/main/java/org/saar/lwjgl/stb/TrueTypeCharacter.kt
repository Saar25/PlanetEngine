package org.saar.lwjgl.stb

import org.joml.primitives.Rectanglei

data class TrueTypeCharacter(
    val char: Char,
    val bitmapBox: Rectanglei,
    val localBox: Rectanglei,
    val xAdvance: Float
)