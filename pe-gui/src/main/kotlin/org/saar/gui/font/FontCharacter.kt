package org.saar.gui.font

import org.joml.primitives.Rectanglei

data class FontCharacter(
    val char: Char,
    val bitmapBox: Rectanglei,
    val localBox: Rectanglei,
    val xAdvance: Float
)