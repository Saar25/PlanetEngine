package org.saar.gui.style.font

import org.saar.gui.font.Font
import org.saar.gui.font.FontLoader

object FontFamilyValues {

    @JvmField
    val inherit: FontFamilyValue = FontFamilyValue { it.parent.style.font.family }

    @JvmField
    val default: FontFamilyValue = FontFamilyValue { FontLoader.DEFAULT_FONT }

    @JvmStatic
    fun of(value: Font): FontFamilyValue = FontFamilyValue { value }

}