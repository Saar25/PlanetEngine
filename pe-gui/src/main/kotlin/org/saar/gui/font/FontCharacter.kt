package org.saar.gui.font

import org.saar.maths.Box2i

data class FontCharacter(
    val char: Char,
    val bitmapBox: Box2i,
    val localBox: Box2i,
    val xAdvance: Float
)