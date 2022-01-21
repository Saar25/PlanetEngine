package org.saar.gui.style.font

import org.saar.gui.font.FontLoader

object NoFontFamily : ReadonlyFontFamily {

    override val family = FontLoader.DEFAULT_FONT

}