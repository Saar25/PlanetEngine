package org.saar.gui.style.font

import org.saar.gui.font.Font
import org.saar.gui.font.FontLoader

object NoStyleFont : ReadonlyStyleFont {

    override fun get(): Font = FontLoader.DEFAULT_FONT
}