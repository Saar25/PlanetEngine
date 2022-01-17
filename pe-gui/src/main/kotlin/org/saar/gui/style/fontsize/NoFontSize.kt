package org.saar.gui.style.fontsize

import org.jproperty.constant.ConstantIntegerProperty

object NoFontSize : ReadonlyFontSize {

    override val size = ConstantIntegerProperty(0)
}