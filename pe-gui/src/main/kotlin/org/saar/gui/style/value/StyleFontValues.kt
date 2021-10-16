package org.saar.gui.style.value

import org.saar.gui.font.Font
import org.saar.gui.font.FontLoader

object StyleFontValues {

    @JvmStatic
    val inherit: StyleFontValue = StyleFontValue { parent, _ -> parent.font.get() }

    @JvmStatic
    val default: StyleFontValue = StyleFontValue { _, _ -> FontLoader.DEFAULT_FONT }

    @JvmStatic
    fun of(value: Font): StyleFontValue = StyleFontValue { _, _ -> value }

}