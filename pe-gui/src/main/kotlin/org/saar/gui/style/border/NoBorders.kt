package org.saar.gui.style.border

import org.jproperty.constant.ConstantInteger

object NoBorders : ReadonlyBorders {
    override val top = ConstantInteger(0)
    override val right = ConstantInteger(0)
    override val bottom = ConstantInteger(0)
    override val left = ConstantInteger(0)
}