package org.saar.gui.style.font

import org.jproperty.constant.ConstantObjectProperty
import org.saar.gui.font.FontLoader

object NoFontFamily : ReadonlyFontFamily {

    override val family = ConstantObjectProperty(FontLoader.DEFAULT_FONT)

}