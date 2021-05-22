package org.saar.gui.font

import org.saar.utils.Box2i

data class FontCharacter(
    val char: Char,
    val bitmapBox: Box2i,
    val localBox: Box2i,
    val xAdvance: Float
)