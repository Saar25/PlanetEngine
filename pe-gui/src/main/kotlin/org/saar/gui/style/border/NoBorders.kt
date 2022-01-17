package org.saar.gui.style.border

import org.jproperty.constant.ConstantIntegerProperty

object NoBorders : ReadonlyBorders {
    override val top = ConstantIntegerProperty(0)
    override val right = ConstantIntegerProperty(0)
    override val bottom = ConstantIntegerProperty(0)
    override val left = ConstantIntegerProperty(0)
}