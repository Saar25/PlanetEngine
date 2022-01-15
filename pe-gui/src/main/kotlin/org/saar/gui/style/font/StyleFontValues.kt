package org.saar.gui.style.font

import org.saar.gui.font.Font
import org.saar.gui.font.FontLoader

object StyleFontValues {

    @JvmStatic
    val inherit: StyleFontValue = StyleFontValue { it.parent.style.font.get() }

    @JvmStatic
    val default: StyleFontValue = StyleFontValue { FontLoader.DEFAULT_FONT }

    @JvmStatic
    fun of(value: Font): StyleFontValue = StyleFontValue { value }

}