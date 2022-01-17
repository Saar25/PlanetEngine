package org.saar.gui.style.font

import org.jproperty.constant.ConstantObject
import org.saar.gui.font.FontLoader

object NoFontFamily : ReadonlyFontFamily {

    override val family = ConstantObject(FontLoader.DEFAULT_FONT)

}